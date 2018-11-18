
// Class pour tester les données entrer par le client avant de passer a la coche qui communique avec la BDD

package services;

import java.util.Calendar;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.interfaces.Claim;
import com.google.gson.JsonObject;

import DAO.ProductDAO;
import DAO.ProfilDAO;
import converters.JSonConverter;
import models.Product;
import models.Reservation;
import status.Reponse;
import tokens.AutorisationAcess;

/**
 *
 * @author Sofiane GHERSA
 */
public class ProductServices {

	ProductDAO pr = new ProductDAO();

// ************************************************** Recherche des annonces
	public JsonObject searchProduct(String nameArticle, int page) {

//		Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.searchProduct(nameArticle, page));
	}

// ************************************************** Ajouter une annonce
	public JsonObject addProduct(Product product) {

//		Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(product.getProductName())) {
			return JSonConverter.objectToJson(new Reponse("ko", "ProductName est obligatoire"));
		}

		if (!Pattern.matches("[a-zA-Z0-9\\s]+", product.getProductName())) {
			return JSonConverter.objectToJson(new Reponse("ko", "ProductName ne respecte pas la syntaxe"));
		}

		if (!IsPresent(product.getProductDescription())) {
			return JSonConverter.objectToJson(new Reponse("ko", "ProductDescription  est obligatoire"));
		}
		if (!IsPresent(product.getProductPrice())) {
			return JSonConverter.objectToJson(new Reponse("ko", "ProductPrice  est obligatoire"));
		}
		if (!IsPresent(product.getUserId())) {
			return JSonConverter.objectToJson(new Reponse("ko", "UserId  est obligatoire"));
		}
		if (!IsPresent(product.getProductPicture())) {
			return JSonConverter.objectToJson(new Reponse("ko", "ProductPicture  est obligatoire"));
		}
//		Ajouter quelque information 
		product.setProductDate(getDate());
		product.setProductStatus(0);

//		Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.addProduct(product));
	}

	// ************************************************** Récupérer les annonces
	// publier par l'utilisateur courant
	public JsonObject MyPubs(int id) {

//				Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(id)) {
			return JSonConverter.objectToJson(new Reponse("ko", "Pas d'identifiant pour la recherch "));
		}

//				Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.MyPubs(id));
	}

	// ************************************************** Récupérer les annonces
	// publier par l'utilisateur courant
	public JsonObject Buy(int id) {

//									Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(id)) {
			return JSonConverter.objectToJson(new Reponse("ko", "Pas d'identifiant pour la validation "));
		}

//									Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.Buy(id));
	}

	// ************************************************** Récupérer les annonces
	// publier par l'utilisateur courant
	public JsonObject History(int id) {

//														Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(id)) {
			return JSonConverter.objectToJson(new Reponse("ko", "Pas d'identifiant pour la validation "));
		}

//														Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.History(id));
	}

// ************************************************** Récupérer les annonces accepter par le vendeur
	public JsonObject Driving(int id) {

//				Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(id)) {
			return JSonConverter.objectToJson(new Reponse("ko", "Pas d'identifiant pour la recherch "));
		}

//				Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.Driving(id));
	}

// ************************************************** Supprimer une annonce
	public JsonObject DeleteProduct(int id) {

//		Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(id)) {
			return JSonConverter.objectToJson(new Reponse("ko", "Pas d'identifiant pour la supprission "));
		}

//		Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.deleteProduct(id));
	}

// ************************************************** Modifier une annonce
	public JsonObject EditProduct(Product product) {

//		Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(product.getProductName())) {
			return JSonConverter.objectToJson(new Reponse("ko", "ProductName est obligatoire"));
		}
		if (!IsPresent(product.getProductDescription())) {
			return JSonConverter.objectToJson(new Reponse("ko", "ProductDescription  est obligatoire"));
		}
		if (!IsPresent(product.getProductPrice())) {
			return JSonConverter.objectToJson(new Reponse("ko", "ProductPrice  est obligatoire"));
		}
		if (!IsPresent(product.getUserId())) {
			return JSonConverter.objectToJson(new Reponse("ko", "UserId  est obligatoire"));
		}
		if (!IsPresent(product.getProductPicture())) {
			return JSonConverter.objectToJson(new Reponse("ko", "ProductPicture  est obligatoire"));
		}

//		Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.EditProduct(product));
	}

// ************************************************** Récupérer les détails d'une annonce
	public JsonObject getProductDetails(Integer productId) {

//		Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(productId)) {
			return JSonConverter.objectToJson(new Reponse("ko", "productId est obligatoire"));
		}

//		Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.getProductDetails(productId));
	}

// ************************************************** Ajouter une demande
	public JsonObject addReservation(Reservation reserv) {

//		Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(reserv.getReservationDate())) {
			return JSonConverter.objectToJson(new Reponse("ko", "getReservationDate  est obligatoire "));
		}
		if (!IsPresent(reserv.getReservationMessage())) {
			return JSonConverter.objectToJson(new Reponse("ko", "getReservationMessage  est obligatoire "));
		}
		if (!IsPresent(reserv.getReservationProduct())) {
			return JSonConverter.objectToJson(new Reponse("ko", "getReservationProduct  est obligatoire "));
		}
		if (!IsPresent(reserv.getReservationReceive())) {
			return JSonConverter.objectToJson(new Reponse("ko", "getReservationReceive  est obligatoire "));
		}
		if (!IsPresent(reserv.getReservationSend())) {
			return JSonConverter.objectToJson(new Reponse("ko", "getReservationSend  est obligatoire "));
		}

//		Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.addReservation(reserv));
	}

// ************************************************** Récupérer les réservations demander pour un produit donner
	public JsonObject GetReservationReq(int id) {

//		Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(id)) {
			return JSonConverter.objectToJson(new Reponse("ko", "ID  est obligatoire "));
		}

//		Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.GetReservationReq(id));
	}

// ************************************************** Valider une réservation
	public JsonObject ReservationValidate(Reservation reserv) {

//		Tester les données envoyé par le client parce qu'on fais pas confiance ;)
		if (!IsPresent(reserv.getProductId())) {
			return JSonConverter.objectToJson(new Reponse("ko", "ID  est obligatoire "));
		}

//		Passer à la couche qui communique avec La BDD
		return JSonConverter.objectToJson(pr.ReservationValidate(reserv));
	}

// ************************************************** Fonction utiles

	private boolean IsPresent(String arg) {

		if (arg == null || arg.length() == 0) {
			return false;
		} else {
			return true;
		}

	}

	private boolean IsPresent(Integer arg) {

		if (arg == null) {
			return false;
		} else {
			return true;
		}

	}

	public String getDate() {
		String Mydate = "";
		Calendar calendar = Calendar.getInstance();
		Mydate = Mydate.concat(Integer.toString(calendar.get(calendar.YEAR)));
		Mydate = Mydate.concat("-");
		Mydate = Mydate.concat(Integer.toString(calendar.get(calendar.MONTH) + 1));
		Mydate = Mydate.concat("-");
		Mydate = Mydate.concat(Integer.toString(calendar.get(calendar.DAY_OF_MONTH)));

		return Mydate;
	}

}
