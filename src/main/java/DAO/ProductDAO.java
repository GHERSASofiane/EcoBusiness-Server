
// class pour l'interaction avec la base de donnée 

package DAO;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import configuration.Connexion;
import models.Product;
import models.ProductDetail;
import models.Reservation;
import status.Reponse;

/**
 *
 * @author Sofiane GHERSA
 */
public class ProductDAO {

	private Connection db;

//*************************************************************	Fonction pour la recherche des annonces
	public Reponse searchProduct(String nameArticle, int page) {
		List<Product> res = new ArrayList<Product>();
		Product tmp;

		try {
			db = Connexion.getConnection();

			Statement stmt = db.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM Product, Users WHERE ProductStatus = 0 AND Product.userid = Users.UserId AND ( ProductName LIKE '%"
							+ nameArticle + "%' OR ProductDescription LIKE '%" + nameArticle
							+ "%' ) ORDER BY ProductDate DESC OFFSET " + (page * 10) + " LIMIT 10 ");

//			Préparer la réponse
			while (rs.next()) {
				tmp = new Product();

				tmp.setProductName(rs.getString("ProductName"));
				tmp.setProductDate(rs.getString("ProductDate"));
				tmp.setProductDescription(rs.getString("ProductDescription"));
				tmp.setProductPicture(rs.getString("ProductPicture"));
				tmp.setProductId(rs.getInt("ProductId"));
				tmp.setProductPrice(rs.getString("ProductPrice"));
				tmp.setProductStatus(rs.getInt("ProductStatus"));
				tmp.setUserId(rs.getInt("userid"));
				tmp.setUserName(rs.getString("UserName"));

				res.add(tmp);

			}
			stmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "Erreur sur le serveur");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "Erreur sur le serveur");
		}
		return new Reponse("ok", res);
	}

//*************************************************************	Fonction pour ajouter une annonce
	public Reponse addProduct(Product product) {

		try {
			db = Connexion.getConnection();
			String res = " INSERT INTO product(productname,productdescription,productprice,productpicture,productstatus,userid,productdate) VALUES('"
					+ product.getProductName() + "','" + product.getProductDescription() + "','"
					+ product.getProductPrice() + "','" + product.getProductPicture() + "',0," + product.getUserId()
					+ ",'" + product.getProductDate() + "');";

			Statement statement = db.createStatement();
			statement.executeUpdate(res);

			statement.close();

			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre produit n'a pas pu etre ajouter");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre produit n'a pas pu etre ajouter");
		}

		return new Reponse("ok", " votre produit est ajouter avec succes ");

	}

//*************************************************************	Fonction pour récupérer les annonces publiées
	public Reponse MyPubs(int id) {

		List<Product> res = new ArrayList<Product>();
		Product tmp;

		try {
			db = Connexion.getConnection();

			Statement stmt = db.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM Product WHERE  Product.UserId = " + id + " ORDER BY ProductDate DESC ");

			while (rs.next()) {
				tmp = new Product();

				tmp.setProductName(rs.getString("ProductName"));
				tmp.setProductDate(rs.getString("ProductDate"));
				tmp.setProductDescription(rs.getString("ProductDescription"));
				tmp.setProductPicture(rs.getString("ProductPicture"));
				tmp.setProductId(rs.getInt("ProductId"));
				tmp.setProductPrice(rs.getString("ProductPrice"));
				tmp.setProductStatus(rs.getInt("ProductStatus"));
				tmp.setUserId(rs.getInt("UserId"));

				res.add(tmp);

			}
			stmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "Erreur sur le serveur");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "Erreur sur le serveur");
		}
		return new Reponse("ok", res);
	}

//*************************************************************	Fonction pour la suppression d'une annonce
	public Reponse deleteProduct(int id) {
		try {

			db = Connexion.getConnection();
			String query = "DELETE FROM product WHERE productid = ?";
			PreparedStatement preparedStmt = db.prepareStatement(query);
			preparedStmt.setInt(1, id);

			// execute the prepared statement
			preparedStmt.execute();
			preparedStmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre produit n'a pas pu etre suprimmer");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre produit n'a pas pu etre suprimmer");
		}

		return new Reponse("ok", "votre produit est supprime avec succes");

	}

