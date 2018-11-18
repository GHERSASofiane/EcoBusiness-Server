//	ajouetr supprimer  un utilisateur
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import converters.JSonConverter;
import emailOperations.EmailVerification;
import helpers.Readers;
import models.Personne;
import services.ProfilServices;
import status.Reponse;
import tokens.AutorisationAcess;

/**
 *
 * @author Sofiane GHERSA
 */
public class ADSProfil extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ADSProfil() {
		super();
	}
	
//	crée un compte
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JsonObject jsObj = Readers.getJSONfromRequest(request);
		 
		 Personne personne = new Personne();
		 personne = (Personne) JSonConverter.objectFromJson(jsObj, personne);
		  
		 JsonObject obj;
		 
		 try
		 {
		 if(EmailVerification.isAddressValid(personne.getUserMail())) {
			 ProfilServices pers = new ProfilServices();
			 obj = pers.signUp(personne);
		 }
		 else
		 {
			 obj = JSonConverter.objectToJson(new Reponse("ko", personne.getUserMail() + "not vailde"));
		 }
		 } catch(Exception e)
		 {
			 obj = JSonConverter.objectToJson(new Reponse("ko", personne.getUserMail() + "not vailde")); 
		 }
		  
		
		PrintWriter pw = response.getWriter();
		
		
		
		String token = AutorisationAcess.registerToken(personne);
		obj.addProperty("token", token);
		pw.println(obj);
		pw.flush();
		
		
		 
	}

	
}
