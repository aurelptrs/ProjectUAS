package oop.studentmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oop.studentmanagement.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

    @FXML
    private TextField nimField;

    @FXML
    private TextField namaField;

    @FXML
    private TextField passwordField;

    @FXML
    private void handleRegister() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String password = passwordField.getText();

        String query = "INSERT INTO mahasiswa (nim, password, nama_lengkap, jumlah_sks) VALUES (?, ?, ?, 0)";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nim);
            statement.setString(2, password);
            statement.setString(3, nama);
            statement.executeUpdate();

            System.out.println("Mahasiswa baru berhasil diregistrasi: " + nim);

            Stage stage = (Stage) nimField.getScene().getWindow();
            stage.close();

            // Login otomatis setelah registrasi
            Stage loginStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/studentmanagement/dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.setStudentData(nim);

            loginStage.setTitle("Student Management System - Dashboard");
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal menambahkan mahasiswa baru: " + nim);
        }
    }
}