//*************************************************************	Fonction pour modifier une annonce
	public Reponse EditProduct(Product product) {

		try {

			db = Connexion.getConnection();
			String query = " UPDATE product SET productname = '" + product.getProductName()
					+ "', productdescription = '" + product.getProductDescription() + "', " + " productprice = '"
					+ product.getProductPrice() + "', productpicture   = '" + product.getProductPicture() + "' "
					+ " WHERE ProductId = " + product.getProductId();

			PreparedStatement preparedStmt = db.prepareStatement(query);
			preparedStmt.executeUpdate();

			preparedStmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre produit n'a pas pu etre modifier ");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre produit n'a pas pu etre modifier ");
		}

		return new Reponse("ok", " votre produit est modifier avec succes ");

	}

//*************************************************************	Fonction pour récupérer les détails d'une annonce
	public Reponse getProductDetails(Integer productId) {

		// tester si l'annance est toujours disponible pour la Concurrence
		if (!isProductDis(productId)) {
			return new Reponse("ko", "l'annance n'est plus disponible");
		}

		ProductDetail tmpProd = new ProductDetail();

		try {
			db = Connexion.getConnection();

			Statement stmt = db.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Product, Users WHERE Product.userid = Users.userid AND ProductId = "
							+ productId + ";");
			while (rs.next()) {

				tmpProd.setProductName(rs.getString("ProductName"));
				tmpProd.setProductDate(rs.getString("ProductDate"));
				tmpProd.setProductDescription(rs.getString("ProductDescription"));
				tmpProd.setProductPicture(rs.getString("ProductPicture"));
				tmpProd.setProductId(rs.getInt("ProductId"));
				tmpProd.setProductPrice(rs.getString("ProductPrice"));
				tmpProd.setProductStatus(rs.getInt("ProductStatus"));
				tmpProd.setUserId(rs.getInt("UserId"));
				tmpProd.setUserName(rs.getString("UserName"));
				tmpProd.setUserMail(rs.getString("UserMail"));
				tmpProd.setUserAdress(rs.getString("UserAdress"));
				tmpProd.setUserPhone(rs.getString("UserPhone"));

			}
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "Erreur sur le serveur");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "Erreur sur le serveur");
		}

		return new Reponse("ok", tmpProd);

	}

//*************************************************************	Fonction pour ajouter une demande de réservation
	public Reponse addReservation(Reservation reserv) {

		// tester si l'annance est toujours disponible pour la Concurrence
		if (!isProductDis(reserv.getProductId())) {
			return new Reponse("ko", "l'annance n'est plus disponible");
		}

		try {

			db = Connexion.getConnection();
			String query = "INSERT INTO Request(RequestSend, RequestReceive, RequestProduct, RequestMessage, RequestDate ) VALUES(?, ?, ?, ?, ? );";
			PreparedStatement preparedStmt = db.prepareStatement(query);
			preparedStmt.setInt(1, reserv.getReservationSend());
			preparedStmt.setInt(2, reserv.getReservationReceive());
			preparedStmt.setInt(3, reserv.getReservationProduct());
			preparedStmt.setString(4, reserv.getReservationMessage());
			preparedStmt.setString(5, reserv.getReservationDate());

			// execute the prepared statement
			preparedStmt.executeUpdate();
			preparedStmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre Reservation n'a pas pu etre ajouter");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre Reservation n'a pas pu etre ajouter");
		}

		return new Reponse("ok", "votre Reservation est envoyer avec succes");

	}

//*************************************************************	Fonction pour récupérer les demandeurs de réservation
	public Reponse GetReservationReq(Integer productId) {
		List<Reservation> TabRes = new ArrayList<Reservation>();
		Reservation tmpProd = new Reservation();

		try {
			db = Connexion.getConnection();

			Statement stmt = db.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM Product, Request, Users WHERE Product.ProductId = Request.RequestProduct AND "
							+ "Request.RequestSend = Users.userid AND RequestProduct = " + productId + ";");
			while (rs.next()) {

				tmpProd.setProductName(rs.getString("ProductName"));
				tmpProd.setProductDate(rs.getString("ProductDate"));
				tmpProd.setProductDescription(rs.getString("ProductDescription"));
				tmpProd.setProductPicture(rs.getString("ProductPicture"));
				tmpProd.setProductId(rs.getInt("ProductId"));
				tmpProd.setProductPrice(rs.getString("ProductPrice"));
				tmpProd.setProductStatus(rs.getInt("ProductStatus"));

				tmpProd.setUserId(rs.getInt("UserId"));
				tmpProd.setUserName(rs.getString("UserName"));
				tmpProd.setUserMail(rs.getString("UserMail"));
				tmpProd.setUserAdress(rs.getString("UserAdress"));
				tmpProd.setUserPhone(rs.getString("UserPhone"));

				tmpProd.setReservationDate(rs.getString("RequestDate"));
				tmpProd.setReservationMessage(rs.getString("RequestMessage"));
				tmpProd.setReservationProduct(rs.getInt("RequestProduct"));
				tmpProd.setReservationReceive(rs.getInt("RequestReceive"));
				tmpProd.setReservationSend(rs.getInt("RequestSend"));

				TabRes.add(tmpProd);

			}
			stmt.close();
			rs.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "Erreur sur le serveur");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "Erreur sur le serveur");
		}

		return new Reponse("ok", TabRes);

	}

