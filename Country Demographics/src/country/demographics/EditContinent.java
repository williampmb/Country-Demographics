/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    Continent currentContinent = null;

    @FXML
    Button bntSave;
    @FXML
    Button bntDelete;
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
                if (t1 != null) {
                    txtContinent.setText(t1.getName());

                } else {

                }

                if (continents.isEmpty()) {
                    bntSave.setDisable(true);
                    bntDelete.setDisable(true);

                } else {
                    bntDelete.setDisable(false);
                    bntSave.setDisable(false);

                }

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
        refreshContinent();
        txtContinent.clear();
    }

    @FXML
    public void saveChanges(ActionEvent e) {
        boolean hasContinent = false;

        for (Continent c : continents) {
            if (c.getName().equals(txtContinent.getText())) {
                hasContinent = true;
                break;
            } else {
                hasContinent = false;
            }
        }

        if (hasContinent) {
            try {
                ErroController.erro = "203: Continent already exists";
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
            Continent continentSelected = (Continent) cbContinent.getSelectionModel().getSelectedItem();
            continentSelected.setName(txtContinent.getText());

            CountryDemographics.service.updateContinent(continentSelected);
            refreshContinent();
        }

    }

    @FXML
    public void newContinent(ActionEvent e) {
        boolean hasContinent = false;

        for (Continent c : continents) {
            if (c.getName().equals("New Continent")) {
                hasContinent = true;
                break;
            } else {
                hasContinent = false;
            }
        }

        if (hasContinent) {
            try {
                ErroController.erro = "201: Continent already exists";
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
            Continent nContinent = new Continent();
            nContinent.setName("New Continent");

            CountryDemographics.service.addContinent(nContinent);
            continents.add(nContinent);
            refreshContinent();

            for (Continent c : continents) {
                if (c.getName().equals("New Continent")) {
                    cbContinent.getSelectionModel().select(c);
                }
            }

        }
        refreshContinent();

    }

    private void refreshContinent() {
        continents.clear();
        cbContinent.setItems(continents);
        List<Continent> continents1 = CountryDemographics.service.getContinents();

        for (Continent c : continents1) {
            continents.add(c);
        }

        cbContinent.setItems(continents);

    }

    @FXML
    public void delete(ActionEvent e) {

        Continent selectedContinent = (Continent) cbContinent.getSelectionModel().getSelectedItem();
        List<Country> countriesByContinentId = CountryDemographics.service.getCountriesByContinentId(selectedContinent.getId());
        if (countriesByContinentId.isEmpty()) {
            continents.remove(selectedContinent);
            CountryDemographics.service.deleteContinentById(selectedContinent.getId());
            refreshContinent();
            txtContinent.clear();
        } else {
            try {
                ErroController.erro = "205: Can't delete Continent with registered Countries.";
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
        }
    }

}
