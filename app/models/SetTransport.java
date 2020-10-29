package models;

public class SetTransport {

/*This method sets up the journey planner
 * Basically, the program will ask user to input the country name that they would like to travel from and to
 * and the cities that they want to departure and arrival. 
 * If the cities are in the same country, then the program will recommend them to take bus;
 * If the both cities are capitals, then take plane;
* If two countries are bordered and the two cities are not capital cities at the same time(1 capital 1 city or 2 cities), then take train*/
	public static Transport setTransport(Country coun1, Country coun2, City city1, City city2) {

		if(coun1.countryID==coun2.countryID) {
			return new BusStop();
		}
		if(coun1.capital.cityID==city1.cityID && coun2.capital.cityID==city2.cityID) {

			return new Airport();
		}else if((coun1.capital.cityID==city1.cityID || coun2.capital.cityID==city2.cityID)|| 
				coun1.bordering.contains(coun2)) {
			return new TrainStation();
		}
		return null;
	}

	public static String travelTo(Country coun1, Country coun2, City city1, City city2) {
		
		if(coun1.cityMap.containsValue(city1) &&  coun2.cityMap.containsValue(city2)){
			
		
		City city3 = coun1.capital;
		City city4 = coun2.capital;

		//for trips that in one country or both cities are capitals or both countries are bordered
		//traveling with no stops
		if((coun1.countryID==coun2.countryID)
				||((coun1.capital.cityID==city1.cityID && coun2.capital.cityID==city2.cityID))
				||(coun1.bordering.contains(coun2))) {

			Transport trip = setTransport(coun1, coun2, city1, city2);

			return "From " + city1.cityName + " to " + city2.cityName + " " + trip.printStops();
		}
		//for trips between 2 countries that only departure city is capital
		//2 stops
		else if(!coun1.bordering.contains(coun2)&&
				(coun1.capital.cityID==city1.cityID) && !(coun2.capital.cityID==city2.cityID)) {
			Transport trip2 = setTransport(coun1, coun2, city1, city4);
			Transport trip3 = setTransport(coun2, coun2,city4, city2);


			return "From " + city1.cityName + " to " + city2.cityName + ": ---First, from " + city1.cityName+ " to " + city2.cityName + " " + trip2.printStops()
			+ "---Then from " + city4.cityName+ " to " + city2.cityName  + " "+ trip3.printStops();

		}
		//for trips between 2 countries that only arrival city is capital
		//2 stops
		else if(!coun1.bordering.contains(coun2)&&
				!(coun1.capital.cityID==city1.cityID) && (coun2.capital.cityID==city2.cityID)) {
			Transport trip2 = setTransport(coun1, coun1, city1, city3);
			Transport trip3 = setTransport(coun1, coun2,city3, city2);


			return "From " + city1.cityName + " to " + city2.cityName + ": ---First, from " + city1.cityName+ " to " + city3.cityName + " " + trip2.printStops()
			+ "---Then from " + city3.cityName+ " to " + city2.cityName  + " "+ trip3.printStops();

		}
		//for trips between 2 countries and both departure and arrival cities are not capitals
		//1 stops or 3 stops
		else {
			/*For the 2 countries that share the same bordering country, when traveling from a non-capital city to another non-capital city,
			 *in order to make the best route(fewer transfer), taking trains which only needs to transfer once (1 stop) is better then taking plane, which 
			 *needs to transfer twice to bus
			 *
			 *Or if the two cities are far from each other, they have to take plane and transfer to bus (3 stops)*/

			for(Country c:coun2.bordering) {
				//if the 2 countries has a shared bordered country
				if(coun1.bordering.contains(c)) {
					Country coun3 = c;
					City fakeCity = c.capital;
					//creating a fakeCity which is the city in the shared bordered country
					Transport trip4 = setTransport(coun1, coun3, city1, fakeCity);
					Transport trip5 = setTransport(coun3, coun2, fakeCity, city2);

					return "From " + city1.cityName + " to " + city2.cityName + ": ---First, from " + city1.cityName+ " to " 
					+ fakeCity.cityName + "("+ c.countryName +") " + trip4.printStops()
					+ "---Then from " +fakeCity.cityName +"("+ c.countryName +") "+ " to " + city2.cityName  + " "+ trip5.printStops();

				}else {
					Transport trip4 = setTransport(coun1, coun1, city1, city3);
					Transport trip5 = setTransport(coun1, coun2, city3, city4);
					Transport trip6 = setTransport(coun2, coun2, city4, city2);



					return "From " + city1.cityName +  " to " + city2.cityName + ": --First, from " + city1.cityName + " to " + city3.cityName + " "+ trip4.printStops()+ 
							"--Then from " + city3.cityName + " to " + city4.cityName + " "+ trip5.printStops() + 
							"--Then from " + city4.cityName +" to " +  city2.cityName  + " "+ trip6.printStops();
				}

			}
			return null;


		}
	}else {
		return "country/city not match";
	}
	}
}

