//	ajouetr supprimer et connecter un utilisateur
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
import helpers.Readers;
import services.ProfilServices;
import status.Reponse;

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
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();

		JsonObject jsObj = Readers.getJSONfromRequest(request);

		models.Personne prof = new models.Personne();
		prof = (models.Personne) JSonConverter.objectFromJson(jsObj, prof); 
		
		ProfilServices PrServ = new ProfilServices();
		// Envoie de réponse
		resp.println(PrServ.Registration(prof));
		resp.flush();
	}

	
}
