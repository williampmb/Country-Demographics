package country.demographics;

import country.demographics.forms.User;
import country.demographics.service.Service;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for Login screen
 *
 * @author williampmb
 */
public class LoginController implements Initializable, ControlledScreen {

    @FXML
    TextField txtLogin;
    @FXML
    PasswordField pfPassword;

    private Service service;
    private ScreensController myController;
    public static User currentUser = new User();

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

    /**
     * Handles going to the Main screen
     * 
     * @param e 
     */
    @FXML
    public void goToMain(ActionEvent e) {
        User user = new User();
        user.setUsername(txtLogin.getText());
        user.setPassword(pfPassword.getText());

        User retrievedUser = service.validateUser(user);

        if (retrievedUser != null) {
            myController.setScreen(CountryDemographics.screen2ID);
            currentUser = retrievedUser;
            CountryDemographics.stage.setWidth(497 + 5);
            CountryDemographics.stage.setHeight(450);
            if (currentUser.getUserType() == 0) {
                MainController.admin = false;

            } else {
                MainController.admin = true;

            }
        }
    }
}
