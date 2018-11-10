package services;

import java.util.Random;

import com.google.gson.JsonObject;

import DAO.ProfilDAO;
import converters.JSonConverter;
import models.Personne;
import status.Reponse; 

/**
 *
 * @author Sofiane GHERSA
 */
public class ProfilServices {

	ProfilDAO DAO = new ProfilDAO();
	
// inscription
	public JsonObject Registration(Personne prof) {
		

		if (!IsPresent(prof.getUserAddress())) {
			return JSonConverter.objectToJson(new Reponse("ko", "UserAddress  est obligatoire "));
		}
		if (!IsPresent(prof.getUserMail())) {
			return JSonConverter.objectToJson(new Reponse("ko", "getUserMail  est obligatoire "));
		}
		if (!IsPresent(prof.getUserName())) {
			return JSonConverter.objectToJson(new Reponse("ko", "getUserName  est obligatoire "));
		}
		if (!IsPresent(prof.getUserPassword())) {
			return JSonConverter.objectToJson(new Reponse("ko", "getUserPassword  est obligatoire "));
		}
		if (!IsPresent(prof.getUserPicture())) {
			return JSonConverter.objectToJson(new Reponse("ko", "getUserPicture  est obligatoire "));
		}
		if (!IsPresent(prof.getUserPhone())) {
			return JSonConverter.objectToJson(new Reponse("ko", "getUserPhone  est obligatoire "));
		}

		prof.setUserKey(GenerateKey());

		if (!IsPresent(prof.getUserKey())) {
			return JSonConverter.objectToJson(new Reponse("ko", "erreur lors de generation de key  est obligatoire "));
		}
		
		return JSonConverter.objectToJson(DAO.Registration(prof));
	}
	
	
	// ****** fonction utiles

	private String GenerateKey() {
		Random rand = new Random();
		String alphabet = "0123456789azertyuiopmlkjhgfdsqwxcvbn";
		String key = "";
		int longueur = alphabet.length();
		for (int i = 0; i < 50; i++) {
			int k = rand.nextInt(longueur);
			key = key.concat("" + alphabet.charAt(k));
		}

		return key;
	}

	private boolean IsPresent(String arg) {

		if (arg == null || arg.length() == 0) {
			return false;
		} else {
			return true;
		}

	}

	private boolean IsPresent(Integer arg) {

		if (arg == null) {
			return false;
		} else {
			return true;
		}

	}
}
