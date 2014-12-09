package country.demographics.forms;

/**
 * Continent Class
 * 
 * @author Jim Lexen
 */
public class Continent {
    private int id;
    private String name;
    
    public int getId() {
        return id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String toString() {
        return  name;
    }
}
