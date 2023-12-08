package com.example.demojavafx.controller;

import com.example.demojavafx.Main;
import com.example.demojavafx.dao.DepartmentDaoImpl;
import com.example.demojavafx.dao.StudentDaoImpl;
import com.example.demojavafx.entity.Department;
import com.example.demojavafx.entity.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public VBox vBoxRoot;
    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<Student> tableStudent;
    @FXML
    private JFXComboBox<Department> comboDepartment;
    @FXML
    private TableColumn<Student, String> col01;

    @FXML
    private TableColumn<Student, String> col02;

    @FXML
    private TableColumn<Student, Department> col03;



    @FXML
    private JFXTextArea txtAddress;

    @FXML
    private JFXTextField txtFistName;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private ObservableList<Department> departments;
    @FXML
    private ObservableList<Student> students;

    private Student selectedStudent;
    private Department selectedDepartment;

    public DepartmentDaoImpl getDepartmentDao() {
        return departmentDao;
    }

    private DepartmentDaoImpl departmentDao;
    private StudentDaoImpl studentDao;

    public void refreshDepartmentData() throws SQLException, ClassNotFoundException {
        departments.addAll(departmentDao.fetchAll());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        departmentDao = new DepartmentDaoImpl();
        studentDao = new StudentDaoImpl();

        departments = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        try {

            departments.addAll(departmentDao.fetchAll());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            students.addAll(studentDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        comboDepartment.setItems(departments);
        tableStudent.setItems(students);

        col01.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        col02.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFistName() + " " + data.getValue().getLastName()));
        col03.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDepartment()));

        tableStudent.getSelectionModel().selectedItemProperty().addListener(((observableValue, student, t1) -> {
            selectedStudent = t1;
            if (selectedStudent != null) {
                txtId.setText(selectedStudent.getId());
                txtFistName.setText(selectedStudent.getFistName());
                txtLastName.setText(selectedStudent.getLastName());
                txtAddress.setText(selectedStudent.getAddress());
                comboDepartment.setValue(selectedStudent.getDepartment());
                txtId.setDisable(true);
                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
            }
        }));
    }
    @FXML
    void callDepartmentView(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("department-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            DepartmentController controller = fxmlLoader.getController();
            controller.setMainController(this);
            Stage mainStage = new Stage();
            mainStage.setTitle("JavaFX Main form");
            mainStage.initModality(Modality.WINDOW_MODAL);
            mainStage.initOwner(vBoxRoot.getScene().getWindow());
            mainStage.setScene(scene);
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Department> getDepartments() {
        return departments;
    }

    @FXML
    void resetAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

    }

    @FXML
    void updateAction(ActionEvent event) {

    }
}
