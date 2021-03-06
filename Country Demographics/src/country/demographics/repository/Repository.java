/**
 * Repository to interact with database
 * 
 */
package country.demographics.repository;

import com.mysql.jdbc.StringUtils;
import country.demographics.forms.Continent;
import country.demographics.forms.Country;
import country.demographics.forms.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import country.demographics.util.Util;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 *
 * @author James Lexen
 */
public class Repository {

    private final static String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private final static String MYSQL_URL = "jdbc:mysql://localhost:3306/demographics";
    private final static String MYSQL_USER = "root";
    private final static String MYSQL_PW = "";

    private Connection connection;

    public Repository() {

        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            Util.log("Unable to find driver");
            return;
        }

        connection = null;

        try {
            connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PW);

        } catch (SQLException e) {
            Util.log("Connection failed to " + MYSQL_URL + ", with user " + MYSQL_USER);
        }
    }
    
    /**
     * Creates a user in the database with username, password and usertype from
     *  passed User object
     * 
     * @param user
     * @return True/False depending if update was successful
     */
    public boolean createUser(final User user) {
        String sql = "INSERT INTO users (username, password, user_type) "
                + "VALUES (?, ?, ?)";
        
        int result = 0;
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, user.getUsername());
            String actualPassword;
            
            if(!StringUtils.isEmptyOrWhitespaceOnly(user.getPassword())) {
                actualPassword = Util.encrypt(user.getPassword());
            } else {
                actualPassword = "";
            }
            
            
            statement.setString(2, actualPassword);
            statement.setInt(3, user.getUserType());
            
            result = statement.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result > 0;
        
    }
    
    /**
     * Deletes a Country by its ID
     * 
     * @param continentId
     * @return True/False
     */
    public boolean deleteContinentById(final int continentId) {
        String sql = "DELETE FROM continents WHERE cont_id=?";
        
        int result;
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, continentId);
            
            result = statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        return result > 0;
    }
    
    /**
     * Deletes a Country by it's ID
     * 
     * @param countryId
     * @return True/False depending on whether or not it was successful
     */
    public boolean deleteCountryById(final int countryId) {
        String sql = "DELETE FROM countries WHERE count_id=?";
        
        int result;
        
        try{ 
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, countryId);

            result = statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        return result > 0;
    }
    
    /**
     * Deletes a User by its ID
     * 
     * @param userId
     * @return True/False
     */
    public boolean deleteUserById(final int userId) {
        String sql = "DELETE FROM users WHERE uid=?";
        
        int result = 0;
        
        try {
            PreparedStatement statement = connection.prepareCall(sql);
            
            statement.setInt(1, userId);
            
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result > 0; 
    }
    
    /**
     * Gets a Continent by its Id
     * 
     * @param id
     * @return Continent object or null if an error ocurs.
     */
    public Continent getContinentById(final int id) {
        String sql = "SELECT * FROM continents WHERE cont_id=?";
        
         try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Continent continent = new Continent();
                continent.setId(result.getInt("cont_id"));
                continent.setName(result.getString("cont_name"));
                return continent;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets all Continents
     * 
     * @return 
     */
    public List<Continent> getContinents() {
        List<Continent> continents = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM continents";

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Continent continent = new Continent();
                continent.setId(result.getInt("cont_id"));
                continent.setName(result.getString("cont_name"));
                continents.add(continent);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return continents;
    }
    
    /**
     * Gets all Countries
     * 
     * @return 
     */
    public List<Country> getCountries() {
        List<Country> countryList = new ArrayList<>();

        String sql = "SELECT * FROM countries";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Country country = new Country();
                country.setArea(resultSet.getInt("count_area"));
                country.setContinentId(resultSet.getInt("cont_id"));
                country.setCurrency(resultSet.getString("count_currency"));
                country.setId(resultSet.getInt("count_id"));
                country.setFlag(resultSet.getString("count_flag"));
                country.setName(resultSet.getString("count_name"));
                country.setOfficialLanguage(resultSet.getString("count_language"));
                country.setPopulation(resultSet.getInt("count_pop"));
                country.setTimeZone(TimeZone.getTimeZone(resultSet.getString("count_timezone")));
                country.setTLD(resultSet.getString("count_tld"));
                countryList.add(country);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countryList;
    }

    /**
     * Gets a list of Countries for a specific Continent
     * 
     * @param continentId
     * @return 
     */
    public List<Country> getCountriesByContinentId(final int continentId) {
        List<Country> countryList = new ArrayList<>();

        String sql = "SELECT * FROM countries WHERE cont_id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, continentId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Country country = new Country();
                country.setArea(resultSet.getInt("count_area"));
                country.setContinentId(resultSet.getInt("cont_id"));
                country.setCurrency(resultSet.getString("count_currency"));
                country.setId(resultSet.getInt("count_id"));
                country.setFlag(resultSet.getString("count_flag"));
                country.setName(resultSet.getString("count_name"));
                country.setOfficialLanguage(resultSet.getString("count_language"));
                country.setPopulation(resultSet.getLong("count_pop"));
                country.setTimeZone(TimeZone.getTimeZone(resultSet.getString("count_timezone")));
                country.setTLD(resultSet.getString("count_tld"));
                countryList.add(country);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countryList;
    } 
    
    /**
     * Function for searching for Countries by name
     * 
     * @param searchText
     * @return 
     */
    public List<Country> getCountriesBySearchText(final String searchText) {
        List<Country> countryList = new ArrayList<>();
        
        String sql = "SELECT * FROM countries WHERE count_name like ?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, "%" + searchText + "%");
            ResultSet resultSet = statement.executeQuery();
            
                        while (resultSet.next()) {
                Country country = new Country();
                country.setArea(resultSet.getInt("count_area"));
                country.setContinentId(resultSet.getInt("cont_id"));
                country.setCurrency(resultSet.getString("count_currency"));
                country.setId(resultSet.getInt("count_id"));
                country.setFlag(resultSet.getString("count_flag"));
                country.setName(resultSet.getString("count_name"));
                country.setOfficialLanguage(resultSet.getString("count_language"));
                country.setPopulation(resultSet.getLong("count_pop"));
                country.setTimeZone(TimeZone.getTimeZone(resultSet.getString("count_timezone")));
                country.setTLD(resultSet.getString("count_tld"));
                countryList.add(country);
            }
                        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return countryList;
    }
    
    /**
     * Gest a Country by its ID
     * 
     * @param id
     * @return Country object or null if there is an error
     */
    public Country getCountryById(final int id) {
        
        String sql = "SELECT * FROM countries WHERE count_id=?"; 
                
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Country country = new Country();
                country.setArea(resultSet.getInt("count_area"));
                country.setContinentId(resultSet.getInt("cont_id"));
                country.setCurrency(resultSet.getString("count_currency"));
                country.setId(resultSet.getInt("count_id"));
                country.setFlag(resultSet.getString("count_flag"));
                country.setName(resultSet.getString("count_name"));
                country.setOfficialLanguage(resultSet.getString("count_language"));
                country.setPopulation(resultSet.getInt("count_pop"));
                country.setTimeZone(TimeZone.getTimeZone(resultSet.getString("count_timezone")));
                country.setTLD(resultSet.getString("count_tld"));
                
                return country;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets a Country's flag path
     * 
     * @param countryId
     * @return 
     */
    public String getFlagByCountryId(final int countryId) {
        String sql = "SELECT flag_path FROM flags INNER JOIN countries "
                + "WHERE flags.flag_id = countries.flag_id AND countries.count_id = ?";
        
        String path = null;
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, countryId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                path = resultSet.getString("flag_path");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return path;
    }
    
        public User getUserById(final int userId) {
        String sql = "SELECT username, user_type FROM users where uid=?";
        
        User user = null;
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                user = new User();
                user.setUserId(userId);
                user.setUsername(resultSet.getString("username"));
                user.setUserType(resultSet.getInt("user_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
        return user;
    }
        
    /**
     * Gets list of all Users
     * Objects do not contain passwords
     * 
     * @return 
     */
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        
        String sql = "SELECT uid, username, user_type FROM users";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("uid"));
                user.setUsername(resultSet.getString("username"));
                user.setUserType(resultSet.getInt("user_type"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
        return users;
    }
    
    /**
     * Inserts a Continent
     * 
     * @param continent
     * @return ID of recently inserted Continent
     */
    public int insertContinent(final Continent continent) {
        String sql = "INSERT INTO continents (cont_name) VALUES (?)";
        
        int result = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, continent.getName());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(result > 0) {
            return getLastId();
        } else {
            return -1;
        }
    }

    /**
     * INserts a Country
     * 
     * @param country
     * @return Country object inserted with its newly created ID or null
     */
    public Country insertCountry(final Country country) {
        String sql = "INSERT INTO countries (count_name, count_pop, count_area, "
                + "count_language, count_timezone, count_currency, count_tld, cont_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int result = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, country.getName());
            statement.setLong(2, country.getPopulation());
            statement.setInt(3, country.getArea());
            statement.setString(4, country.getOfficialLanguage());
            try{    
                statement.setString(5, country.getTimeZone().getID());
            } catch(NullPointerException e) {
                statement.setString(5, "");
            }
                
            statement.setString(6, country.getCurrency());
            
            if(StringUtils.isEmptyOrWhitespaceOnly(country.getTLD())) {
                statement.setString(7, country.getTLD());
            } else {
                statement.setString(7, country.getTLD().toLowerCase());
            }            
            
            statement.setInt(8, country.getContinentId());
            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        if(result > 0) {
            return getCountryById(getLastId());
        } 
        
        return null;
    }

    /**
     * Updates a Continent. 
     * Assumes Continent has been created. 
     * 
     * @param continent
     * @return 
     */
    public boolean updateContinent(final Continent continent) {
        
        String sql = "UPDATE continents SET cont_name = ? WHERE cont_id = ?";
        
        int result = 0;
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, continent.getName());
            statement.setInt(2, continent.getId());
            
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result > 0;
    }
    
    /**
     * Updates a Country.
     * Assumes Country has been created. 
     * 
     * @param country
     * @return 
     */
    public boolean updateCountry(final Country country) {
                
        String sql = "UPDATE countries SET count_name=?, count_pop=?, count_area=?, "
                + "count_language=?, count_timezone=?, count_currency=?, count_tld=?, "
                + "cont_id=?, count_flag=? WHERE count_id=?";
        
        int result = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, country.getName());
            statement.setLong(2, country.getPopulation());
            statement.setInt(3, country.getArea());
            statement.setString(4, country.getOfficialLanguage());
            statement.setString(5, country.getTimeZone().getID());
            statement.setString(6, country.getCurrency());
            
            if(StringUtils.isEmptyOrWhitespaceOnly(country.getTLD())) {
                statement.setString(7, country.getTLD());
            } else {
                statement.setString(7, country.getTLD().toLowerCase());
            }
            
            statement.setInt(8, country.getContinentId());
            statement.setString(9, country.getFlag());  
            statement.setInt(10, country.getId());  

            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return result > 0;
    }
    
    /**
     * Updates a User
     * Assumes the User has been created
     * 
     * @param user
     * @return 
     */
    public boolean updateUser(final User user) {
        
        String sql;
        
        if(StringUtils.isEmptyOrWhitespaceOnly(user.getPassword())) {
            sql = "UPDATE users SET username=?, user_type=? WHERE uid=?";
        } else {
            sql = "UPDATE users SET username=?, user_type=?, password=? WHERE uid=?";
        }
        
        int result = 0;
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, user.getUsername());
            statement.setInt(2, user.getUserType());
            
            if(!StringUtils.isEmptyOrWhitespaceOnly(user.getPassword())) {      
                statement.setString(3, Util.encrypt(user.getPassword()));
                statement.setInt(4, user.getUserId());
            } else {
                statement.setInt(3, user.getUserId());
            }
            
            result = statement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return result > 0;
    }

    /**
     * Validates a User with a given username and password
     * 
     * @param form
     * @return 
     */
    public User validatateUser(final User form) {
        User user = null;
        String sql = "SELECT uid, username, user_type FROM users WHERE BINARY username=? and BINARY password=? LIMIT 0, 1";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
                        
            String encrypted = Util.encrypt(form.getPassword());
            
            statement.setString(1, form.getUsername());
            statement.setString(2, encrypted);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("uid"));
                user.setUsername(resultSet.getString("username"));
                user.setUserType(resultSet.getInt("user_type"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 

        return user;
    }

    /**
     * Gets last ID updated in database
     * 
     * @return 
     */
    public int getLastId() {
        String sql = "SELECT LAST_INSERT_ID()";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                return resultSet.getInt("LAST_INSERT_ID()");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
}