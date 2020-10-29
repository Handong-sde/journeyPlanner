package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.junit.validator.PublicClassValidator;

import play.db.jpa.Model;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //Important for ensure integrity constraints
public class Country extends Model{
	
	public Integer countryID;
	public String countryName;
	public Integer countryPopulation;
	
	@OneToOne
	public City capital;
	
	@OneToMany
	public Map<Integer, City> cityMap;  

	@ManyToMany 
	public Set<Country> bordering;
	
	public Country(Integer countryID, String countryName) {
		this.countryID = countryID;
		this.countryName = countryName;
		this.cityMap = new HashMap<>();
		this.bordering = new HashSet<>();
	}

}
