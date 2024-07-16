package oop.studentmanagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oop.studentmanagement.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddNilaiController {

    @FXML
    private TextField nimField;

    @FXML
    private TextField nilaiTugasField;

    @FXML
    private TextField nilaiUtsField;

    @FXML
    private TextField nilaiUasField;

    @FXML
    private TextField kodeMatkulField;

    @FXML
    private void handleSubmit() {
        String nim = nimField.getText();
        float nilaiTugas = Float.parseFloat(nilaiTugasField.getText());
        float nilaiUts = Float.parseFloat(nilaiUtsField.getText());
        float nilaiUas = Float.parseFloat(nilaiUasField.getText());
        String kodeMatkul = kodeMatkulField.getText();

        float nilaiAkhir = 0.3f * nilaiTugas + 0.3f * nilaiUts + 0.4f * nilaiUas;
        String nilaiAkhirHuruf = "";

        if (nilaiAkhir >= 85) {
            nilaiAkhirHuruf = "A";
        } else if (nilaiAkhir >= 70) {
            nilaiAkhirHuruf = "B";
        } else if (nilaiAkhir >= 55) {
            nilaiAkhirHuruf = "C";
        } else if (nilaiAkhir >= 40) {
            nilaiAkhirHuruf = "D";
        } else {
            nilaiAkhirHuruf = "E";
        }

        String query = "INSERT INTO nilai (nim, kode_matkul, nilai_tugas, nilai_uts, nilai_uas, nilai_akhir) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nim);
            statement.setString(2, kodeMatkul);
            statement.setFloat(3, nilaiTugas);
            statement.setFloat(4, nilaiUts);
            statement.setFloat(5, nilaiUas);
            statement.setString(6, nilaiAkhirHuruf);
            statement.executeUpdate();

            // Update jumlah SKS
            updateJumlahSks(nim);

            Stage stage = (Stage) nimField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateJumlahSks(String nim) {
        String query = "UPDATE mahasiswa m " +
                "JOIN ( " +
                "    SELECT nim, SUM(mk.jumlah_sks) AS total_sks " +
                "    FROM nilai n " +
                "    JOIN matkul mk ON n.kode_matkul = mk.kode_matkul " +
                "    WHERE n.nim = ? " +
                "    GROUP BY nim " +
                ") AS sks ON m.nim = sks.nim " +
                "SET m.jumlah_sks = sks.total_sks";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nim);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
