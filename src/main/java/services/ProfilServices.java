package services;

import java.util.Random;

import com.google.gson.JsonObject;

import DAO.ProfilDAO;
import converters.JSonConverter;
import emailOperations.EmailVerification;
import models.Personne;
import status.Reponse;
import tokens.AutorisationAcess; 

/**
 *
 * @author Sofiane GHERSA
 */
public class ProfilServices {

	ProfilDAO DAO = new ProfilDAO();
	
	// inscription
		public JsonObject Registration(Personne prof) {
			

			if (!IsPresent(prof.getUserName()) || (prof.getUserName().length()<3) ) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserName  est obligatoire "));
			}
			if (!IsPresent(prof.getUserMail())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserMail  est obligatoire "));
			}
			if (!IsPresent(prof.getUserPassword()) || (prof.getUserPassword().length()<6) ) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserPassword  est obligatoire "));
			}
			if (!IsPresent(prof.getUserAddress())) {
				return JSonConverter.objectToJson(new Reponse("ko", "UserAddress  est obligatoire "));
			}
			if (!IsPresent(prof.getUserPhone())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserPhone  est obligatoire "));
			}
			if (!IsPresent(prof.getUserPicture())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserPicture  est obligatoire "));
			}

			prof.setUserKey(GenerateKey());

			if (!IsPresent(prof.getUserKey())) {
				return JSonConverter.objectToJson(new Reponse("ko", "erreur lors de generation de key  est obligatoire "));
			}
			
			return JSonConverter.objectToJson(DAO.Registration(prof));
		}
		
		// connexion
			public JsonObject Login(Personne prof) {
				

				if (!IsPresent(prof.getUserPassword()) || (prof.getUserPassword().length()<6) ) {
					return JSonConverter.objectToJson(new Reponse("ko", "getUserPassword  est obligatoire "));
				}
				if (!IsPresent(prof.getUserMail())) {
					return JSonConverter.objectToJson(new Reponse("ko", "getUserMail  est obligatoire "));
				} 
				
				Reponse reponse = DAO.Login(prof);
				
				if(reponse.getStatus().equals("ok"))
				{
					JsonObject res = JSonConverter.objectToJson(reponse);
					String token = AutorisationAcess.registerToken((Personne) reponse.getReponse());
					res.addProperty("token", token);
					return res;
				}
				
				return JSonConverter.objectToJson(reponse);
			}
	
		public JsonObject signUp(Personne prof)
		{
			if (!IsPresent(prof.getUserPassword()) || (prof.getUserPassword().length()<6) ) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserPassword  est obligatoire "));
			}
			
			if (IsPresent(prof.getUserMail())) {
				if(!EmailVerification.isAddressValid(prof.getUserMail()))
					return JSonConverter.objectToJson(new Reponse("ko", prof.getUserMail() + " est invalide"));
			}
			else
			return JSonConverter.objectToJson(new Reponse("ko", "getUserMail  est obligatoire "));
			
			if (!IsPresent(prof.getUserName())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserName  est obligatoire "));
			}
			else
				if(prof.getUserName().length() < 4)
					return JSonConverter.objectToJson(new Reponse("ko", "UserName doit avoir au minimum 4 caracteres"));
					
			
			if (!IsPresent(prof.getUserPhone())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserPhone  est obligatoire "));
			} 
			else
				if(prof.getUserPhone().length() < 8)
					return JSonConverter.objectToJson(new Reponse("ko", "UserPhone doit avoir au minimum 8 caracteres"));
			
			if (!IsPresent(prof.getUserPicture())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserPicture  est obligatoire "));
			} 
			
			if (!IsPresent(prof.getUserAddress())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserAddress  est obligatoire "));
			} 
			
			
			prof.setUserKey(GenerateKey());
			Reponse reponse = DAO.Registration(prof);
			
			if(reponse.getStatus().equals("ok"))
			{
				JsonObject res = JSonConverter.objectToJson(reponse);
				String token = AutorisationAcess.registerToken((Personne) reponse.getReponse());
				res.addProperty("token", token);
				return res;
			}
			
			return JSonConverter.objectToJson(reponse);
			
			
			
		}
		
		
		public JsonObject update(Personne prof)
		{
			if (!IsPresent(prof.getUserPassword()) || (prof.getUserPassword().length()<6) ) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserPassword  est obligatoire "));
			}
			
			if (IsPresent(prof.getUserMail())) {
				if(!EmailVerification.isAddressValid(prof.getUserMail()))
					return JSonConverter.objectToJson(new Reponse("ko", prof.getUserMail() + " est invalide"));
			}
			else
			return JSonConverter.objectToJson(new Reponse("ko", "getUserMail  est obligatoire "));
			
			if (!IsPresent(prof.getUserName())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserName  est obligatoire "));
			}
			else
				if(prof.getUserName().length() < 4)
					return JSonConverter.objectToJson(new Reponse("ko", "UserName doit avoir au minimum 4 caracteres"));
					
			
			if (!IsPresent(prof.getUserPhone())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserPhone  est obligatoire "));
			} 
			else
				if(prof.getUserPhone().length() < 8)
					return JSonConverter.objectToJson(new Reponse("ko", "UserPhone doit avoir au minimum 8 caracteres"));
			
			if (!IsPresent(prof.getUserPicture())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserPicture  est obligatoire "));
			} 
			
			if (!IsPresent(prof.getUserAddress())) {
				return JSonConverter.objectToJson(new Reponse("ko", "getUserAddress  est obligatoire "));
			} 
			
			prof.setUserKey(GenerateKey());
			Reponse reponse = DAO.update(prof);
			
			if(reponse.getStatus().equals("ok"))
			{
				JsonObject res = JSonConverter.objectToJson(reponse);
				String token = AutorisationAcess.registerToken((Personne) reponse.getReponse());
				res.addProperty("token", token);
				return res;
			}
			
			return JSonConverter.objectToJson(reponse);
			
			
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
