package controllers;
 
import models.City;
import play.*;
import play.mvc.*;

@With(Secure.class)
@CRUD.For(City.class)
public class Cities extends CRUD {    
}