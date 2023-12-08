package com.example.demojavafx.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.example.demojavafx.entity.Department;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentController implements Initializable{
    @FXML
    public JFXTextField txtId;
    @FXML
    public JFXTextField txtName;
    @FXML
    public TableView<Department> tableDepartment;
    @FXML
    public TableColumn<Department, Integer> col01;
    @FXML
    public TableColumn<Department, String> col02;
    @FXML
    public JFXButton btnSave;
    @FXML
    public JFXButton btnUpdate;
    private MainController mainController;

    private Department selectedDepartment;

    @FXML
    public void saveAction(ActionEvent event) {
        String id = txtId.getText().trim();
        int integerId;
        if (id.isEmpty()) {
            integerId = 0;
        } else {
            integerId = Integer.parseInt(id);
        }

        String name = txtName.getText().trim();
        if (name.isEmpty() || integerId == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill id and name");
            alert.setTitle("Error");
            alert.showAndWait();
        } else {
            boolean sameId = false;
            for (Department d : mainController.getDepartments()) {
                if (d.getId() ==integerId) {
                    sameId = true;
                    break;
                }
            }

            if (!sameId) {
                Department department = new Department(integerId, name);
                try {
                    if (mainController.getDepartmentDao().addData(department) == 1) {
                        mainController.refreshDepartmentData();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
    public void resetAction(ActionEvent event) {
        txtId.clear();
        txtName.clear();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        txtId.setDisable(false);
        selectedDepartment = null;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        tableDepartment.setItems(mainController.getDepartments());
    }

       @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col01.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        col02.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        tableDepartment.getSelectionModel().selectedItemProperty().addListener((observableValue, department, t1) -> {
            selectedDepartment = t1;
            if (selectedDepartment != null) {
                txtId.setText(String.valueOf(selectedDepartment.getId()));
                txtName.setText(selectedDepartment.getName());
                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                txtId.setDisable(true);
            }
        });
    }

    public void updateAction(ActionEvent event) {

        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill id and name");
            alert.setTitle("Error");
            alert.showAndWait();
        } else {
            selectedDepartment.setName(name);
            try {
                if (mainController.getDepartmentDao().updateData(selectedDepartment) == 1) {
                    mainController.refreshDepartmentData();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            txtId.clear();
            txtName.clear();
            btnUpdate.setDisable(false);
            btnSave.setDisable(false);
            txtId.setDisable(false);
            selectedDepartment = null;
        }
    }
}