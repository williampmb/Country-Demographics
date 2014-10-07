/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package country.demographics;

import country.demographics.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author williampmb
 */
public class CountryDemographics extends Application {
    public static String screen1ID = "login";
    public static String screen1File = "Login.fxml";
    public static String screen2ID = "screen2";
    public static String screen2File = "Screen2.fxml";
    public static String screen3ID = "editcontinent";
    public static String screen3File = "EditContinent.fxml";
    
    public static Service service;
    
    public static Stage stage;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        service = new Service();
        this.stage = stage;
        
        ScreensController mainContainer = new ScreensController();
        
        mainContainer.loadScreen(CountryDemographics.screen1ID, CountryDemographics.screen1File);
        mainContainer.loadScreen(CountryDemographics.screen2ID, CountryDemographics.screen2File);
        mainContainer.loadScreen(CountryDemographics.screen3ID, CountryDemographics.screen3File);
        
        mainContainer.setScreen(CountryDemographics.screen1ID);
        
        
             
        
             
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
                
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
