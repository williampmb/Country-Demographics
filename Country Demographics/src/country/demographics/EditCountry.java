package country.demographics;

import country.demographics.forms.Continent;
import country.demographics.forms.Country;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * Controller Class for Edit Country screen. 
 *
 * @author williampmb
 */
public class EditCountry implements Initializable, ControlledScreen {

    @FXML
    ChoiceBox cbCountry;
    @FXML
    ChoiceBox cbContinent;
    @FXML
    TextField txtCountry;
    @FXML
    TextField txtPop;
    @FXML
    TextField txtArea;
    @FXML
    TextField txtOfficialL;
    @FXML
    TextField txtTimeZone;
    @FXML
    TextField txtTLD;
    @FXML
    TextField txtCurrency;
    @FXML
    TextField txtPathFlag;
    @FXML
    Button btnNew;
    @FXML
    Button btnSave;
    @FXML
    Button btnDelete;
    @FXML
    Button btnBrowse;
    @FXML
    ImageView ivFlag;
    @FXML
    Label lbMessage;

    ScreensController myController;
    ObservableList<Country> countries = FXCollections.observableArrayList();
    Country currentCountry = null;
    Country lastCountry = null;
    Continent currentContinent = null;
    Continent lastContinent = null;

    ObservableList<Country> countriesByContinentIdObservable = FXCollections.observableArrayList();
    ObservableList<Continent> continents = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btnNew.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnBrowse.setDisable(true);
        disableFields();
        lbMessage.setText("");
        List<Continent> continents1 = CountryDemographics.service.getContinents();

        for (Continent c : continents1) {
            continents.add(c);
        }

        cbContinent.setItems(continents);
        if (cbContinent.getSelectionModel().getSelectedItem() == null) {
            cbCountry.setDisable(true);
        }

