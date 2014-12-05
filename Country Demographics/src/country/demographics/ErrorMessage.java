/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class ErrorMessage {
    private Stage stage;
    
    public ErrorMessage(final String errorText, final Object theParent) {
        
        try { 
            ErrorController.error = errorText;
            Parent parent = FXMLLoader.load(theParent.getClass().getResource("/country/demographics/Error.fxml"));
            stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Error");
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    public void show() {
        stage.show();
        stage.setResizable(false);
    }
}
