/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;



/**
 * FXML Controller class
 *
 * @author williampmb
 */
public class ErroController implements Initializable {
    
    static String erro = "erro";
    @FXML
    Label lErro;
    @FXML
    Button btnClose;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lErro.setText(erro);
    } 
    
    @FXML
    void close(){
        Stage erro = (Stage) btnClose.getScene().getWindow();
        erro.close();
    }
    
}