        /**
         * Listener for continent box
         */
        cbContinent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Continent>() {

            @Override
            public void changed(ObservableValue<? extends Continent> ov, Continent t, Continent t1) {
                
                lastContinent = t;

                if (t1 != null) {
                    currentContinent = t1;
                    btnNew.setDisable(false);

                    countriesByContinentIdObservable.clear();
                    cbCountry.setItems(countriesByContinentIdObservable);

                    List<Country> countriesByContinentIdList = CountryDemographics.service.getCountriesByContinentId(t1.getId());

                    for (Country c : countriesByContinentIdList) {
                        countriesByContinentIdObservable.add(c);

                    }
                }

                if (countriesByContinentIdObservable.isEmpty()) {

                    cbCountry.setDisable(true);
                    btnSave.setDisable(true);
                    btnDelete.setDisable(true);
                    btnBrowse.setDisable(true);

                } else {
                    cbCountry.setDisable(false);
                    btnSave.setDisable(false);
                    btnDelete.setDisable(false);
                    btnBrowse.setDisable(false);

                    cbCountry.setItems(countriesByContinentIdObservable);
                }

                if (cbCountry.getSelectionModel().getSelectedItem() == null) {
                    btnSave.setDisable(true);
                    btnDelete.setDisable(true);
                    btnBrowse.setDisable(true);

                } else {
                    btnSave.setDisable(false);
                    btnDelete.setDisable(false);
                    btnBrowse.setDisable(false);
                }
            }

        });

        /**
         * Listener that observes the Country Choice Box.
         */
        cbCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {

            @Override
            public void changed(ObservableValue<? extends Country> ov, Country t, Country t1) {
                lastCountry = t;
                if (t1 != null) {
                    currentCountry = t1;
                    enableFields();

                    txtCountry.setText(t1.getName());
                    txtPop.setText(String.valueOf(t1.getPopulation()));
                    txtArea.setText(Integer.toString(t1.getArea()));
                    txtOfficialL.setText(t1.getOfficialLanguage());
                    txtTimeZone.setText(t1.getTimeZone().getID());
                    txtCurrency.setText(t1.getCurrency());
                    txtTLD.setText(t1.getTLD());
                    txtPathFlag.setText(t1.getFlag());
                    displayFlag(t1.getFlag());

                } else {
                    clearFields();
                }

                if (cbCountry.getSelectionModel().getSelectedItem() == null) {
                    disableFields();
                    btnSave.setDisable(true);
                    btnDelete.setDisable(true);
                    btnBrowse.setDisable(true);
                } else {
                    btnSave.setDisable(false);
                    btnDelete.setDisable(false);
                    btnBrowse.setDisable(false);
                }

            }
        });

    }
    
    /**
     * Disables all fields
     */
    private void disableFields() {
        txtCountry.setDisable(true);
        txtPop.setDisable(true);
        txtArea.setDisable(true);
        txtOfficialL.setDisable(true);
        txtTimeZone.setDisable(true);
        txtTLD.setDisable(true);
        txtCurrency.setDisable(true);
        txtPathFlag.setDisable(true);
    }
    
    /**
     * Enables all fields
     */
    private void enableFields() {
        txtCountry.setDisable(false);
        txtPop.setDisable(false);
        txtArea.setDisable(false);
        txtOfficialL.setDisable(false);
        txtTimeZone.setDisable(false);
        txtTLD.setDisable(false);
        txtCurrency.setDisable(false);
        txtPathFlag.setDisable(false);
    }

    /**
     * Tries to display the flag
     * 
     * @param path 
     */
    private void displayFlag(String path) {

        try {
            ivFlag.setImage(new Image(path));
            ivFlag.setVisible(true);
        } catch (NullPointerException e) {
            // flag is just null
            ivFlag.setVisible(false);
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
            if (path == null || path.equals("")) {
                lbMessage.setVisible(false);
            } 

            ivFlag.setVisible(false);
        }
    }

    /**
     * Clears all fields of their values
     */
    private void clearFields() {
        txtCountry.setText("");
        txtPop.setText("");
        txtArea.setText("");
        txtOfficialL.setText("");
        txtTimeZone.setText("");
        txtCurrency.setText("");
        txtTLD.setText("");
        txtPathFlag.setText("");
        displayFlag("");
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    /**
     * Goes to Main Screen
     * 
     * @param e 
     */
    @FXML
    public void toGoScreen2(ActionEvent e) {
        myController.setScreen(CountryDemographics.screen2ID);
        myController.getScreen(CountryDemographics.screen2ID);
        CountryDemographics.stage.setWidth(497 + 10);
        CountryDemographics.stage.setHeight(400);
        cbContinent.getSelectionModel().clearSelection();

        cbCountry.getSelectionModel().clearSelection();
        
        // disable everything except 'Back' and ContinentBox
        clearFields();
        disableFields();        
        cbCountry.setDisable(true);
        btnNew.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnBrowse.setDisable(true);
    }

    /**
     * Handles browsing for the Flag image
     * 
     * @param e 
     */
    @FXML
    public void browseFlag(ActionEvent e) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        File file = fileChooser.showOpenDialog(null);
        try {

            if (file != null) {
                txtPathFlag.setText(file.toURI().toURL().toString());
                displayFlag(file.toURI().toURL().toString());
            }

        } catch (IOException ex) {

        }
    }

    /**
     * Handles saving data to the database
     * 
     * @param e 
     */
    @FXML
    public void saveChanges(ActionEvent e) {
        Continent selectedItem = (Continent) cbContinent.getSelectionModel().getSelectedItem();
        List<Country> countriesByContinentId = CountryDemographics.service.getCountriesByContinentId(selectedItem.getId());
        boolean hasCountry = false;

        for (Country c : countriesByContinentId) {
            if (c.getName().equals(txtCountry.getText())) {
                hasCountry = true;
                break;
            } 
        }

        if (hasCountry && !((Country) cbCountry.getSelectionModel().getSelectedItem()).getName()
                .equals(txtCountry.getText())) {     
            ErrorMessage msg = new ErrorMessage("103: Country already exists", this);
            msg.show();
            
        } else if (!cbCountry.getSelectionModel().isEmpty()) {
            currentCountry.setName(txtCountry.getText());
            currentCountry.setPopulation(Long.valueOf(txtPop.getText()));
            currentCountry.setArea(Integer.valueOf(txtArea.getText()));
            currentCountry.setOfficialLanguage(txtOfficialL.getText());
            currentCountry.setTimeZone(TimeZone.getTimeZone(txtTimeZone.getText()));
            currentCountry.setCurrency(txtCurrency.getText());
            currentCountry.setTLD(txtTLD.getText());
            currentCountry.setFlag(txtPathFlag.getText());
            displayFlag(currentCountry.getFlag());

            CountryDemographics.service.updateCountry(currentCountry);

            countriesByContinentIdObservable.clear();
            cbCountry.setItems(countriesByContinentIdObservable);

            List<Country> countriesByContinentIdList = CountryDemographics.service.getCountriesByContinentId(currentContinent.getId());

            for (Country c : countriesByContinentIdList) {
                countriesByContinentIdObservable.add(c);
            }
        }
    }

    /**
     * Handles creating a new Country 
     * 
     * @param e 
     */
    @FXML
    public void newCountry(ActionEvent e) {
        Continent selectedContinent = (Continent) cbContinent.getSelectionModel().getSelectedItem();
        List<Country> countries = CountryDemographics.service.getCountriesByContinentId(selectedContinent.getId());

        boolean hasCountry = false;

        for (Country c : countries) {
            if (c.getName().equals("New Country")) {
                hasCountry = true;
                break;
            } 
        }

        if (hasCountry) {
            
            ErrorMessage msg = new ErrorMessage("101: New Country already created", this);
            msg.show();

        } else {
            clearFields();
            Country nCountry = new Country();
            nCountry.setName("New Country");
            nCountry.setContinentId(currentContinent.getId());
            nCountry.setArea(0);
            nCountry.setTLD("");
            nCountry = CountryDemographics.service.addCountry(nCountry);

            // update list of countries
            countriesByContinentIdObservable.clear();
            cbCountry.setItems(countriesByContinentIdObservable);
            List<Country> countriesByContinentIdList = CountryDemographics.service.getCountriesByContinentId(currentContinent.getId());
            for (Country c : countriesByContinentIdList) {
                countriesByContinentIdObservable.add(c);
            }
            cbCountry.setItems(countriesByContinentIdObservable);

            // select newly created country
            for (Country c : countriesByContinentIdObservable) {
                if (c.getId() == nCountry.getId()) {
                    cbCountry.getSelectionModel().select(c);
                    break;
                }
            }
            
            cbCountry.setDisable(false);
        }
    }

    /**
     * Handles deleting a Country
     * 
     * @param e 
     */
    @FXML
    public void delete(ActionEvent e) {

        Country selectedItem = (Country) cbCountry.getSelectionModel().getSelectedItem();
        countriesByContinentIdObservable.remove(selectedItem);
        CountryDemographics.service.deleteCountryById(selectedItem.getId());
        clearFields();
        disableFields();
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnBrowse.setDisable(true);
        
        // check if there are any countries left. If there aren't, disable Country Dropdown
        Continent selectedItem1 = (Continent) cbContinent.getSelectionModel().getSelectedItem();
        List<Country> countriesByContinentId = CountryDemographics.service.getCountriesByContinentId(selectedItem1.getId());
        if (countriesByContinentId.isEmpty()) {
            cbCountry.setDisable(true);
        }
    }
}
