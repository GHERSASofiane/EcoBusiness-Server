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

		models.Personne product = new models.Personne();
		product = (models.Personne) JSonConverter.objectFromJson(jsObj, product);
		product.setUserKey(GenerateKey());
		
		// Envoie de réponse
		resp.println(JSonConverter.objectToJson(new Reponse("ok", product)));
		resp.flush();
	}
	
	private String GenerateKey() {
		Random rand = new Random();
		String alphabet = "0123456789azertyuiopmlkjhgfdsqwxcvbn";
		String key ="";
		int longueur = alphabet.length();
		for(int i = 0; i < 50; i++) {
		  int k = rand.nextInt(longueur);
		  key = key.concat(""+alphabet.charAt(k));
		}
		
		return key;
	}
	
}
