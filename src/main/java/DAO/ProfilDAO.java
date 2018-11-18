package DAO;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import configuration.Connexion;
import models.Personne;
import status.Reponse;

/**
 *
 * @author Sofiane GHERSA
 */
public class ProfilDAO {

	private Connection db;
	
// update profile
	public Reponse update(Personne prof)
	{
			if( !isMailExiste(prof.getUserMail()) ) return new Reponse("ko", "le mail n'existe pas ");
		//	if( !isTelExiste(prof.getUserPhone()) ) return new Reponse("ko", "le Phone existe deja ");
			
			try {
				db = Connexion.getConnection();
				String query = "UPDATE users SET (userMail, userName, userPassword, userPhone, userAdress, userKey, userprofilepicture) = (?,?,?,?,?,?,?)"
						+ "  WHERE userId = ?;";
				PreparedStatement preparedStmt = db.prepareStatement(query);
				preparedStmt.setString(1, prof.getUserMail() );
				preparedStmt.setString(2, prof.getUserName() );
				preparedStmt.setString(3, prof.getUserPassword() );
				preparedStmt.setString(4, prof.getUserPhone() );
				preparedStmt.setString(5, prof.getUserAddress() );
				preparedStmt.setString(6, prof.getUserKey() );
				preparedStmt.setString(7, prof.getUserPicture());
				preparedStmt.setInt(8, prof.getUserId());
				
				// execute the prepared statement
				preparedStmt.execute();
				preparedStmt.close();
				db.close();

			} catch (URISyntaxException e) {
				e.printStackTrace();
				return new Reponse("ko", "votre inscription a echooo");
			} catch (SQLException e) {
				e.printStackTrace();
				return new Reponse("ko", "votre inscription a echooo");
			}
			
			prof.setUserPassword("");
			return new Reponse("ok", prof);

	}
	
//	inscription
	public Reponse Registration(Personne prof) {

		if( isMailExiste(prof.getUserMail()) ) return new Reponse("ko", "le mail existe deja ");
		if( isTelExiste(prof.getUserPhone()) ) return new Reponse("ko", "le Phone existe deja ");
		
		try {
			db = Connexion.getConnection();
			String query = "INSERT INTO Users(UserMail, UserName, UserPassword, UserPhone, UserAdress, UserKey, UserPicture) VALUES(?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = db.prepareStatement(query);
			preparedStmt.setString(1, prof.getUserMail() );
			preparedStmt.setString(2, prof.getUserName() );
			preparedStmt.setString(3, prof.getUserPassword() );
			preparedStmt.setString(4, prof.getUserPhone() );
			preparedStmt.setString(5, prof.getUserAddress() );
			preparedStmt.setString(6, prof.getUserKey() );
			preparedStmt.setString(7, prof.getUserPicture() );
			
			// execute the prepared statement
			preparedStmt.execute();
			preparedStmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre inscription a echooo");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "votre inscription a echooo");
		}
		
		prof.setUserPassword("");
		return new Reponse("ok", prof);
		
	}
	
//	login
	public Reponse Login(Personne prof) {

		if( !isMailExiste(prof.getUserMail()) ) return new Reponse("ko", "le mail n'existe deja "); 
		if( !isMailPass(prof.getUserMail(), prof.getUserPassword()) ) return new Reponse("ko", "le mot de passe est incorrect "); 
		
		Personne personne = new Personne();
		
		try {
			db = Connexion.getConnection();

			

			String res = "select * from users where userMail =? and userPassword=?;";
			PreparedStatement pst = db.prepareStatement(res);
			pst.setString(1, prof.getUserMail());
			pst.setString(2, prof.getUserPassword());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				personne.setUserId(rs.getInt(1));
				personne.setUserMail(rs.getString(2));
				personne.setUserName(rs.getString(3));
				personne.setUserPhone(rs.getString(5));
				personne.setUserAddress(rs.getString(6));
				personne.setUserPicture(rs.getString(8));
			}

			pst.close();
			rs.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new Reponse("ko", "Erreur sur le serveur");
		} catch (SQLException e) {
			e.printStackTrace();
			return new Reponse("ko", "Erreur sur le serveur");
		}
		
		return new Reponse("ok", personne);
	}
	
//	********************* fonction utiles
	

	private boolean isMailPass(String mail, String pass) {
		boolean res = false;
		try {
			db = Connexion.getConnection();
			Statement stmt = db.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Users WHERE UserMail = '"+mail+"' AND UserPassword = '"+pass+"';");
			
			if (rs.next()) {
				res = true;
			}			
			rs.close();
			stmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace(); return res;
		} catch (SQLException e) {
			e.printStackTrace(); return res; 
		}
		
		 return res;
	}
	
	private boolean isMailExiste(String mail) {
		boolean res = false;
		try {
			db = Connexion.getConnection();
			Statement stmt = db.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Users WHERE UserMail = '"+mail+"';");
			
			if (rs.next()) {
				res = true;
			}			
			rs.close();
			stmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace(); return res;
		} catch (SQLException e) {
			e.printStackTrace(); return res; 
		}
		
		 return res;
	}
	
	private boolean isTelExiste(String tel) {
		boolean res = false;
		try {
			db = Connexion.getConnection();
			Statement stmt = db.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Users WHERE UserPhone = '"+tel+"';");
			
			if (rs.next()) {
				res = true;
			}			
			rs.close();
			stmt.close();
			db.close();

		} catch (URISyntaxException e) {
			e.printStackTrace(); return res;
		} catch (SQLException e) {
			e.printStackTrace(); return res; 
		}
		
		 return res;
	}

	public boolean getUserWithId(int userId) {
		try {
			db = Connexion.getConnection();
			
			System.out.println("user id ---------------------> " + userId);
			
			String res = "select count(*) from users where userId =?;";
			PreparedStatement pst = db.prepareStatement(res);
			pst.setInt(1, userId);

			ResultSet rs = pst.executeQuery();

			int count = 0;
			while (rs.next()) {
				count = rs.getInt(1);
			}

			if (count == 0) {
				db.close();
				return false;

			}

			db.close();
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
			
		}

		return true;
	}
	
}
