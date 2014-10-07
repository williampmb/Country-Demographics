/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics.service;

import country.demographics.forms.Continent;
import country.demographics.forms.Country;
import country.demographics.forms.User;
import country.demographics.repository.Repository;
import country.demographics.util.Util;
import java.util.List;

/**
 *
 * @author admin
 */
public class Service {
    private final Repository repository;
    
    
    public Service() {
        repository = new Repository();
        
        if(repository == null) {
            Util.log("Error Opening repository");
        }
    }
    
    /**
     * Adds a new continent
     * 
     * @param continent
     * @return true if update successful, else false
     */
    public int addContinent(final Continent continent) {
        return repository.insertContinent(continent);
    }
    
    public boolean addCountry(final Country country) {
        return repository.insertCountry(country);
    }
    
    public boolean addUser(final User user) {
        return false;
    }
    
    public boolean deleteContinentById(final int continentId) {
        //TODO
        return false;
    }
    
    public boolean deleteCountryById(final int countryId) {
        //TODO
        return false;
    }
    
    public boolean deleteUserById(final int countryId) {
        //TODO
        return false;
    }
    
    public Continent getContinentById(final int id) {
        return repository.getContinentById(id);
    }
    
    /**
     * Returns list of all Continents or empty list if none exist
     * 
     * @return 
     */
    public List<Continent> getContinents() {
        return repository.getContinents();
    }
    
    public List<Country> getCountries() {
        return repository.getCountries();
    }
    
    /**
     * Returns all Countries associated with a Continent's Id
     * 
     * @param continentId
     * @return 
     */
    public List<Country> getCountriesByContinentId(final int continentId) {
        return repository.getCountriesByContinentId(continentId);
    }
    

    /**
     * This returns all Countries that match the Search Text
     * 
     * @param searchText
     * @return 
     */
    public List<String> getCountriesBySearchText(final String searchText) {
        return null;
    }
    
    public List<User> getUsers() {
        return repository.getUsers();
    }
    
    public int getLastId() {
        return repository.getLastId();
    }
    
    public boolean updateContinent(final Continent continent) {
        //TODO
        return false;
    }
    
    public boolean updateCountry(final Country country) {
        //TODO
        return false;
    }
    
    public boolean updateUser(final User user) {
        //TODO
        return false;
    }    
    /**
     * Validates a User object with a username and password.
     * If the credentials are correct, a User object with the username, uid and
     *  userType are returned. 
     * 
     * @param user 
     * @return User object or null if invalid
     */
    public User validateUser(User user) {
       return repository.validatateUser(user);
    }
}
