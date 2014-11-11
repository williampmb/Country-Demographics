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
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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
    ObservableList<Country> countriesSearch = FXCollections.observableArrayList();
    public static ObservableList<Continent> continents = FXCollections.observableArrayList();
    @FXML
    ComboBox cbCountry;
    @FXML
    ComboBox cbContinent;
    @FXML
    ImageView ivFlag;

    @FXML
    Label lbPop;
    @FXML
    Label lbArea;
    @FXML
    Label lbOfficialL;
    @FXML
    Label lbTimeZone;
    @FXML
    Label lbCurrency;
    @FXML
    Label lbTLD;
    @FXML
    TextField txtSearch;
    @FXML
    ListView lvSearch;
    @FXML
    MenuItem miEditUser;
    @FXML
            Menu Edit;

    Country currentCountry;
    int count = 0;
    Continent currentContinent;

    User loggedUser = LoginController.loggedUser;

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
        txtSearch.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                countriesSearch.clear();
                lvSearch.setItems(countriesSearch);
                String text = txtSearch.getText();

                if (text.equals("")) {
                    lvSearch.setDisable(true);
                    lvSearch.setOpacity(0.0);
                } else {
                    List<Country> allCountry = CountryDemographics.service.getCountriesBySearchText(text);
                    if (allCountry.isEmpty()) {
                        lvSearch.setDisable(true);
                        lvSearch.setOpacity(0.0);
                    } else {
                        for (Country c : allCountry) {
                            countriesSearch.add(c);
                        }
                        lvSearch.setItems(countriesSearch);
                        lvSearch.setDisable(false);
                        lvSearch.setOpacity(1.0);

                        lvSearch.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {
                            @Override
                            public void changed(ObservableValue<? extends Country> ov, Country t, Country t1) {
                                System.out.println("teste");
                                try {
                                    int continentId = t1.getContinentId();
                                    Continent continentById = CountryDemographics.service.getContinentById(continentId);
                                    cbContinent.getSelectionModel().select(continentById);
                                    cbCountry.getSelectionModel().select(t1);
                                    lvSearch.setDisable(true);
                                    lvSearch.setOpacity(0.0);
                                    txtSearch.clear();
                                } catch (NullPointerException e) {

                                }

                            }
                        });

                    }

                }

            }
        });

        cbContinent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Continent>() {

            @Override
            public void changed(ObservableValue<? extends Continent> ov, Continent t, Continent t1) {
                currentContinent = t1;
                if (t1 != null) {
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

            }
        });

        // Listener that change the flag when the selected country is changed. 
        //If selected country is null, it sets ImageView not visible.
        cbCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {

            @Override
            public void changed(ObservableValue<? extends Country> ov, Country t, Country t1) {

                if (t1 != null) {
                    currentCountry = t1;

                    lbPop.setText(String.valueOf(t1.getPopulation()));

                    lbArea.setText(Integer.toString(t1.getArea()));

                    lbOfficialL.setText(t1.getOfficialLanguage());

                    lbTimeZone.setText(t1.getTimeZone().getID());

                    lbCurrency.setText(t1.getCurrency());

                    lbTLD.setText(t1.getTLD());

                    displayFlag(t1.getFlag());

                } else {
                    clearFields();
                }
            }
        });

    }

    private void clearFields() {
        lbPop.setText("");
        lbArea.setText("");
        lbOfficialL.setText("");
        lbTimeZone.setText("");
        lbCurrency.setText("");
        lbTLD.setText("");
        displayFlag("");
    }

    private void displayFlag(String path) {
        try {

            ivFlag.setImage(new Image(path));
            ivFlag.setVisible(true);
        } catch (NullPointerException e) {

            ivFlag.setVisible(false);
        } catch (IllegalArgumentException e) {
            if (path.equals("")) {
                ivFlag.setVisible(false);
            } else {

                ivFlag.setVisible(false);

                try {
                    Thread.sleep(400);

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
        }
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
        myController.setScreen(CountryDemographics.screen3ID);
        CountryDemographics.stage.setWidth(311 + 10);
        CountryDemographics.stage.setHeight(200 + 40);

        int countrySelectedIndex = cbCountry.getSelectionModel().getSelectedIndex();
        cbCountry.getSelectionModel().clearSelection(countrySelectedIndex);

        int continentSelectedIndex = cbContinent.getSelectionModel().getSelectedIndex();
        cbContinent.getSelectionModel().clearSelection(continentSelectedIndex);

    }

    @FXML
    public void toGoEditUser(ActionEvent e) {
        myController.setScreen(CountryDemographics.screen5ID);
        CountryDemographics.stage.setWidth(330 + 10);
        CountryDemographics.stage.setHeight(250 + 40);

        int countrySelectedIndex = cbCountry.getSelectionModel().getSelectedIndex();
        cbCountry.getSelectionModel().clearSelection(countrySelectedIndex);

        int continentSelectedIndex = cbContinent.getSelectionModel().getSelectedIndex();
        cbContinent.getSelectionModel().clearSelection(continentSelectedIndex);

    }

    @FXML
    public void openAbout(ActionEvent e) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/country/demographics/About.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("About");
            stage.show();
            stage.setResizable(false);

        } catch (Exception ex) {
            System.out.println("Abriu About nao!");
        }

    }

    @FXML
    public void toGoEditCountry(ActionEvent e) {
        myController.setScreen(CountryDemographics.screen4ID);
        CountryDemographics.stage.setWidth(330 + 10);
        CountryDemographics.stage.setHeight(590 + 40);

        int countrySelectedIndex = cbCountry.getSelectionModel().getSelectedIndex();
        cbCountry.getSelectionModel().clearSelection(countrySelectedIndex);

        int continentSelectedIndex = cbContinent.getSelectionModel().getSelectedIndex();
        cbContinent.getSelectionModel().clearSelection(continentSelectedIndex);

    }

    public void refreshChoiceBox() {
        CountryDemographics.service.updateCountry(currentCountry);

        countriesByContinentIdObservable.clear();
        cbCountry.setItems(countriesByContinentIdObservable);

        List<Country> countriesByContinentIdList = CountryDemographics.service.getCountriesByContinentId(currentContinent.getId());

        for (Country c : countriesByContinentIdList) {
            countriesByContinentIdObservable.add(c);
        }
    }

}
