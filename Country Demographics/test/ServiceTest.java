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
    public void test_deleteCountry() {
        Country country = new Country();
        
        country.setName("test");
        
        Country newcount = service.addCountry(country);
                
        assertTrue(service.deleteCountryById(newcount.getId()));
    }
    
    @Test
    public void test_getContinentById() {
        Continent continent = service.getContinentById(1);
        assertNotNull(continent);
        System.out.println(continent.toString());
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
        for(Country country : countries) {
            System.out.println(country.toString());
        }
    }

    /**
     * @see Service#getCountriesByContinentId(int) 
     */
    @Test
    public void test_getCountriesByContinentId() {
        List<Country> countries = service.getCountriesByContinentId(1);
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
    public void test_getUsers() {
        List<User> users = service.getUsers();
        
        assertNotNull(users);
        
        for(User user : users) {
            System.out.println(user.toString());
        }
    }
    
    @Test
    public void test_insertContinent() {
        Continent continent = new Continent();
        
        continent.setName("new cont :]");
        
        int result = service.addContinent(continent);
        
        System.out.println("last id result: " + result);
        
        assertNotEquals(-1, result);
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
        
        assertNotNull(service.addCountry(country) );
    }

    @Test
    public void test_updateCountry() {
        Country country = service.getCountryById(1);
        
        country.setArea(999);
        
        assertTrue(service.updateCountry(country));
        
        assertEquals(999,service.getCountryById(1).getArea());
    }
    
    
    /**
     * @see Service#validateUser(country.demographics.forms.User) 
     */
    @Test
    public void test_validateUser() {
        User user = new User();
        user.setUsername("jim");
        user.setPassword("1");
        user = service.validateUser(user);
        
        assertNotNull(user);
    }
}
