package oop.studentmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oop.studentmanagement.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private TextField idField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String id = idField.getText();
        String password = passwordField.getText();

        if (authenticateStudent(id, password)) {
            // Redirect to student dashboard
            loadStudentDashboard(id);
        } else if (authenticateLecturer(id, password)) {
            // Redirect to lecturer dashboard
            loadLecturerDashboard(id);
        } else {
            showAlert("Login Failed", "Invalid ID or Password.");
        }
    }

    private boolean authenticateStudent(String nim, String password) {
        String query = "SELECT * FROM mahasiswa WHERE nim = ? AND password = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nim);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean authenticateLecturer(String nid, String password) {
        String query = "SELECT * FROM dosen WHERE nid = ? AND password = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nid);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadStudentDashboard(String nim) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/studentmanagement/FXML/dashboard.fxml"));
            Parent root = loader.load();

            // Pass data to the dashboard controller
            DashboardController controller = loader.getController();
            controller.setStudentData(nim);

            Stage stage = new Stage();
            stage.setTitle("Student Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the login window
            Stage loginStage = (Stage) idField.getScene().getWindow();
            loginStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLecturerDashboard(String nid) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/studentmanagement/FXML/lecturer_dashboard.fxml"));
            Parent root = loader.load();

            // Pass data to the dashboard controller
            LecturerDashboardController controller = loader.getController();
            controller.setLecturerData(nid);

            Stage stage = new Stage();
            stage.setTitle("Lecturer Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the login window
            Stage loginStage = (Stage) idField.getScene().getWindow();
            loginStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/studentmanagement/FXML/register.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Student Registration");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
