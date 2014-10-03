import country.demographics.forms.Country;
import country.demographics.forms.User;
import country.demographics.service.Service;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import java.util.TimeZone;
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

    /**
     * @see Service#getContinents() 
     */
    @Test
    public void test_getContinents() {
        assertNotNull(service.getContinents());
    }

    /**
     * @see Service#getCountriesByContinentId(int) 
     */
    @Test
    public void test_getCountriesByContinentId() {
        List<Country> countries = service.getCountriesByContinentId(1);
        System.out.println(countries.get(0).toString());
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
        
        assertTrue( service.addCountry(country) );
    }

    /**
     * @see Service#validateUser(country.demographics.forms.User) 
     */
    @Test
    public void test_validateUser() {
        User user = new User();
        user.setUsername("jim");
        user.setPassword("usaisbetter");
        user = service.validateUser(user);
        
        assertNotNull(user);
    }
}
