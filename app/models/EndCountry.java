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
//fake country class contains the same values a country
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EndCountry extends Model{
	
	public Integer countryid;
	public String countryName;
	public Integer countryPopulation;
	
	@OneToOne
	public City capital;
	
	@OneToMany
	public Map<Integer, EndCity> cityMap;  

	@ManyToMany 
	public Set<EndCountry> bordering;
	
	public EndCountry(Integer countryid, String countryName) {
       
		this.countryid = countryid;
		this.countryName = countryName;
		this.cityMap = new HashMap<>();
		this.bordering = new HashSet<>();
	}

}

