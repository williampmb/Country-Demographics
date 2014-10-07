/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics;

import country.demographics.forms.Continent;
import country.demographics.forms.Country;
import country.demographics.forms.User;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    ObservableList<Country> countriesByContinentIdObservable = FXCollections.observableArrayList();
    ObservableList<Continent> continents = FXCollections.observableArrayList();
    @FXML
    ComboBox cbCountry;
    @FXML
    ComboBox cbContinent;
    @FXML
    ImageView ivFlag;
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

        /*  Continent na = new Continent();
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
         */
        cbContinent.setItems(continents);

        /* Country usa = new Country();
         usa.setName("USA");
         usa.setContinentId(1);  
         usa.setFlag(new Image("country/demographics/flags/United-States-of-America(USA).png"));
         countries.add(usa);

         Country br = new Country();
         br.setName("Brazil");
         br.setContinentId(2);
         br.setFlag(new Image("country/demographics/flags/Brazil.png"));
         countries.add(br);*/
        //Listener that observes the Continent Choice Box.
        //If it is changed, it will change the Country Choice Box too
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

        // Listener that change the flag when the selected country is changed. 
        //If selected country is null, it sets ImageView not visible.
        cbCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {

            @Override
            public void changed(ObservableValue<? extends Country> ov, Country t, Country t1) {

                if (t1 == null) {
                    ivFlag.setVisible(false);
                } else {
                    ivFlag.setVisible(true);
                    ivFlag.setImage(new Image(t1.getFlag()));

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

    @FXML
    public void toGoEditContinent(ActionEvent e) {
       //TODO verify with database, and push the information from a user.

        myController.setScreen(CountryDemographics.screen3ID);
        //CountryDemographics.stage.setWidth(497 + 10);
       // CountryDemographics.stage.setHeight(400);

    }

}
