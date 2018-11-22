
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
import status.Reponse;
import tokens.AutorisationAcess;

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
		
		
		
        JsonObject result = new JsonObject();
        
        if(!AutorisationAcess.isTokenExist(request))
        {
        	result = JSonConverter.objectToJson(new Reponse("ko", "Deconnexion"));
        }
        else
        {
        
        	// transférer les données de la requête en Json
    		JsonObject jsObj = Readers.getJSONfromRequest(request);
    		// extraire les données qu'on a besoin
    		models.Product product = new models.Product();
    		product = (models.Product) JSonConverter.objectFromJson(jsObj, product);

    		// Préparer la réponse
    		ProductServices rep = new ProductServices();
    		
    		result = rep.addProduct(product);
        }
        
		
		
		
		// Envoie de réponse au client
		resp.println(result);
		resp.flush();

	}

//**************************************************	Pour la modification d'une annonce 
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();
		
		
JsonObject result = new JsonObject();
        
        if(!AutorisationAcess.isTokenExist(request))
        {
        	result = JSonConverter.objectToJson(new Reponse("ko", "Dec"));
        }
        else
        {
        
        	// transférer les données de la requête en Json
    		JsonObject jsObj = Readers.getJSONfromRequest(request);
    		// extraire les données qu'on a besoin
    		Product product = new Product();
    		product = (Product) JSonConverter.objectFromJson(jsObj, product);

    		// Préparer la réponse
    		ProductServices rep = new ProductServices();
    		result = rep.EditProduct(product);
        }
		
		
		// Envoie de réponse
		resp.println(result);
		resp.flush();

	}

//**************************************************	Pour la suppression d'une annonce
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();
		
		
		JsonObject result = new JsonObject();
        
        if(!AutorisationAcess.isTokenExist(req))
        {
        	result = JSonConverter.objectToJson(new Reponse("ko", "Dec"));
        }
        else
        {
        
        	// extraire les données qu'on a besoin
    		int idProduct = Integer.parseInt(req.getParameter("idProduct"));

    		// Préparer la réponse
    		ProductServices rep = new ProductServices();
    		result = rep.DeleteProduct(idProduct);
        }
		
		// Envoie de réponse
		resp.println(result);
		resp.flush();
	}

//**************************************************	fonction pour la recherche d'une annonce
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récuperer le PrintWriter Pour envoyer la réponse
		PrintWriter resp = response.getWriter();

        JsonObject result = new JsonObject();
 
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
        	result = rep.searchProduct(PName, p);
       


		// Envoie de réponse
		resp.println(result); 
		resp.flush();
	}

}
