package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.junit.validator.PublicClassValidator;

import play.db.jpa.Model;
//not used in this application
@Entity
public class Interest extends Model{
	public String type;
	
	public Interest(String type) {
		this.type = type;

	}

}
