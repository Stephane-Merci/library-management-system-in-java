/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_management;

/**
 *
 * @author dell
 */


public class user {
   public String userName;
   public String email;
   public String password;
   
   public user(String name){
    this.userName=name;
    };
   public user(String name, String email, String pass){
        this.userName = name;
        this.email = email; 
        this.password = pass;
   };
   
   public book searchBook(String input){
       //search for the book LIKE '%input%'
       book bk = new book();
       return bk;
   };
   
}
