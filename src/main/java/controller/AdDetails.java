
// class pour récupérer les détails d'une annonce 

package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import converters.JSonConverter;
import services.ProductServices;
import status.Reponse;
import tokens.AutorisationAcess;

/**
 *
 * @author Sofiane GHERSA
 */
public class AdDetails extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AdDetails() {
		super();
	}

//**************************************************	class pour récupérer les détails d'une annonce 
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();

        JsonObject result = new JsonObject();
        
//        Securisé avec le token rien ne passe sans le token valide
        if(!AutorisationAcess.isTokenExist(request))
        {
        	result = JSonConverter.objectToJson(new Reponse("ko", "Dec"));
        }
        else
        {
    		// récupérer les params
    		String ProductId = request.getParameter("ProductId");
    		if (ProductId == null) {
    			ProductId = "";
    		}
    		int p = Integer.parseInt(ProductId);

    		// Préparer la répense
    		ProductServices rep = new ProductServices();
        	result = rep.getProductDetails(p);
        }

		// Envoie de réponse
		resp.println(result);
		resp.flush();

	}
}
