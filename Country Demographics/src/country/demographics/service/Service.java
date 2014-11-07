/**
 * Service class to interact with repository
 * 
 * @author James Lexen
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
    
    /**
     * Constructor: Initializes the repository
     */
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
    
    /**
     * Adds a country 
     * 
     * @param country
     * @return The added country or null if unsuccessful
     */
    public Country addCountry(final Country country) {
        return repository.insertCountry(country);
    }
    
    /**
     * Adds a user
     * 
     * @param user
     * @return True/False depending on success in repository
     */
    public boolean addUser(final User user) {
        return repository.createUser(user);
    }
    
    /**
     * Deletes a continent by it's ID
     * 
     * @param continentId
     * @return True/False depending on success in repository
     */
    public boolean deleteContinentById(final int continentId) {
        return repository.deleteContinentById(continentId);
    }
    
    /**
     * Deletes a Country by it's ID
     * 
     * @param countryId
     * @return True/False depending on success in repository
     */
    public boolean deleteCountryById(final int countryId) {
        return repository.deleteCountryById(countryId);
    }
    
    /**
     * Deletes a User by it's ID
     * 
     * @param userId
     * @return True/False depending on success in repository
     */
    public boolean deleteUserById(final int userId) {
        return repository.deleteUserById(userId);
    }
    
    /**
     * Gets a Continent by it's Id
     * 
     * @param id
     * @return 
     */
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
    
    /**
     * Gets list of all Countries
     * 
     * @return 
     */
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
    public List<Country> getCountriesBySearchText(final String searchText) {
        return repository.getCountriesBySearchText(searchText);
    }
    
    /**
     * Gets a Country by it's ID
     * 
     * @param id
     * @return Country object or null unable to find it
     */
    public Country getCountryById(final int id) {
        return repository.getCountryById(id);
    }
    
    /**
     * Gets all Users
     * 
     * @return list of all Users
     */
    public List<User> getUsers() {
        return repository.getUsers();
    }
    
    /**
     * Gets the last Id entered in the database 
     * 
     * @return 
     */
    public int getLastId() {
        return repository.getLastId();
    }
    
    /**
     * Updates Continent
     * 
     * @param continent
     * @return True/False depending on success in repository
     */
    public boolean updateContinent(final Continent continent) {
        return repository.updateContinent(continent);
    }
    
    /**
     * Updates Country
     * 
     * @param country
     * @return True/False depending on success in repository
     */
    public boolean updateCountry(final Country country) {
        return repository.updateCountry(country);
    }
    
    @Deprecated
    public boolean updateUser(final User user) {
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
