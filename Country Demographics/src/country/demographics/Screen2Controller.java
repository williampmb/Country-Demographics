/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics;

import country.demographics.forms.Continent;
import country.demographics.forms.Country;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author williampmb
 */
public class Screen2Controller implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    MenuItem closemenu;
    @FXML
    Menu item;
    ObservableList<Country> countries = FXCollections.observableArrayList();
    ObservableList<Country> countriesByContinent = FXCollections.observableArrayList();
    ObservableList<Continent> continents = FXCollections.observableArrayList();
    @FXML
    ComboBox cbCountry;
    @FXML
    ComboBox cbContinent;
    @FXML
    ImageView ivFlag;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Continent ct = new Continent();
        
        Country country = new Country();
        
        Continent na = new Continent();
        na.setId(1);
        na.setName("America do Norte");
        continents.add(na);
        
        Continent eu = new Continent();
        eu.setId(3);
        eu.setName("Europa");
        continents.add(eu);

        Continent sa = new Continent();
        sa.setId(2);
        sa.setName("South America");
        continents.add(sa);
        cbContinent.setItems(continents);

        Country usa = new Country();
        usa.setName("USA");
        usa.setContinentId(1);
        usa.setFlag(new Image("country/demographics/flags/United-States-of-America(USA).png"));
        countries.add(usa);

        Country br = new Country();
        br.setName("Brazil");
        br.setContinentId(2);
        br.setFlag(new Image("country/demographics/flags/Brazil.png"));
        countries.add(br);

        //Listener that observes the Continent Choice Box.
        //If it is changed, it will change the Country Choice Box too
        cbContinent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Continent>() {
            
            @Override
            public void changed(ObservableValue<? extends Continent> ov, Continent t, Continent t1) {
                countriesByContinent.clear();
                cbCountry.setItems(countriesByContinent);
                for (Country c : countries) {
                    if (c.getContinentId() == t1.getId()) {
                        countriesByContinent.add(c);

                    }
                }
                if (countriesByContinent.isEmpty()) {

                    cbCountry.setDisable(true);
                } else {
                    cbCountry.setDisable(false);
                    cbCountry.setItems(countriesByContinent);

                }

            }
        });

        // Listener that change the flag when the selected country is changed. 
        //If selected country is null, it sets ImageView not visible.
        cbCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {

            @Override
            public void changed(ObservableValue<? extends Country> ov, Country t, Country t1) {
                if (t1 == null) {
                    ivFlag.setVisible(false);
                } else {
                    ivFlag.setVisible(true);
                    ivFlag.setImage(t1.getFlag());

                }
            }
        });

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;

    }

    @FXML
    public void exitProgram() {
        System.exit(0);
    }

}
