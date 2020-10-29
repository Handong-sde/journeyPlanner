package controllers;

import play.*;


import play.mvc.*;
import play.data.validation.*;
import play.db.jpa.GenericModel.JPAQuery;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import models.*; 
import static java.lang.Math.*;

public class Application extends Controller {

	/**
	 * Action method for index.html. On the home page, there will be only a list of countries,
	 * and each country can be clicked on and details of country will be showed.
	 */
	public static void index() {

		List<Country> countries = Country.findAll();
		render(countries);
	}

	/**
	 * Action method for check.html, and a method for posting cities and countries to the web page. 
	 * postCountryAndCity method will be called in check.html and then a list of countries and a list 
	 * of cities will be showed on web page, then select a city and a country to show their relationship.
	 */
	public static void check() {
		List<City> cities = City.findAll();
		List<Country> countries = Country.findAll();

		render(cities, countries);
	}
	public static void postCountryAndCity(@Required Long cityID, @Required Long countryID) {
		Country country = Country.findById(countryID);
		City city = City.findById(cityID);

		if (validation.hasErrors()) {
			render("Application/check.html");
		}

		if (country.cityMap.containsValue(city)) {
			flash.success(country.countryName + " is the owner of city: " + city.cityID + ", " + city.cityName);
		} else {
			flash.error(country.countryName + " is NOT the owner of city: " + city.cityID + ", " + city.cityName);
		}
		check();
	}


	/**
	 * Action method for show.html. 
	 * Appication.show method will be called when there is a country displayed on the web page. This method
	 * enables showing details of a country by clicking on the country name. Then, country properties like,
	 * capital, city list, bordering country list and population will be showed.
	 */
	public static void show(@Required Long countryID) {

		Country country = Country.find("byCountryID", Math.toIntExact(countryID)).first();

		Collection<City> cities = country.cityMap.values();
		Set<Country> bordering = country.bordering;

		render(country, cities, bordering);
	}
	/**
	 * Action method for showCity.html. 
	 * It is basically same as show method above, but this time, the details of a city will be showed.
	 * Since the shortage of time and the pointOfInterest is not necessary in this application, showCity method 
	 * will not show anything since the interests are not included in json datasheet and the retrieving method for
	 * interests that is built in MapLoader class has been commented.

	 */
	public static void showCity(@Required Long cityID) {

		City city = City.find("byCityID", Math.toIntExact(cityID)).first();

		render(city);
	}
	
	/**
	 * Action method for planner.html. And the planJourney method that is showed below is used to call the travelTo
	 * method to create the plan.
	 * 
	 * Journey planner function includes classes of Airport, BusStop, TrainStation, Transport and SetTransport.
	 * 
	 * In my Journey Planner function, user will be provided with four list on the page, two of them for departure
	 * country and city, two of them for arrival country and city (City & Country classes are for Departure places; 
	 * EndCity&EndCountry are for arrival places). User should fill in all fields and if country
	 * and city are not matched, there will be an error message to let you reenter your choice.
	 * 
	 * In my opinion, it is better and more user friendly to let user to choose country first and then city.
	 * 
	 * The design of journey plan involves building extra two end classes both for country and city. These two classes contain
	 * another two primary key of countryID and cityID and other properties are same as normal country and city. 
	 * In the MapLoader class, both normal and end country and city will be given same value from JSOP data so that showing 
	 * the choices of both departure country&city and arrival country&city on the web page at the same time will be possible.
	 *
	 * This function will provide error message to user that whether their departure or arrival country/city is not matched, then
	 * user can choose country city again. (result will be green if no error found, but becomes red when there's an mismatch)
	 */
	public static void planner() {
		List<City> cities = City.findAll();
		List<Country> countries = Country.findAll();
		List<EndCity> fcities = EndCity.findAll();
		List<EndCountry> fcountries = EndCountry.findAll();

		render(cities, countries, fcities, fcountries);
	}

	public static void planJourney(Long cityID, Long cityid, Long countryid, Long countryID) {
		Country country1 = Country.findById(countryID);
		Country country2 = Country.findById(countryid);

		City city1 = City.findById(cityID);
		City city2 = City.findById(cityid);

		SetTransport set1 = new SetTransport();
		set1.travelTo(country1, country2, city1, city2);

		if(country1.cityMap.containsValue(city1) && country2.cityMap.containsValue(city2)) {
		flash.success("start city: " + country1.countryName + ", " + city1.cityName + 
					"--------------------------------------------------------------> "
					+ "arrival city: " + country2.countryName + ", " + city2.cityName
					+ ".------------------------------------------------------------> "
					+ "plan: " + set1.travelTo(country1, country2, city1, city2));

		}else if(country1.cityMap.containsValue(city1) && !(country2.cityMap.containsValue(city2))){
			flash.error("Arrival Country/City not matched");

		}else if(!(country1.cityMap.containsValue(city1)) && (country2.cityMap.containsValue(city2))){
			flash.error("Departure Country/City not matched");
		}	
			planner();

	}



}