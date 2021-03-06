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
    
    private static long lastContinentUpdate = -1;
    
    /**
     * Constructor: Initializes the repository
     */
    public Service() {
        repository = new Repository();
        lastContinentUpdate = System.currentTimeMillis();
        
        if(repository == null) {
            Util.log("Error Opening repository");
        }
    }
    
    /**
     * Adds a new continent
     * 
     * @param continent
     * @return continent id of last added continent
     */
    public int addContinent(final Continent continent) {
        int result = repository.insertContinent(continent);
        
        if(result > 0) {
            lastContinentUpdate = System.currentTimeMillis();
        }
        
        return result;
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
        boolean result = repository.deleteContinentById(continentId);

        if(result) {
            lastContinentUpdate = System.currentTimeMillis();
        }
        
        return result;
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
    
    public User getUserById(final int userId) {
        return repository.getUserById(userId);
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
        boolean result = repository.updateContinent(continent);
        
        if(result) {
            lastContinentUpdate = System.currentTimeMillis();
        }
        
        return result;
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
    
    /**
     * Updates a User
     * 
     * @param user
     * @return True/False depending on success in repository
     */
    public boolean updateUser(final User user) {
        return repository.updateUser(user);
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
    
    /**
     * Determines if Controller needs to update its Continent list
     * 
     * @param myLastUpdate
     * @return 
     */
    public static boolean continentUpdatedRequired(final long myLastUpdate) {
        return myLastUpdate < lastContinentUpdate;
    }
}
