/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_management;

import UII.signup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author dell
 */
public class librarian extends user{
        public int libId;   
        
        //constructor
        public librarian( String name, String email, String password) {
            super(name, email, password);
       }
         public librarian(String name, String password){
            super(name);
            this.password=password;
        }

       public void addBook(book book) {   
    //populate the book table
            String bookname = book.getBookName();
            String author = book.getAuthor();
            String publisher = book.getPublisher();
            boolean availability = book.isAvailable();
            int quantity = book.quantity;

              //database variables
          Statement mystat;
          Connection connect;
          PreparedStatement ps;
          ResultSet rs ;
          connect= JavaConnect.connectDb();
            try {
                String query="insert into books(`bookName`, `author`, `publisher`, `availability`, `quantity`) values('"+bookname+"','"+author+"','"+publisher+"','"+availability+"','"+quantity+"') "; 
                mystat=connect.createStatement();
                mystat.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "book succcessfully created"); 

            } catch (SQLException ex) {
                Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "error creating a book "); 
            }
            try {
                String logquery="select bookId from books where bookName='"+book.getBookName()+"' and author='"+book.getAuthor()+"' " ;
                ps=connect.prepareStatement(logquery);
                rs =ps.executeQuery();
                if(rs.next()){
                    int bi = rs.getInt(1);
                
                String query="select libId from librarian where userName='"+this.userName+"' and password='"+this.password+"' " ;
                ps=connect.prepareStatement(query);
                rs =ps.executeQuery();
                if(rs.next()){
                    int libId=rs.getInt(1);
                    String add = "add" ;
                    String queryQ="insert into modify values('"+bi+"','"+libId+"','"+book.getBookName()+"','"+this.userName+"','"+add+"') "; 
                    mystat=connect.createStatement();
                    mystat.executeUpdate(queryQ);
                }
                }
            } catch (SQLException ex) {
                Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "error occured unexpectedly"); 
            }
       }
       public void removeBook(String bookId, int libid) {
           // delete from the table

              //database variables
          Statement mystat;
          PreparedStatement ps;
          ResultSet rs;
          Connection connect;
          connect= JavaConnect.connectDb();
            try {
                String query="DELETE FROM books WHERE bookId='"+bookId+"'"; 
                mystat=connect.createStatement();
                mystat.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "book succcessfully deleated"); 

            } catch (SQLException ex) {
                Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "error deleteing the book"); 
            }

             try {
                String q ="SELECT bookName FROM books WHERE bookId='"+bookId+"'  " ;  
                ps=connect.prepareStatement(q);
                rs =ps.executeQuery();
                if(rs.next()){
                    String bookName=rs.getString(1);
                
                String deleted = "deleted";
                String query="insert into modify values('"+bookId+"','"+libid+"','"+bookName+"', "+this.userName+"','"+deleted+"') "; 
                mystat=connect.createStatement();
                mystat.executeUpdate(query);
            } 
             }catch (SQLException ex) {
                Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "an error occured unexpectedly"); 
            }
       }


       public void removeStudent(String matricule, int libid) {
           //remove student from the table
            Statement mystat;
          Connection connect;
          connect= JavaConnect.connectDb();
            try {
                String query="DELETE FROM students WHERE matricule='"+matricule+"'"; 
                mystat=connect.createStatement();
                mystat.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "student succcessfully deleated"); 

            } catch (SQLException ex) {
                Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "error deleteing the student"); 
                
            }
       }
       public void registerLib(librarian st){
            //database variables
          Statement mystat;
          Connection connect;
          connect= JavaConnect.connectDb();
            try {
                String query="insert into librarian (`PASSWORD`, `userName`, `email`) values('"+st.password+"','"+st.userName+"','"+st.email+"' ) "; 
                mystat=connect.createStatement();
                mystat.executeUpdate(query);     
                JOptionPane.showMessageDialog(null, "creted successfully"); 
            } catch (SQLException ex) {
                Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "an error occured unexpectedly"); 
            }
       }
       public ResultSet login (librarian st) throws SQLException{
        Connection connect;
        ResultSet dbrs;
        PreparedStatement ps;
        connect= JavaConnect.connectDb();
           String logquery="select * from librarian where username='"+st.userName+"' and password='"+st.password+"' " ;
           ps=connect.prepareStatement(logquery);
           dbrs =ps.executeQuery();
           return dbrs;
    }
}



