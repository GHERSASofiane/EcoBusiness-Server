// Class pour la getion des annances ajout,supprimer et recherche
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

/**
 *
 * @author Sofiane GHERSA
 */
public class ADSProduct extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ADSProduct() {
		super();
	}

//	méthode pour l'ajoute d'une annance
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();

		JsonObject jsObj = Readers.getJSONfromRequest(request);

		models.Product product = new models.Product();
		product = (models.Product) JSonConverter.objectFromJson(jsObj, product);

		// Préparer la répense
		ProductServices rep = new ProductServices();
		// Envoie de réponse
		resp.println(rep.addProduct(product));
		resp.flush();

	}

//	Pour la supprission d'une annance
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();

		int idProduct = Integer.parseInt(req.getParameter("idProduct"));

		// Préparer la répense
		ProductServices rep = new ProductServices();
		// Envoie de réponse
		resp.println(rep.DeleteProduct(idProduct));
		resp.flush();
	}

//	fonction pour la recherche d'une annance
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();

		// recuperer les params
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
