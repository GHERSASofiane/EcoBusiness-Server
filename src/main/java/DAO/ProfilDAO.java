package DAO;

import java.sql.Connection;

import com.google.gson.JsonObject;

import models.Personne;
import status.Reponse;

/**
 *
 * @author Sofiane GHERSA
 */
public class ProfilDAO {

	private Connection db;
	
//	inscription
	public Reponse Registration(Personne prof) {
		return new Reponse("ok", prof);
		
	}
	
}
