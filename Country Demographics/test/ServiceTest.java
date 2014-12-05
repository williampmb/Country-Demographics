import country.demographics.forms.Continent;
import country.demographics.forms.Country;
import country.demographics.forms.User;
import country.demographics.service.Service;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import java.util.TimeZone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * JUnit Test
 * 
 * NOTE: This is really bad to do tests on an our actual database. This isn't
 *  permanent. In the future there will be a mock database. 
 * 
 * @author admin
 */
public class ServiceTest {

    Service service;

    /**
     * This initializes the service
     */
    @Before
    public void startService() {
        service = new Service();
    }
   
    @Test
    public void test_addRemoveUser() {
        User user = new User();
        user.setUsername("user123");
        user.setPassword("pw12345");
        user.setUserType(1);
        
        assertTrue(service.addUser(user));
        
        User retrieved = service.validateUser(user);
        assertNotNull(retrieved);
        
        assertTrue(service.deleteUserById(retrieved.getUserId()));
    }
    
    @Test
    public void test_deleteCountry() {
        Country country = new Country();
        
        country.setName("test");
        country.setTLD("");
        
        Country newcount = service.addCountry(country);
        assertTrue(service.deleteCountryById(newcount.getId()));
    }
    
    @Test
    public void test_getContinentById() {
        Continent continent = service.getContinentById(1);
        assertNotNull(continent);
    }

    /**
     * @see Service#getContinents() 
     */
    @Test
    public void test_getContinents() {
        List<Continent> continents = service.getContinents();
        assertNotNull(continents);
    }
    
    @Test
    public void test_getCountries() {
        List<Country> countries = service.getCountries();
        assertNotNull(countries);
    }

    /**
     * @see Service#getCountriesByContinentId(int) 
     */
    @Test
    public void test_getCountriesByContinentId() {
        List<Country> countries = service.getCountriesByContinentId(1);
        assertNotNull(countries);
    }
    
    @Test
    public void test_getCountriesBySearchText() {
        List<Country> countries = service.getCountriesBySearchText("united stat");
        assertNotNull(countries);
    }
    
    @Test
    public void test_getCountryById() {
        Country country = service.getCountryById(1);
        
        assertNotNull(country);
    }
    
    @Test
    public void test_getLastId() {
        service.getLastId();
    }
    
    @Test
    public void test_getUserById() {
        int id = 1;
        assertNotNull(service.getUserById(id));
    }
    
    @Test
    public void test_getUsers() {
        List<User> users = service.getUsers();
        
        assertNotNull(users);
    }
    
    @Test
    public void test_insertContinent() {
        Continent continent = new Continent();
        
        continent.setName("new cont :]");
        
        int result = service.addContinent(continent);
                
        assertNotEquals(-1, result);
        
        assertTrue(service.deleteContinentById(result));
    }
    
    @Test
    public void test_insertCountry() {
        
        Country country = new Country();
        country.setArea(123);
        country.setContinentId(0);
        country.setCurrency("jim dollars");
        country.setOfficialLanguage("English");
        country.setName("JimLand");
        country.setPopulation(111);
        country.setTLD(".jim"); 
        country.setTimeZone(TimeZone.getTimeZone("Pacific/Apia"));
        
        Country retrieved = service.addCountry(country);
        assertNotNull(retrieved);
        
        service.deleteCountryById(retrieved.getId());
    }

    @Test
    public void test_updateContinent() {
        Continent c = service.getContinentById(1);
        
        String origName = c.getName();
        String newName = "newName";
        c.setName(newName);
        
        assertTrue(service.updateContinent(c));
        
        assertEquals(service.getContinentById(c.getId()).getName(), newName );
        
        c.setName(origName);
        
        service.updateContinent(c);
    }
    
    @Test
    public void test_updateCountry() {
        Country country = service.getCountryById(1);
        
        int area = (int)(Math.random() * 10000.0);
        country.setArea(area);
        
        assertTrue(service.updateCountry(country));
        
        assertEquals(area,service.getCountryById(1).getArea());
    }
    
    @Test
    public void test_updateUser() {
        User user = service.getUsers().get(0);
        
        String origName = user.getUsername();
        
        String newName = "asdfasdfasdf";
        
        user.setUsername(newName);
        
        assertTrue( service.updateUser(user));
        
        User updatedUser = service.getUserById(user.getUserId());
        
        assertEquals(newName, updatedUser.getUsername());
        
        updatedUser.setUsername(origName);
        
        assertTrue(service.updateUser(updatedUser));
        
        updatedUser = service.getUserById(user.getUserId());
    }
    
    /**
     * @see Service#validateUser(country.demographics.forms.User) 
     */
    @Test
    public void test_validateUser() {
        User user = new User();
        user.setUsername("jim");
        user.setPassword(" ");
        user = service.validateUser(user);
        
        assertNotNull(user);
    }
}
