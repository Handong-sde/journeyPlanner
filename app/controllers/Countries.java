package controllers;
 
import models.Country;
import play.*;
import play.mvc.*;

@With(Secure.class)
@CRUD.For(Country.class)
public class Countries extends CRUD {    
}