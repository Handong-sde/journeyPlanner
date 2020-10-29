package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import exceptions.DataFormatException;
import play.mvc.Controller;
import play.mvc.With;

import models.*;

@With(Secure.class)
/**
 * MapLoader class is used for creating the function of uploading the JSON file and
 * retrieving data from json and store them to the proper object and then store to database.
 * 
 * The loader page is secured.
 */
public class MapLoader extends Controller {
	public static void loader() {
    	render();
    }
    
	//the method for uploading JSON file by user.
    public static void uploadJSON(File jsonData, boolean clearData) {
    	InputStream input =null;
    	try {
			System.out.println(clearData);
			if (clearData) play.test.Fixtures.deleteDatabase();
			
			input = new FileInputStream(jsonData);
			populateDatabase(input);

			flash.success("File uploaded successfully and database updated");
		} catch (FileNotFoundException fe) {
			flash.error("Failed to locate the uploaded JSON file.");
			fe.printStackTrace();
		} catch (DataFormatException de) {
			flash.error("Data formating error: " + de.getMessage());
			de.printStackTrace();
		} catch (JSONException je) {
			flash.error("JSON library exception raised while loading the file. ");
			je.printStackTrace();
		}finally {
			try {
				if(!(null==input)) input.close();//close JSON file at last
			}catch(IOException e) {
				flash.error("input error, has closed");
			}
		} 
    	loader();
    }

    //retrieving JSON and store to proper objects
	private static void populateDatabase(InputStream input) 
	throws DataFormatException, FileNotFoundException, JSONException {
		
		// Parse the JSON file and make sure it's not completely broken.
		JSONTokener parser = new JSONTokener(input);
		JSONObject data = new JSONObject(parser);

		// Check if file has the key
		if (!data.has("countries"))
			throw new DataFormatException("No key 'countries', nothing I can do with these data.", data);
		
		// Load the array
		JSONArray countriesArray = data.getJSONArray("countries");
		
		// Check if the data for "countries" is a an array
		if (!(countriesArray instanceof JSONArray))
			throw new DataFormatException("Key 'country' must point to a JSON array.", data);
		
		
		// Loop through all countries
		for (int i = 0; i < countriesArray.length(); i++) {
			JSONObject countryJSON = countriesArray.getJSONObject(i);
			
			if (!(countryJSON.has("name") && countryJSON.get("name") instanceof String))
				throw new DataFormatException("All countries must be named.", data);
			
			if (!(countryJSON.has("id") && countryJSON.get("id") instanceof Integer))
				throw new DataFormatException("Missing number for country " + countryJSON.get("id"), data);
			
			if (!(countryJSON.has("capital") && countryJSON.get("capital") instanceof Integer))
				throw new DataFormatException("Missing capital for country " + countryJSON.get("capital"), data);
			
			Integer population = 0;
			Integer countryID = countryJSON.getInt("id");
			String countryName = countryJSON.getString("name");
			Integer capitalID = countryJSON.getInt("capital");
			
			// Check if there is already a country with this number in database
			if (null != Country.find("byCountryID", countryID).first())
				throw new DataFormatException("There is already an existing country with number " + countryID, data);
			
			
			Country country  = new Country(countryID, countryName);
			EndCountry fcountry  = new EndCountry(countryID, countryName);
			country.save();
			fcountry.save();
			//finish retrieving values for country	
			
			//start city
			if (countryJSON.has("cities") && countryJSON.get("cities") instanceof JSONArray) {

				JSONArray citiesArray = countryJSON.getJSONArray("cities");

				for (int j = 0; j < citiesArray.length(); j++) {
					JSONObject cityJSON = citiesArray.getJSONObject(j);

					if (!cityJSON.has("id") && cityJSON.get("id") instanceof Integer)
						throw new DataFormatException("Missing city number for customer " + countryName, data);

					if (!cityJSON.has("name") && cityJSON.get("name") instanceof String)
						throw new DataFormatException("Missing city name for cities " + countryName, data);

					if (!cityJSON.has("population") && cityJSON.get("population") instanceof String)
						throw new DataFormatException("Missing city popularion for cities " + countryName, data);

					Integer cityID = cityJSON.getInt("id");
					String cityName = cityJSON.getString("name");
					Integer cityPopulation = cityJSON.getInt("population");
					
					population += cityPopulation;
					if (null != City.find("byCityID", cityID).first()) {
						throw new DataFormatException("There is already an existing account with number " + cityID, data);
					}

					City city = new City(cityID, cityName, cityPopulation);					
					city.save();
					//put city into country
					country.cityMap.put(cityID, city);
					
					EndCity fcity = new EndCity(cityID, cityName, cityPopulation);
					fcity.save();
					fcountry.cityMap.put(cityID, fcity);
					
					City cap = city.find("byCityID", capitalID).first();
					country.capital = cap;
					fcountry.capital = cap;
					country.countryPopulation = population;
					fcountry.countryPopulation = population;
					country.save();
					fcountry.save();
					//finish retrieving values for city
					
					//retrieving values for interests are not used in this application
//					
//					JSONArray interestArray = cityJSON.getJSONArray("interest");
//					
//					for(int k=0;k<interestArray.length();i++) {
//						JSONObject InterestJSON = interestArray.getJSONObject(j);
//						String type = InterestJSON.getString("type");
//						Interest interest = new Interest(type);
//						city.interest.add(interest);
//					}
					
					
					
				}
			}

			
		}
		// Now that we have all the countries, deal with borders
		if (data.has("bordering")) {
			JSONObject relationsJSON = data.getJSONObject("bordering");
			for (String k : relationsJSON.keySet()) {
				Integer countryID = Integer.parseInt(k);
				Country c = Country.find("byCountryID", countryID).first();
				EndCountry fc = EndCountry.find("byCountryID", countryID).first();

				if (null == c)
					throw new DataFormatException("There is no customer with number " + countryID, data);

				JSONArray relationsArray = relationsJSON.getJSONArray(k);

				for (int i = 0; i < relationsArray.length(); i++) {
					Integer rCountryID = relationsArray.getInt(i);

					Country cr = Country.find("byCountryID", rCountryID).first();
					EndCountry fcr = EndCountry.find("byCountryID", rCountryID).first();

					if (null == cr)
						throw new DataFormatException("There is no country with number " + countryID, data);
					
					//add bordering country to each country
					c.bordering.add(cr);
					fc.bordering.add(fcr);
					c.save();
					fc.save();
				}//finish setting up bordering countries

			}
		}
	}
}
