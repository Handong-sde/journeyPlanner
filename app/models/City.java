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

@Entity
public class City extends Model{

	public int cityID;
	public String cityName;
	public int cityPopulation;
	

	public City(int cityID, String cityName, int cityPopulation) {
		this.cityID = cityID;
		this.cityName = cityName;
		this.cityPopulation = cityPopulation;


	}

}
