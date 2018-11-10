/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


/**
 *
 * @author Sofiane GHERSA
 */
public class ProductDetail extends Product{
     String UserMail, UserAdress;
     String UserPhone;

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setUserMail(String UserMail) {
        this.UserMail = UserMail;
    }

    public void setUserAdress(String UserAdress) {
        this.UserAdress = UserAdress;
    }

    public void setUserPhone(String string) {
        this.UserPhone = string;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserMail() {
        return UserMail;
    }

    public String getUserAdress() {
        return UserAdress;
    }

    public String getUserPhone() {
        return UserPhone;
    }
     
     
}
