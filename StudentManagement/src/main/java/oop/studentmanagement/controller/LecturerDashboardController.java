package oop.studentmanagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import oop.studentmanagement.util.DBHelper;
import oop.studentmanagement.util.Nilai;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LecturerDashboardController {

    @FXML
    private Label namaLabel;

    @FXML
    private Label nidLabel;

    @FXML
    private TableView<Nilai> nilaiTable;

    @FXML
    private TableColumn<Nilai, String> nimColumn;

    @FXML
    private TableColumn<Nilai, String> kodeMatkulColumn;

    @FXML
    private TableColumn<Nilai, String> namaMatkulColumn;

    @FXML
    private TableColumn<Nilai, Integer> sksColumn;

    @FXML
    private TableColumn<Nilai, Float> nilaiTugasColumn;

    @FXML
    private TableColumn<Nilai, Float> nilaiUtsColumn;

    @FXML
    private TableColumn<Nilai, Float> nilaiUasColumn;

    @FXML
    private TableColumn<Nilai, String> nilaiAkhirColumn;

    @FXML
    private TableColumn<Nilai, Void> actionColumn;

    private ObservableList<Nilai> nilaiList = FXCollections.observableArrayList();

    public void setLecturerData(String nid) {
        namaLabel.setText("Nama Dosen: " + getDosenName(nid));
        nidLabel.setText("NID: " + nid);
        loadNilaiData(nid);
    }

    private String getDosenName(String nid) {
        String query = "SELECT nama_lengkap FROM dosen WHERE nid = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nid);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("nama_lengkap");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void loadNilaiData(String nid) {
        nilaiList.clear();
        String query = "SELECT m.nim, mk.kode_matkul, mk.nama_matkul, mk.jumlah_sks, n.nilai_tugas, n.nilai_uts, n.nilai_uas, n.nilai_akhir " +
                "FROM dosen d " +
                "JOIN dosen_matkul dm ON d.nid = dm.nid " +
                "JOIN matkul mk ON dm.kode_matkul = mk.kode_matkul " +
                "JOIN nilai n ON mk.kode_matkul = n.kode_matkul " +
                "JOIN mahasiswa m ON n.nim = m.nim " +
                "WHERE d.nid = ?";

        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nid);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nim = resultSet.getString("nim");
                String kodeMatkul = resultSet.getString("kode_matkul");
                String namaMatkul = resultSet.getString("nama_matkul");
                int sks = resultSet.getInt("jumlah_sks");
                float nilaiTugas = resultSet.getFloat("nilai_tugas");
                float nilaiUts = resultSet.getFloat("nilai_uts");
                float nilaiUas = resultSet.getFloat("nilai_uas");
                String nilaiAkhir = resultSet.getString("nilai_akhir");

                Nilai nilai = new Nilai(nim, kodeMatkul, namaMatkul, sks, nilaiTugas, nilaiUts, nilaiUas, nilaiAkhir);
                nilaiList.add(nilai);
            }

            nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));
            kodeMatkulColumn.setCellValueFactory(new PropertyValueFactory<>("kodeMatkul"));
            namaMatkulColumn.setCellValueFactory(new PropertyValueFactory<>("namaMatkul"));
            sksColumn.setCellValueFactory(new PropertyValueFactory<>("sks"));
            nilaiTugasColumn.setCellValueFactory(new PropertyValueFactory<>("nilaiTugas"));
            nilaiUtsColumn.setCellValueFactory(new PropertyValueFactory<>("nilaiUts"));
            nilaiUasColumn.setCellValueFactory(new PropertyValueFactory<>("nilaiUas"));
            nilaiAkhirColumn.setCellValueFactory(new PropertyValueFactory<>("nilaiAkhir"));

            actionColumn.setCellFactory(new Callback<TableColumn<Nilai, Void>, TableCell<Nilai, Void>>() {
                @Override
                public TableCell<Nilai, Void> call(TableColumn<Nilai, Void> param) {
                    return new TableCell<Nilai, Void>() {
                        private final Button editButton = new Button("Edit");
                        private final Button deleteButton = new Button("Delete");

                        {
                            editButton.setOnAction(event -> {
                                Nilai nilai = getTableView().getItems().get(getIndex());
                                handleEdit(nilai);
                            });

                            deleteButton.setOnAction(event -> {
                                Nilai nilai = getTableView().getItems().get(getIndex());
                                handleDelete(nilai);
                            });

                            HBox pane = new HBox(editButton, deleteButton);
                            pane.setSpacing(10);
                            setGraphic(pane);
                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(getGraphic());
                            }
                        }
                    };
                }
            });

            nilaiTable.setItems(nilaiList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/studentmanagement/FXML/add_nilai.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Tambah Nilai Mahasiswa");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Wait until the window is closed

            // Refresh the table after adding
            loadNilaiData(nidLabel.getText().substring(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEdit(Nilai nilai) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/studentmanagement/FXML/edit_nilai.fxml"));
            Parent root = loader.load();

            // Pass data to the edit controller
            EditNilaiController controller = loader.getController();
            controller.setNilaiData(nilai);

            Stage stage = new Stage();
            stage.setTitle("Edit Nilai Mahasiswa");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Wait until the window is closed

            // Refresh the table after editing
            loadNilaiData(nidLabel.getText().substring(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete(Nilai nilai) {
        String query = "DELETE FROM nilai WHERE nim = ? AND kode_matkul = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nilai.getNim());
            statement.setString(2, nilai.getKodeMatkul());
            statement.executeUpdate();

            nilaiList.remove(nilai);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) namaLabel.getScene().getWindow();
        stage.close();

        try {
            Stage loginStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/oop/studentmanagement/FXML/login.fxml"));
            loginStage.setTitle("Student Management System - Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
