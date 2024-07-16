module oop.studentmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens oop.studentmanagement to javafx.fxml;
    exports oop.studentmanagement;
    exports oop.studentmanagement.controller;
    opens oop.studentmanagement.controller to javafx.fxml;
    exports oop.studentmanagement.util;
    opens oop.studentmanagement.util to javafx.fxml;
}