
// Class pour récupérer le driving " les annonce demander et valider par le vendeur "

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
public class Driving extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Driving() {
		super();
	}

//**************************************************	méthode do get
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();

		JsonObject result = new JsonObject();

//        Securisé avec le token rien ne passe sans le token valide
		if (!AutorisationAcess.isTokenExist(request)) {
			result = JSonConverter.objectToJson(new Reponse("ko", "Dec"));
		} else {

			// extraire les données qu'on a besoin id de user
			String Id = request.getParameter("id");
			if (Id == null) {
				Id = "";
			}

			Id = Id.toLowerCase();
			int id = Integer.parseInt(Id);

			// Préparer la répense
			ProductServices rep = new ProductServices();
			result = rep.Driving(id);
		}

		// Envoie de réponse
		resp.println(result);
		resp.flush();
	}
}
