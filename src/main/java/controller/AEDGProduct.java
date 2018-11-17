
// Class pour la gestion des annonces ajouter, modifier, supprimer et recherche

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
import models.Product;
import services.ProductServices;

/**
 *
 * @author Sofiane GHERSA
 */
public class AEDGProduct extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AEDGProduct() {
		super();
	}

//**************************************************	méthode pour l'ajoute d'une annonce
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();
		// transférer les données de la requête en Json
		JsonObject jsObj = Readers.getJSONfromRequest(request);
		// extraire les données qu'on a besoin
		models.Product product = new models.Product();
		product = (models.Product) JSonConverter.objectFromJson(jsObj, product);

		// Préparer la réponse
		ProductServices rep = new ProductServices();
		// Envoie de réponse au client
		resp.println(rep.addProduct(product));
		resp.flush();

	}

//**************************************************	Pour la modification d'une annonce 
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();
		// transférer les données de la requête en Json
		JsonObject jsObj = Readers.getJSONfromRequest(request);
		// extraire les données qu'on a besoin
		Product product = new Product();
		product = (Product) JSonConverter.objectFromJson(jsObj, product);

		// Préparer la réponse
		ProductServices rep = new ProductServices();

		// Envoie de réponse
		resp.println(rep.EditProduct(product));
		resp.flush();

	}

//**************************************************	Pour la suppression d'une annonce
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();
		// extraire les données qu'on a besoin
		int idProduct = Integer.parseInt(req.getParameter("idProduct"));

		// Préparer la réponse
		ProductServices rep = new ProductServices();
		// Envoie de réponse
		resp.println(rep.DeleteProduct(idProduct));
		resp.flush();
	}

//**************************************************	fonction pour la recherche d'une annonce
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();

		// extraire les données qu'on a besoin
		String PName = request.getParameter("ProductName");
		String Page = request.getParameter("Page");
		if (PName == null) {
			PName = "";
		}
		if (Page == null) {
			Page = "0";
		}

		PName = PName.toLowerCase();
		Page = Page.toLowerCase();
		int p = Integer.parseInt(Page);

		// Préparer la répense
		ProductServices rep = new ProductServices();
		// Envoie de réponse
		resp.println(rep.searchProduct(PName, p));
		resp.flush();
	}

}