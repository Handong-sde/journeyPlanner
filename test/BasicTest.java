import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;


//action unit testing for retrieving and creating city and country
public class BasicTest extends UnitTest {
	
	// This function will first in this test
	@Before
    public void setup() {
		// Clear the database
        Fixtures.deleteDatabase();
    }
	
	
    @Test
    public void createAndRetrieveCity() {
    	// Create an city
    	City a = new City(12345, "aaaa", 1000);
    	
    	// Save to the database
    	a.save();
		
		// Find the city with "cityID=12345"
    	City acc = City.find("byCityID", 12345).first();
    	
    	// Check that the city exists 
		assertNotNull(acc);
		
		// Check if the cityID of found city matches with 12345
    	assertEquals((Integer)12345, (Integer)acc.cityID);
    }
    
    @Test
    public void createAndRetrieveCountry() {
		// Create and save an city
		City a = new City(678, "bbbbb", 2000).save();

		// Create and save a country
    	Country c = new Country(1, "NoName").save();
		c.cityMap.put(1, a);
		// Find the country with "countryID=1"
    	Country cust = Country.find("byCountryID", 1).first();
		
		// Check that the country exists
		assertNotNull(cust);
		
		// Check if the city of found country is same as the city created above
		assertEquals((Integer)cust.cityMap.get(1).cityID,(Integer)678);

		// Check if the countryID of found country matches with 1
		assertEquals(cust.countryID, (Integer)1);

		// Check if the country name of found country matches with "NoName"
		assertEquals(cust.countryName, "NoName");
    }
}
