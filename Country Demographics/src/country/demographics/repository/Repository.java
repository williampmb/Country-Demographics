/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics.repository;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
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
    
    public List<Country> getCountriesByContinentId(final int continentId) {
        List<Country> countryList = new ArrayList<>();
        
        String sql = "SELECT * FROM countries WHERE cont_id=1";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setInt(1, continentId);

            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                Country country = new Country();
                country.setArea(resultSet.getInt("count_area"));
                country.setContinentId(resultSet.getInt("cont_id"));
                country.setCurrency(resultSet.getString("count_currency"));
                country.setId(resultSet.getInt("count_id"));
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
    
    public boolean insertCountry(final Country country) {
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
            statement.setString(5, country.getTimeZone().getID());
            statement.setString(6, country.getCurrency());
            statement.setString(7, country.getTLD());
            statement.setInt(8, country.getContinentId());

            result = statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        return  result > 0;
    }
    
    public User validatateUser(final User form) {
        User user = null;
        String sql = "SELECT uid, username, user_type FROM users WHERE BINARY username=? and BINARY password=? LIMIT 0, 1";
        
        try { 
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, form.getUsername());
            statement.setString(2, form.getPassword());

            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("uid"));
                user.setUsername(resultSet.getString("username"));
                user.setUserType(resultSet.getInt("user_type"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
        
        return user;
    }
    
    
}
