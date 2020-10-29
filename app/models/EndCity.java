package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

import java.util.*;

import java.util.HashSet;


import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.junit.validator.PublicClassValidator;

import play.db.jpa.Model;

//fake city class contains values as city
@Entity
public class EndCity extends Model{

	public int cityid;
	public String cityName;
	public int cityPopulation;
	
	

	public EndCity(int cityid, String cityName, int cityPopulation) {
		this.cityid = cityid;
		this.cityName = cityName;
		this.cityPopulation = cityPopulation;


	}

}
