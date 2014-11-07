/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics;


import country.demographics.forms.User;
import country.demographics.service.Service;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author williampmb
 */
public class LoginController implements Initializable, ControlledScreen {

    Service service;
    
    ScreensController myController;

    @FXML
    TextField txtLogin;
    @FXML
    PasswordField pfPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new Service();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    public void toGoScreen2(ActionEvent e) {
       //TODO verify with database, and push the information from a user.
        
        User user = new User();
        user.setUsername(txtLogin.getText());
        user.setPassword(pfPassword.getText());
        
        User globalUser = service.validateUser(user);
        
        if(globalUser != null) {
            
            myController.setScreen(CountryDemographics.screen2ID);
            CountryDemographics.stage.setWidth(497+10);
            CountryDemographics.stage.setHeight(400);
            
        }
    }
}
