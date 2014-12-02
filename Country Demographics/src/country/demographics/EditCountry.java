/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    Button bntSave;
    @FXML
    Button bntDelete;
    @FXML
    Button bntBrowse;

    @FXML
    ImageView ivFlag;

    @FXML
    Label lbMessage;

    Country currentCountry = null;
    Country lastCountry = null;
    Continent currentContinent = null;
    Continent lastContinent = null;

    ObservableList<Country> countriesByContinentIdObservable = FXCollections.observableArrayList();
    ObservableList<Continent> continents = FXCollections.observableArrayList();

    int count = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bntSave.setDisable(true);
        bntDelete.setDisable(true);
        lbMessage.setText("");
        List<Continent> continents1 = CountryDemographics.service.getContinents();

        for (Continent c : continents1) {
            continents.add(c);

        }

        cbContinent.setItems(continents);
        if (cbContinent.getSelectionModel().getSelectedItem() == null) {
            cbCountry.setDisable(true);
        }

        cbContinent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Continent>() {

            @Override
            public void changed(ObservableValue<? extends Continent> ov, Continent t, Continent t1) {
                lastContinent = t;

                if (t1 != null) {
                    currentContinent = t1;

                    countriesByContinentIdObservable.clear();
                    cbCountry.setItems(countriesByContinentIdObservable);

                    List<Country> countriesByContinentIdList = CountryDemographics.service.getCountriesByContinentId(t1.getId());

                    for (Country c : countriesByContinentIdList) {
                        countriesByContinentIdObservable.add(c);

                    }
                }

                if (countriesByContinentIdObservable.isEmpty()) {

                    cbCountry.setDisable(true);
                    bntSave.setDisable(true);
                    bntDelete.setDisable(true);
                    bntBrowse.setDisable(true);

                } else {
                    cbCountry.setDisable(false);
                    bntSave.setDisable(false);
                    bntDelete.setDisable(false);
                    bntBrowse.setDisable(false);

                    cbCountry.setItems(countriesByContinentIdObservable);

                }

                if (cbCountry.getSelectionModel().getSelectedItem() == null) {
                    bntSave.setDisable(true);
                    bntDelete.setDisable(true);
                    bntBrowse.setDisable(true);

                } else {
                    bntSave.setDisable(false);
                    bntDelete.setDisable(false);
                    bntBrowse.setDisable(false);

                }
            }

        });

        //Listener that observes the Continent Choice Box.
        cbCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {

            @Override
            public void changed(ObservableValue<? extends Country> ov, Country t, Country t1) {
                lastCountry = t;
                if (t1 != null) {
                    currentCountry = t1;

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

                    bntSave.setDisable(true);
                    bntDelete.setDisable(true);
                    bntBrowse.setDisable(true);

                } else {
                    bntSave.setDisable(false);
                    bntDelete.setDisable(false);
                    bntBrowse.setDisable(false);

                }

            }
        });

    }

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
            } else {
                ivFlag.setVisible(true);

            }

            ivFlag.setVisible(false);
        }
    }

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

    @FXML
    public void toGoScreen2(ActionEvent e) {
        myController.setScreen(CountryDemographics.screen2ID);
        myController.getScreen(CountryDemographics.screen2ID);
        CountryDemographics.stage.setWidth(497 + 10);
        CountryDemographics.stage.setHeight(400);
        cbContinent.getSelectionModel().clearSelection();

        cbCountry.getSelectionModel().clearSelection();

    }

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

    @FXML
    public void saveChanges(ActionEvent e) {
        Continent selectedItem = (Continent) cbContinent.getSelectionModel().getSelectedItem();
        List<Country> countriesByContinentId = CountryDemographics.service.getCountriesByContinentId(selectedItem.getId());
        boolean hasCountry = false;

        for (Country c : countriesByContinentId) {
            if (c.getName().equals(txtCountry.getText())) {
                hasCountry = true;
                break;
            } else {
                hasCountry = false;

            }
        }

        if (hasCountry) {
            try {
                ErroController.erro = "103: Country already exists";
                Parent parent = FXMLLoader.load(getClass().getResource("/country/demographics/Erro.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Erro");

                stage.show();
                stage.setResizable(false);

            } catch (Exception ex) {
                System.out.println("Problem to open.");
            }
        } else {
            if (!cbCountry.getSelectionModel().isEmpty()) {
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

    }

    @FXML
    public void newCountry(ActionEvent e) {
        Continent selectedContinent = (Continent) cbContinent.getSelectionModel().getSelectedItem();
        List<Country> countries = CountryDemographics.service.getCountriesByContinentId(selectedContinent.getId());

        boolean hasCountry = false;

        for (Country c : countries) {
            if (c.getName().equals("New Country")) {
                hasCountry = true;
                break;
            } else {
                hasCountry = false;

            }
        }

        if (hasCountry) {
            try {
                ErroController.erro = "101: New Country already created";
                Parent parent = FXMLLoader.load(getClass().getResource("/country/demographics/Erro.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Erro");

                stage.show();
                stage.setResizable(false);

            } catch (Exception ex) {
                System.out.println("Abriu About nao!");
            }
        } else {
            clearFields();
            Country nCountry = new Country();
            nCountry.setName("New Country");
            nCountry.setContinentId(currentContinent.getId());
            nCountry = CountryDemographics.service.addCountry(nCountry);

            countriesByContinentIdObservable.clear();
            cbCountry.setItems(countriesByContinentIdObservable);

            List<Country> countriesByContinentIdList = CountryDemographics.service.getCountriesByContinentId(currentContinent.getId());

            for (Country c : countriesByContinentIdList) {
                countriesByContinentIdObservable.add(c);
            }
            cbCountry.setItems(countriesByContinentIdObservable);

            for (Country c : countriesByContinentIdObservable) {
                if (c.getId() == nCountry.getId()) {
                    cbCountry.getSelectionModel().select(c);

                }
            }
            cbCountry.setDisable(false);
        }
    }

    @FXML
    public void delete(ActionEvent e) {

        Country selectedItem = (Country) cbCountry.getSelectionModel().getSelectedItem();
        countriesByContinentIdObservable.remove(selectedItem);
        CountryDemographics.service.deleteCountryById(selectedItem.getId());
        clearFields();
        Continent selectedItem1 = (Continent) cbContinent.getSelectionModel().getSelectedItem();
        List<Country> countriesByContinentId = CountryDemographics.service.getCountriesByContinentId(selectedItem1.getId());
        if (countriesByContinentId.isEmpty()) {
            cbCountry.setDisable(true);
        }
    }

}
