/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics;

import country.demographics.forms.Continent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author williampmb
 */
public class EditContinent implements Initializable, ControlledScreen {

    ScreensController myController;

    ObservableList<Continent> continents = FXCollections.observableArrayList();
    
    @FXML
    ChoiceBox cbContinent;

    @FXML
    TextField txtContinent;
    
    Continent currentContinent=  null;

  //  @FXML
  //  Button btnBack;
   // @FXML
   // Button btnSave;
   // @FXML
  //  Button btnNew;

    int count = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Continent> continentsList = CountryDemographics.service.getContinents();

        for (Continent c : continentsList) {
            continents.add(c);
        }

        cbContinent.setItems(continents);

        //Listener that observes the Continent Choice Box.
        //If it is changed, it will change the Country Choice Box too
        cbContinent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Continent>() {

            

            @Override
            public void changed(ObservableValue<? extends Continent> ov, Continent t, Continent t1) {
                currentContinent = t1;
                txtContinent.setText(t1.getName());
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
    }
    
    @FXML
    public void saveChanges(ActionEvent e){
        // pegar o continente Current Continent e alterar o nome para 
        // o texto do txtContinent.getText();
    }
    
    @FXML
    public void newContinent(ActionEvent e){
        Continent nCont = new Continent();
        
    }

}
