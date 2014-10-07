/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics;

import country.demographics.forms.Continent;
import country.demographics.forms.Country;
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
public class EditCountry implements Initializable, ControlledScreen {

    ScreensController myController;

    ObservableList<Country> countries = FXCollections.observableArrayList();

    @FXML
    ChoiceBox cbCountry;
    @FXML
    ChoiceBox cbContinent;

    @FXML
    TextField txtCountry;

    Country currentCountry = null;

    ObservableList<Country> countriesByContinentIdObservable = FXCollections.observableArrayList();
    ObservableList<Continent> continents = FXCollections.observableArrayList();

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
        List<Continent> continents1 = CountryDemographics.service.getContinents();

        for (Continent c : continents1) {
            continents.add(c);

        }

        cbContinent.setItems(continents);

        cbContinent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Continent>() {

            @Override
            public void changed(ObservableValue<? extends Continent> ov, Continent t, Continent t1) {
                countriesByContinentIdObservable.clear();
                cbCountry.setItems(countriesByContinentIdObservable);

                List<Country> countriesByContinentIdList = CountryDemographics.service.getCountriesByContinentId(t1.getId());

                for (Country c : countriesByContinentIdList) {
                    System.out.println(c.toString());
                    countriesByContinentIdObservable.add(c);

                }
                if (countriesByContinentIdObservable.isEmpty()) {

                    cbCountry.setDisable(true);
                } else {
                    cbCountry.setDisable(false);
                    cbCountry.setItems(countriesByContinentIdObservable);

                }

            }
        });

        //Listener that observes the Continent Choice Box.
        cbCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {

            @Override
            public void changed(ObservableValue<? extends Country> ov, Country t, Country t1) {
                currentCountry = t1;
                txtCountry.setText(t1.getName());
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
    public void browseFlag(ActionEvent e) {
         System.out.println(" teste");
//open file browser and select file.
    }

    @FXML
    public void saveChanges(ActionEvent e) {
        // update the database with this currentCountry!
        // the same with continent
                
    }

    @FXML
    public void newCountry(ActionEvent e) {
        /*  
        
         Create a new row in database and return that continent to here
        the same with continent
         */
    }

}
