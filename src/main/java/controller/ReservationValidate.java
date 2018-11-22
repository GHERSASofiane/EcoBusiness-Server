
// Class pour la confirmation d'une demande de réservation

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
import models.Reservation;
import services.ProductServices;
import status.Reponse;
import tokens.AutorisationAcess;

/**
 *
 * @author Sofiane GHERSA
 */
public class ReservationValidate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ReservationValidate() {
		super();
	}

//**************************************************	confirmation d'une demande de réservation
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();

		JsonObject result = new JsonObject();

//        Securisé avec le token rien ne passe sans le token valide
		if (!AutorisationAcess.isTokenExist(request)) {
			result = JSonConverter.objectToJson(new Reponse("ko", "Deconnexion"));
		} else {

			// transférer les données de la requête en Json
			JsonObject jsObj = Readers.getJSONfromRequest(request);
			// extraire les données qu'on a besoin
			Reservation reserv = new Reservation();
			reserv = (Reservation) JSonConverter.objectFromJson(jsObj, reserv);

			// Préparer la réponse
			ProductServices rep = new ProductServices();
			result = rep.ReservationValidate(reserv);
		}

		// Envoie de réponse
		resp.println(result);
		resp.flush();

	}
}
