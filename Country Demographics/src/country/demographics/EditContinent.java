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
import javafx.scene.control.TextField;

/**
 * Controller Class for Edit Continent screen
 *
 * @author williampmb
 */
public class EditContinent implements Initializable, ControlledScreen {

    @FXML
    ChoiceBox cbContinent;
    @FXML
    TextField txtContinent;
    @FXML
    Button bntSave;
    @FXML
    Button bntDelete;
    
    ScreensController myController;
    ObservableList<Continent> continents = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshContinents();

       
        /**
         * Listener that observes the Continent Choice Box.
         * If it is changed, it will change the Country Choice Box too
         */
        cbContinent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Continent>() {

            @Override
            public void changed(ObservableValue<? extends Continent> ov, Continent t, Continent t1) {
                if (t1 != null) {
                    txtContinent.setText(t1.getName());
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

    /**
     * Handles going to the main screen. 
     * 
     * @param e 
     */
    @FXML
    public void toGoScreen2(ActionEvent e) {
        myController.setScreen(CountryDemographics.screen2ID);
        myController.getScreen(CountryDemographics.screen2ID);
        CountryDemographics.stage.setWidth(497 + 10);
        CountryDemographics.stage.setHeight(400);
        refreshContinents();
    }

    /**
     * Handles saving changes. 
     * 
     * @param e 
     */
    @FXML
    public void saveChanges(ActionEvent e) {
        boolean hasContinent = false;
        
        // if we're saving something with the exact same name, don't need to do anything
        if( ((Continent) cbContinent.getSelectionModel().getSelectedItem()).getName()
                .equals(txtContinent.getText())) {
            refreshContinents();
            return;
        }

        for (Continent c : continents) {
            if (c.getName().equals(txtContinent.getText())) {
                hasContinent = true;
                break;
            } 
        }

        if (hasContinent ){            
            ErrorMessage msg = new ErrorMessage("203: Continent already exists", this);
            msg.show();

        } else {
            Continent continentSelected = (Continent) cbContinent.getSelectionModel().getSelectedItem();
            continentSelected.setName(txtContinent.getText());

            CountryDemographics.service.updateContinent(continentSelected);
            refreshContinents();
        }

    }

    /**
     * Handles creating a new Continent
     * 
     * @param e 
     */
    @FXML
    public void newContinent(ActionEvent e) {
        boolean hasContinent = false;

        for (Continent c : continents) {
            if (c.getName().equals("New Continent")) {
                hasContinent = true;
                break;
            } 
        }

        if (hasContinent) {
            ErrorMessage msg = new ErrorMessage("201: New Continent already exists", this);
            msg.show();
                    
        } else {
            Continent nContinent = new Continent();
            nContinent.setName("New Continent");

            CountryDemographics.service.addContinent(nContinent);
            continents.add(nContinent);
            refreshContinents();

            for (Continent c : continents) {
                if (c.getName().equals("New Continent")) {
                    cbContinent.getSelectionModel().select(c);
                }
            }
        }
    }

    /**
     * Refreshes the list of Continents
     */
    private void refreshContinents() {
        continents.clear();
        cbContinent.setItems(continents);
        List<Continent> continents1 = CountryDemographics.service.getContinents();

        for (Continent c : continents1) {
            continents.add(c);
        }

        cbContinent.setItems(continents);
        txtContinent.clear();
    }

    /**
     * Handles deleting a Continent
     * 
     * @param e 
     */
    @FXML
    public void delete(ActionEvent e) {

        Continent selectedContinent = (Continent) cbContinent.getSelectionModel().getSelectedItem();
        List<Country> countriesByContinentId = CountryDemographics.service.getCountriesByContinentId(selectedContinent.getId());
        if (countriesByContinentId.isEmpty()) {
            continents.remove(selectedContinent);
            CountryDemographics.service.deleteContinentById(selectedContinent.getId());
            refreshContinents();
        } else {
            ErrorMessage msg = new ErrorMessage("205: Can't delete Continent with registered Countries.", this);
            msg.show();
            
        }
    }
}
