// class pour mettre a jour les information d'une annance et recuperer les details de cette derniere
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
public class EDProduct  extends HttpServlet {
 
	private static final long serialVersionUID = 1L;

	public EDProduct() { super(); }
	
//	pour modifier une annance
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récuperer le PrintWriter Pour envoyer la réponse
        PrintWriter resp = response.getWriter();

        JsonObject jsObj = Readers.getJSONfromRequest(request);

        Product product = new Product();
        product = (Product) JSonConverter.objectFromJson(jsObj, product);  
        
        // Préparer la répense
        ProductServices rep = new ProductServices();
        
        // Envoie de réponse 
        resp.println(rep.EditProduct(product));  
        resp.flush();
        
	}
	
//	pour recuperer les details d'une annance
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récuperer le PrintWriter Pour envoyer la réponse
        PrintWriter resp = response.getWriter();

        // recuperer les params
        String ProductId = request.getParameter("ProductId");
        if (ProductId == null) {
            ProductId = "";
        }
        int p = Integer.parseInt(ProductId); 

        // Préparer la répense
        ProductServices rep = new ProductServices();
        // Envoie de réponse
        resp.println(rep.getProductDetails(p));
        resp.flush();

    }
}
