package com.example.demojavafx.controller;

import com.example.demojavafx.Main;
import com.example.demojavafx.dao.UserDaoImpl;
import com.example.demojavafx.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public AnchorPane rootAchPane;
    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;
    private UserDaoImpl userDao;

    @FXML
    void loginAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        User user = null;
        try {
            user = userDao.login(email, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (user != null && user.getEmail().equals(email)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage mainStage = new Stage();
                mainStage.setTitle("JavaFX Main form");
                mainStage.setScene(scene);
                mainStage.show();

                ((Stage) rootAchPane.getScene().getWindow()).close();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Login fail");
            alert.setTitle("Login Info");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDao = new UserDaoImpl();
    }
}
