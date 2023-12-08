module com.example.demojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires com.jfoenix;


    opens com.example.demojavafx to javafx.fxml;
    exports com.example.demojavafx;
    exports com.example.demojavafx.dao;
    exports com.example.demojavafx.entity;

    exports com.example.demojavafx.controller;
    opens com.example.demojavafx.controller to javafx.fxml;
}