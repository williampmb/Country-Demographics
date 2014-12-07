/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics;

import country.demographics.forms.Continent;
import country.demographics.forms.User;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author williampmb
 */
public class EditUser implements Initializable, ControlledScreen {

    ScreensController myController;

    ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    ChoiceBox cbUser;

    @FXML
    Button btnNewUser;

    @FXML
    TextField txtLogin;

    @FXML
    TextField txtNewPassword;

    @FXML
    TextField txtNewPassword2;

    @FXML
    PasswordField pfPassword;

    @FXML
    CheckBox cebLvlUser;

    User loggedUser;

    Continent currentContinent = null;

    int count = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /* List<User> userList = CountryDemographics.service.getUsers();

         for (User c : userList) {
         users.add(c);
         }

         cbUser.setItems(users);*/

        cbUser.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (LoginController.currentUser != null) {
                    if (LoginController.currentUser.getUserType() == 0) {

                        /* for (User u : users) {
                         if (u.getUserId() == LoginController.currentUser.getUserId()) {
                         loggedUser = u;
                         break;
                         }
                         }*/
                        users.clear();
                        cbUser.setItems(users);
                        txtLogin.clear();
                        users.add(LoginController.currentUser);
                        cbUser.setItems(users);
                        cebLvlUser.setDisable(true);
                        btnNewUser.setDisable(true);

                        // cbUser.setDisable(true);
                    } else {
                        refreshUser();

                        cebLvlUser.setDisable(false);

                        cbUser.setDisable(false);

                    }
                }
            }
        });

        //Listener that observes the Continent Choice Box.
        //If it is changed, it will change the Country Choice Box too
        cbUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {

            @Override
            public void changed(ObservableValue<? extends User> ov, User t, User t1) {
                if (t1 != null) {
                    txtLogin.setText(t1.getUsername());
                    if (t1.getUserType() == 1) {
                        cebLvlUser.setSelected(true);

                    } else {
                        cebLvlUser.setSelected(false);

                    }
                    /* if(t1.getUserType() == 0){
                     users.clear();
                     cbUser.setItems(users);
                     users.add(LoginController.loggedUser);
                        
                     }else{
                     refreshUser();
                     }*/
                }

            }
        });

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;

    }

    @FXML
    public void toGoScreen2(ActionEvent e) {
        myController.setScreen(CountryDemographics.screen2ID);
        CountryDemographics.stage.setWidth(497 + 10);
        CountryDemographics.stage.setHeight(400);
        refreshUser();
    }

    @FXML
    public void saveChanges(ActionEvent e) {
        boolean hasUser = false;
        boolean canChange = false;

        for (User u : users) {
            if (u.getUsername().equals(txtLogin.getText())) {
                hasUser = true;
                break;
            } else {
                hasUser = false;

            }
        }
        
        for (User u : users) {
            if (u.getUsername().equals(txtLogin.getText())) {
                canChange = false;
                break;
            } else {
                canChange = true;

            }
        }
        

        if (hasUser && !canChange ) {
            try {
                ErrorController.error = "303: User already exists";
                Parent parent = FXMLLoader.load(getClass().getResource("/country/demographics/Error.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Erro");

                stage.show();
                stage.setResizable(false);

            } catch (Exception ex) {
                System.out.println("Problem to open.");
            }
        } else {
            User userSelected = (User) cbUser.getSelectionModel().getSelectedItem();
            userSelected.setUsername(txtLogin.getText());
            if (!txtNewPassword.getText().equals("")) {
                if (txtNewPassword.getText().equals(txtNewPassword2.getText())) {
                    userSelected.setPassword(txtNewPassword.getText());
                }

            }
            boolean selected = cebLvlUser.isSelected();
            if (selected) {
                userSelected.setUserType(1);
            } else {
                userSelected.setUserType(0);
            }

            CountryDemographics.service.updateUser(userSelected);
            refreshUser();
        }

        //TODO 
        // CountryDemographics.service.updateUser(userSelected);
    }

    @FXML
    public void newUser(ActionEvent e) {
        if (LoginController.currentUser.getUserType() == 0) {
            btnNewUser.setDisable(true);
        } else {
            boolean hasUser = false;

            for (User u : users) {
                if (u.getUsername().equals("New User")) {
                    hasUser = false;
                } else {
                    hasUser = true;
                    break;
                }
            }

            if (hasUser) {
                try {
                    ErrorController.error = "301: User already exists";
                    Parent parent = FXMLLoader.load(getClass().getResource("/country/demographics/Error.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Erro");

                    stage.show();
                    stage.setResizable(false);

                } catch (Exception ex) {
                    System.out.println("Problem to open.");
                }
            } else {
                User nUser = new User();
                nUser.setUsername("New User");

                CountryDemographics.service.addUser(nUser);
                refreshUser();
            }
        }
    }

    @FXML
    public void delete(ActionEvent e) {
        User selectedUser = (User) cbUser.getSelectionModel().getSelectedItem();
        users.remove(selectedUser);
        CountryDemographics.service.deleteUserById(selectedUser.getUserId());
        refreshUser();
    }

    private void refreshUser() {
        users.clear();
        cbUser.setItems(users);
        List<User> userList = CountryDemographics.service.getUsers();

        for (User c : userList) {
            users.add(c);
        }

        cbUser.setItems(users);

        txtLogin.clear();
        txtNewPassword.clear();
        txtNewPassword2.clear();
    }

}
