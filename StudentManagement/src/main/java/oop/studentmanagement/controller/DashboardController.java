package oop.studentmanagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import oop.studentmanagement.util.DBHelper;
import oop.studentmanagement.util.Nilai;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardController {

    @FXML
    private Label namaLabel;

    @FXML
    private Label nimLabel;

    @FXML
    private Label sksLabel;

    @FXML
    private TableView<Nilai> nilaiTable;

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

    private ObservableList<Nilai> nilaiList = FXCollections.observableArrayList();

    public void setStudentData(String nim) {
        String query = "SELECT m.nama_lengkap, m.jumlah_sks, mk.kode_matkul, mk.nama_matkul, mk.jumlah_sks AS sks, n.nilai_tugas, n.nilai_uts, n.nilai_uas, n.nilai_akhir " +
                "FROM mahasiswa m " +
                "LEFT JOIN nilai n ON m.nim = n.nim " +
                "LEFT JOIN matkul mk ON n.kode_matkul = mk.kode_matkul " +
                "WHERE m.nim = ?";

        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nim);
            ResultSet resultSet = statement.executeQuery();

            boolean hasData = false;

            while (resultSet.next()) {
                hasData = true;
                String namaLengkap = resultSet.getString("nama_lengkap");
                int jumlahSks = resultSet.getInt("jumlah_sks");
                String kodeMatkul = resultSet.getString("kode_matkul");
                String namaMatkul = resultSet.getString("nama_matkul");
                int sks = resultSet.getInt("sks");
                float nilaiTugas = resultSet.getFloat("nilai_tugas");
                float nilaiUts = resultSet.getFloat("nilai_uts");
                float nilaiUas = resultSet.getFloat("nilai_uas");
                String nilaiAkhir = resultSet.getString("nilai_akhir");

                // Debug log
                System.out.println("Nama: " + namaLengkap + ", NIM: " + nim + ", Total SKS: " + jumlahSks);
                System.out.println("Mata Kuliah: " + kodeMatkul + " - " + namaMatkul + ", SKS: " + sks);
                System.out.println("Nilai Tugas: " + nilaiTugas + ", Nilai UTS: " + nilaiUts + ", Nilai UAS: " + nilaiUas + ", Nilai Akhir: " + nilaiAkhir);

                namaLabel.setText(namaLengkap);
                nimLabel.setText("NIM: " + nim);
                sksLabel.setText("Total SKS: " + jumlahSks);

                Nilai nilai = new Nilai(nim, kodeMatkul, namaMatkul, sks, nilaiTugas, nilaiUts, nilaiUas, nilaiAkhir);
                nilaiList.add(nilai);
            }

            if (!hasData) {
                System.out.println("Tidak ada data ditemukan untuk NIM: " + nim);
            }

            kodeMatkulColumn.setCellValueFactory(new PropertyValueFactory<>("kodeMatkul"));
            namaMatkulColumn.setCellValueFactory(new PropertyValueFactory<>("namaMatkul"));
            sksColumn.setCellValueFactory(new PropertyValueFactory<>("sks"));
            nilaiTugasColumn.setCellValueFactory(new PropertyValueFactory<>("nilaiTugas"));
            nilaiUtsColumn.setCellValueFactory(new PropertyValueFactory<>("nilaiUts"));
            nilaiUasColumn.setCellValueFactory(new PropertyValueFactory<>("nilaiUas"));
            nilaiAkhirColumn.setCellValueFactory(new PropertyValueFactory<>("nilaiAkhir"));

            nilaiTable.setItems(nilaiList);

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