//*************************************************************	Fonction pour valider une demande de réservation et annuler les autre demande de 
//*************************************************************	cette annonce et passer le statut de cette annonce à 1 qui veux dire ç'est déjà réserver
	public Reponse ReservationValidate(Reservation reserv) {

		try {

			db = Connexion.getConnection();

			// changer le status
			String RChange = "UPDATE Product SET ProductStatus = 1 WHERE ProductId = ?;";
			PreparedStatement preparedStmt = db.prepareStatement(RChange);
			preparedStmt.setInt(1, reserv.getProductId());
			preparedStmt.execute();

			// supprimer de la table reservation
			String RDelete = "DELETE FROM Request WHERE RequestProduct = ?;";
			preparedStmt = db.prepareStatement(RDelete);
			preparedStmt.setInt(1, reserv.getProductId());
			preparedStmt.execute();

			// ajouter dans la table achat
			String RInsert = "INSERT INTO Booking(bookingDated,ProductId,UserId) VALUES(?,?,?);";
			preparedStmt = db.prepareStatement(RInsert);
			preparedStmt.setString(1, this.getDate());
			preparedStmt.setInt(2, reserv.getProductId());
			preparedStmt.setInt(3, reserv.getReservationSend());
			preparedStmt.execute();

			preparedStmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre Validation n'a pas pu etre effectuer");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre Validation n'a pas pu etre effectuer");
		}

		return new Reponse("ok", "L'operation de Validation est bien passer :) :) ");

	}


//*************************************************************	Fonction pour récupérer le driving
		public Reponse Driving(int id) {

			List<ProductDetail> res = new ArrayList<ProductDetail>();
			ProductDetail tmp;

			try {
				db = Connexion.getConnection();

				Statement stmt = db.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT * FROM Product, Booking, Users WHERE Product.UserId = Users.UserId = AND Booking.UserId = " + id + " AND Product.UserId = Booking.UserId ORDER BY BookingDated DESC ");

				while (rs.next()) {
					tmp = new ProductDetail();
					
					tmp.setProductName(rs.getString("ProductName"));
					tmp.setProductDate(rs.getString("ProductDate"));
					tmp.setProductDescription(rs.getString("ProductDescription"));
					tmp.setProductPicture(rs.getString("ProductPicture"));
					tmp.setProductId(rs.getInt("ProductId"));
					tmp.setProductPrice(rs.getString("ProductPrice"));
					tmp.setProductStatus(rs.getInt("ProductStatus"));

					tmp.setUserId(rs.getInt("UserId"));
					tmp.setUserName(rs.getString("UserName"));
					tmp.setUserMail(rs.getString("UserMail"));
					tmp.setUserAdress(rs.getString("UserAdress"));
					tmp.setUserPhone(rs.getString("UserPhone"));

					res.add(tmp);

				}
				stmt.close();
				db.close();

			} catch (URISyntaxException e) {
				e.printStackTrace();
				return new Reponse("ko", "Erreur sur le serveur");
			} catch (SQLException e) {
				e.printStackTrace();
				return new Reponse("ko", "Erreur sur le serveur");
			}
			return new Reponse("ok", res);
		}
		
//*************************************************************	Fonction utiles

//	Tester si le produit est toujours disponible 
	private boolean isProductDis(int prod) {
		boolean res = false;
		try {
			db = Connexion.getConnection();
			Statement stmt = db.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT COUNT(*) FROM Product WHERE ProductStatus = 0 AND ProductId = " + prod + ";");

			
			while(rs.next()) {
				res = true;  
			}
			
			rs.close();
			stmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return res;
		}

		return res;
	}

//	Récuperer la date de systeme 
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
