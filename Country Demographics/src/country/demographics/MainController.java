package country.demographics;

import country.demographics.forms.Continent;
import country.demographics.forms.Country;
import country.demographics.service.Service;
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
public class MainController implements Initializable, ControlledScreen {

    @FXML
    MenuItem closemenu;
    @FXML
    Menu item;
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

    static boolean admin = false;

    ScreensController myController;
    private static ObservableList<Country> countries = FXCollections.observableArrayList();
    private static ObservableList<Country> countriesByContinentIdObservable = FXCollections.observableArrayList();
    private ObservableList<Country> countriesSearch = FXCollections.observableArrayList();
    public static ObservableList<Continent> continents = FXCollections.observableArrayList();
    private long lastContinentUpdate = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        refreshContinents();

        /**
         * Search Listener
         */
        txtSearch.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                countriesSearch.clear();
                lvSearch.setOpacity(1);
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

                        /**
                         * Listener for Countries in Text Box
                         */
                        lvSearch.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {
                            @Override
                            public void changed(ObservableValue<? extends Country> ov, Country t, Country t1) {
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

        /**
         * Continent Change Listener
         */
        cbContinent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Continent>() {

            @Override
            public void changed(ObservableValue<? extends Continent> ov, Continent t, Continent t1) {
                if (t1 != null) {
                    countriesByContinentIdObservable.clear();
                    cbCountry.setItems(countriesByContinentIdObservable);
                    List<Country> countriesByContinentIdList = CountryDemographics.service.getCountriesByContinentId(t1.getId());

                    for (Country c : countriesByContinentIdList) {
                        countriesByContinentIdObservable.add(c);
                    }

                    if (countriesByContinentIdObservable.isEmpty()) {
                        cbCountry.setDisable(true);
                    } else {
                        cbCountry.setDisable(false);
                        cbCountry.setItems(countriesByContinentIdObservable);
                    }

                } else {
                    cbCountry.setDisable(true);
                }

            }
        });

        /**
         * Country Change Listener
         */
        cbCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {

            @Override
            public void changed(ObservableValue<? extends Country> ov,
                    Country oldCountry, Country newCountry) {

                if (newCountry != null) {
                    lbPop.setText(String.valueOf(newCountry.getPopulation()));
                    lbArea.setText(Integer.toString(newCountry.getArea()));
                    lbOfficialL.setText(newCountry.getOfficialLanguage());
                    lbTimeZone.setText(newCountry.getTimeZone().getID());
                    lbCurrency.setText(newCountry.getCurrency());
                    lbTLD.setText(newCountry.getTLD());

                    displayFlag(newCountry.getFlag());
                } else {
                    clearCountryFields();
                }
            }
        });
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    /**
     * Handles closing the program
     */
    @FXML
    public void exitProgram() {
        System.exit(0);
    }

    /**
     * Handles going to Edit Continent screen
     * 
     * @param e 
     */
    @FXML
    public void toGoEditContinent(ActionEvent e) {
        if (admin) {

            myController.setScreen(CountryDemographics.screen3ID);

            CountryDemographics.stage.setWidth(311 + 10);
            CountryDemographics.stage.setHeight(200 + 40);

            clearScreen();
        } else {
            ErrorMessage error = new ErrorMessage("You are not admin", this);
            error.show();

        }

    }

    /**
     * Handles going to Edit Country screen 
     * 
     * @param e 
     */
    @FXML
    public void toGoEditCountry(ActionEvent e) {
        if (admin) {
            myController.setScreen(CountryDemographics.screen4ID);

            CountryDemographics.stage.setWidth(330 + 10);
            CountryDemographics.stage.setHeight(590 + 40);

            clearScreen();
        } else {
            ErrorMessage error = new ErrorMessage("You are not admin", this);
            error.show();
        }

    }

    /**
     * Handles going to Edit User screen
     * 
     * @param e 
     */
    @FXML
    public void toGoEditUser(ActionEvent e) {
        myController.setScreen(CountryDemographics.screen5ID);

        CountryDemographics.stage.setWidth(330 + 10);
        CountryDemographics.stage.setHeight(250 + 40);

        clearScreen();
    }

    /**
     * Opens About screen
     * 
     * @param e 
     */
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

        txtSearch.clear();
        lvSearch.setOpacity(0);
    }

    /**
     * Responds to when user clicks the Continent combobox
     */
    @FXML
    public void continentContextRequested() {
        if (Service.continentUpdatedRequired(lastContinentUpdate)) {
            refreshContinents();
        }
    }

    /**
     * Rests the screen
     */
    public void clearScreen() {
        cbCountry.getSelectionModel().clearSelection();
        cbContinent.getSelectionModel().clearSelection();
        txtSearch.clear();
        lvSearch.setOpacity(0);
        clearCountryFields();
    }

    /**
     * Resets all the fields
     */
    private void clearCountryFields() {
        lbPop.setText("");
        lbArea.setText("");
        lbOfficialL.setText("");
        lbTimeZone.setText("");
        lbCurrency.setText("");
        lbTLD.setText("");
        displayFlag("");
    }

    /**
     * Displays the flag
     * 
     * @param path 
     */
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

    /**
     * Resets list of Continents
     */
    public void refreshContinents() {
        continents.clear();
        List<Continent> currentContinents = CountryDemographics.service.getContinents();

        for (Continent c : currentContinents) {
            continents.add(c);
        }

        cbContinent.setItems(continents);
        cbCountry.setDisable(true);

        lastContinentUpdate = System.currentTimeMillis();
    }
}
