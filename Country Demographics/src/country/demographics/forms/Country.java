package country.demographics.forms;

import java.util.TimeZone;
import javafx.scene.image.Image;

/**
 *
 * @author Jim Lexen
 */
public class Country {

    private int id;
    private String name;
    private long population;
    private int area;
    private String officialLanguage;
    private TimeZone timeZone;
    private String currency;
    private String tld;
    private int continentId;
    private String flag;

    public String getFlag() {
        return flag;
    }

    public int getId() {
        return id;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getTLD() {
        return tld;
    }
    
    public void setTLD(final String tld) {
        this.tld = tld;
    }
    
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    
    public long getPopulation() {
        return population;
    }
    
    public void setPopulation(final long population) {
        this.population = population;
    }

    public int getArea() {
        return area;
    }
    
    public void setArea(final int area) {
        this.area = area;
    }
    
    public String getOfficialLanguage() {
        return officialLanguage;
    }
    
    public void setOfficialLanguage(final String officialLanguage) {
        this.officialLanguage = officialLanguage;
    }
    
    public TimeZone getTimeZone() {
        return timeZone;
    }
    
    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(final String currency) {
        this.currency = currency;
    }
    
    public int getContinentId() {
        return continentId;
    }

    public void setContinentId(final int continentId) {
        this.continentId = continentId;
    }
    
    public String toString() {
        return getName();
    }
    
    public String toStringData() {
        return "Country: id=" + id + ", name=" + name + ", area=" + area 
                + ", officialLanguage=" + officialLanguage + ", timezone=" + timeZone
                + ", currency=" + currency + ", tld=" + tld 
                + ", continentId=" + continentId;
    }
}