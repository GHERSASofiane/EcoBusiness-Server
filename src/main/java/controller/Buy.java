
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
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
	    		String ID = request.getParameter("id"); 
	    		if (ID == null) {
	    			ID = "0";
	    		}

	    		ID = ID.toLowerCase();
	    		int id = Integer.parseInt(ID); 

	    		// Préparer la réponse
	    		ProductServices rep = new ProductServices();
	    		
	    		result = JSonConverter.objectToJson( new Reponse("ok", "voila le id : "+id) ) ; // rep.Buy(id);
	        }
	        
			
			
			
			// Envoie de réponse au client
			resp.println(result);
			resp.flush();

		}
	
}
