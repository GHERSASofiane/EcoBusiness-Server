
// Class pour la validation de l'achat par le client

package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import converters.JSonConverter;
import helpers.Readers;
import services.ProductServices;
import status.Reponse;
import tokens.AutorisationAcess;

/**
 *
 * @author Sofiane GHERSA
 */
public class Buy extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Buy() {
		super();
	}
	

	//**************************************************	méthode pour la validation 
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// Récuperer le PrintWriter Pour envoyer la réponse
			PrintWriter resp = response.getWriter();
			
			
			
	        JsonObject result = new JsonObject();
	        
	        if(!AutorisationAcess.isTokenExist(request))
	        {
	        	result = JSonConverter.objectToJson(new Reponse("ko", "user not logged in"));
	        }
	        else
	        {
	        
	        	// transférer les données de la requête en Json
	    		JsonObject jsObj = Readers.getJSONfromRequest(request);
	    		// extraire les données qu'on a besoin
	    		int id  = 0 ;
	    		id = (Integer) JSonConverter.objectFromJson(jsObj, id);

	    		// Préparer la réponse
	    		ProductServices rep = new ProductServices();
	    		
	    		result = rep.Buy(id);
	        }
	        
			
			
			
			// Envoie de réponse au client
			resp.println(result);
			resp.flush();

		}
	
}
