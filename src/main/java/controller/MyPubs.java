
// Class pour récuperer les annonces de client

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
public class MyPubs extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public MyPubs() {
		super();
	}

//**************************************************	récuperer les annances 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();

		JsonObject result = new JsonObject();

//        Securisé avec le token rien ne passe sans le token valide
		if (!AutorisationAcess.isTokenExist(req)) {
			result = JSonConverter.objectToJson(new Reponse("ko", "Dec"));
		} else {

			// Récuperer le ID de l'utilisateur
			int idUser = Integer.parseInt(req.getParameter("idUser"));

			// Préparer la répense
			ProductServices rep = new ProductServices();
			result = rep.MyPubs(idUser);
		}

		// Envoie de réponse
		resp.println(result);
		resp.flush();

	}
}
