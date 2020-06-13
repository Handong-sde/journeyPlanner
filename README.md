# Assignment 2---Handong Wang-18234796

There are 3 documents in the zip file: 1. ReadMe, 2. DATAtest.json (for user uploading), 3. WorldMap folder (contains the main project)

### Overview project
1. The project name is tutorial4 since I worked on this assignment based on tutorial4's structure. It is using play-framework 1.5.2 MVC model and using db=men (mem : for a transient in memory database (H2 in memory)) to store everything. 
    
   It uses the Json file from Alessandro's assignment1 solution, the josn retrieving code in MapLoader class is based on the tutorial code. The classes in model are mainly based on my assignement1. Design pattern stategy has been showed in the relation of Transpotation and Airport/BusStop/TrainStation.  

2. It is required to upload json file to the web page to get country and city information. The DATAtest.json has been put in the zip file. The format of json is the same as Alessandro provided in his assignment1-solution, while I fill the city population to each city in the json file, and the point of interest is not included in this project as it is not required. 

3. In terms of setting up the project, based on play-framework 1.5.2, play test = unit test; play dependencies = execute dependencies; play run = run the project.

4. The project has a secure page that requires user login first when user wants to upload json fild or access admin page. The login details are not set in the application, so you can login with any username and password.

5. This project is following the concept of MVC model strictly. As showed in the lecture, model, view and controller package have been built with corresponding java classes or html files. Dependencies and routes can be found in conf.

6. Funcional test and modules unit tests have been set up in this project. "/check", "/" and "/planner" are the two functional tests that are chosen to be tested, while in the modules, the functions of creating and retrieving for each country and city entity in the database have been tested.

### Funtions
This project is able to:
1. List all the countries in a table.
2. Select a country from above table, to view its properties:
   * Its population and capital city.
   * List of all of its bordering countries.
   * List all the cities in the country and their properties.
3. Plan a journey between two cities (countries are requested to be chosen as well)

Administrators of the web-based World Atlas is able to:
1. Login to the administration web-page.
2. Load the JSON data about countries and cities.
3. Manually create, update, and/or delete countries and cities.

