/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_management;
import UII.*;
import com.mysql.cj.x.protobuf.MysqlxNotice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class student extends user {
   private int matricule;
   private int numBooksBorrowed;
   private double fines;

    //constructor
      public student(int mat,  String name, String email, String password) {
      super(name, email, password);
      this.matricule = mat;
      this.numBooksBorrowed = 0;
      this.fines = 0;
   }
      public student(String name,String password){
        super(name);
        this.password=password;
      }

   public int getmatricule() {
      return matricule;
   }

   public int getNumBooksBorrowed() {
      return numBooksBorrowed;
   }

   public double getFines() {
      return fines;
   }

   
   
public void borrowBook(int bookId, int quantity, int matricule) {
    Connection connect;
    ResultSet dbrs;
    PreparedStatement ps;
    connect= JavaConnect.connectDb();
    Statement mystat;
        
    DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    Date date=new Date();
    String Date=dateFormat.format(date); //to print the current date
    
    Calendar cal=Calendar.getInstance();
    cal.add(Calendar.DATE,+14);
    Date todate1=cal.getTime();
    String Datelim= dateFormat.format(todate1);        
        
        
    try {
        String logquery="SELECT quantity,bookName FROM books WHERE bookId='"+bookId+"'  " ;  
        ps=connect.prepareStatement(logquery);
        dbrs =ps.executeQuery();
        if(dbrs.next()){
            int amt = dbrs.getInt(1);
            String bookname = dbrs.getString(2);
            int remove = amt-quantity ;
            if (remove < 0){
                JOptionPane.showMessageDialog(null, "insufficient quantity");
            }
            else if (remove >=0){
                String query="UPDATE books SET quantity = '"+remove+"' WHERE bookId = '"+bookId+"' " ; 
                mystat=connect.createStatement();
                mystat.executeUpdate(query);

                String state = "borrow"; 
                float fine = 0 ;
                JOptionPane.showMessageDialog(null, "you can take the books broda");
                String Query="INSERT INTO borrow(bookId,matricule,bookName,userName,date,datelimit,state,quantity,fines) VALUES('"+bookId+"','"+matricule+"', '"+bookname+"', '"+this.userName+"', '"+Date+"', '"+Datelim+"', '"+state+"', '"+quantity+"', '"+fine+"') " ;
                mystat=connect.createStatement();
                mystat.executeUpdate(Query);
                String q ="SELECT numberOfBooksBorrowed FROM students WHERE matricule='"+matricule+"'  " ;  
                ps=connect.prepareStatement(q);
                dbrs =ps.executeQuery();
                if (dbrs.next()){
                    int numbBooksBorrowed = dbrs.getInt(1);         
                    int newNumbBooksBorrowed = numbBooksBorrowed+quantity;
                    String queryQ="UPDATE students SET numberOfBooksBorrowed = '"+newNumbBooksBorrowed+"' WHERE matricule = '"+matricule+"' " ; 
                    mystat=connect.createStatement();
                    mystat.executeUpdate(queryQ);

                    JOptionPane.showMessageDialog(null, "you can take the books broda");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "book not found");
        }
    }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "sql error"); 
    }               
}
   //OR we can still check availability and reduce th quantity here if we dont succeed too convert the re
   //sult set to a corresponding book object
   
public void reserveBook(int bookid, int quantity, int matricule) {
    Connection connect;
    ResultSet dbrs;
    PreparedStatement ps;
    connect= JavaConnect.connectDb();
    Statement mystat;
        
    DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    Date date=new Date();
    String Date=dateFormat.format(date); //to print the current date
    
    Calendar cal=Calendar.getInstance();
    cal.add(Calendar.DATE,+14);
    Date todate1=cal.getTime();
    String Datelim= dateFormat.format(todate1);        
        
        
    try {
        String logquery="SELECT quantity,bookName FROM books WHERE bookId='"+bookid+"'  " ;  
        ps=connect.prepareStatement(logquery);
        dbrs =ps.executeQuery();
        if(dbrs.next()){
            int amt = dbrs.getInt(1);
            String bookname = dbrs.getString(2);
            int remove = amt-quantity ;
            if (remove < 0){
               JOptionPane.showMessageDialog(null, "insufficient quantity");
           }
           else if(remove>=0){
                String query="UPDATE books SET quantity = '"+remove+"' WHERE bookId = '"+bookid+"' " ; 
                mystat=connect.createStatement();
                mystat.executeUpdate(query);

                String reserved = "reserved";
                int fine = 0;
                String Query="INSERT INTO borrow VALUES('"+bookid+"','"+matricule+"', '"+bookname+"', '"+this.userName+"', '"+Date+"', '"+Datelim+"', '"+reserved+"', '"+quantity+"', '"+fine+"') " ;  
                mystat=connect.createStatement();
                mystat.executeUpdate(Query);

                String q ="SELECT numberOfBooksBorrowed FROM students WHERE matricule='"+matricule+"'  " ;  
                ps=connect.prepareStatement(q);
                dbrs =ps.executeQuery();
                if(dbrs.next()){
                    int numbBooksBorrowed = dbrs.getInt(1);
                    int newNumbBooksBorrowed = numbBooksBorrowed+quantity;
                    String queryQ="UPDATE students SET numberOfBooksBorrowed = '"+newNumbBooksBorrowed+"' WHERE matricule = '"+matricule+"' " ; 
                    mystat=connect.createStatement();
                    mystat.executeUpdate(queryQ);

                    JOptionPane.showMessageDialog(null, "you can take the books broda");
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"book not found");
            }
    }catch(SQLException e){ 
        JOptionPane.showMessageDialog(null, "an error occured unexpectedly"); 
    }       
      this.numBooksBorrowed++;
      System.out.println("Book borrowed successfully.");
   }
   
public void returnBook(int bookid, int quantity, int matricule) {
    Connection connect;
    ResultSet dbrs;
    PreparedStatement ps;
    connect= JavaConnect.connectDb();
    Statement mystat;
        
    DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    Date date=new Date();
    String Date=dateFormat.format(date); //to print the current date
    
    Calendar cal=Calendar.getInstance();
    cal.add(Calendar.DATE,+14);
    Date todate1=cal.getTime();
    String Datelim= dateFormat.format(todate1);        
        
        
    try {
        String qry="SELECT fines FROM borrow WHERE bookId='"+bookid+"' and matricule = '"+matricule+"'  " ;  
        ps=connect.prepareStatement(qry);
        dbrs =ps.executeQuery();
        if( dbrs.next() ){
            float fine = dbrs.getFloat(1);
            if(fine > 0){ 
                JOptionPane.showMessageDialog(null, "You have to pay fines first");
                System.out.println("You have to pay fines first");
            }else{               
                String logquery="SELECT quantity,bookName FROM books WHERE bookId='"+bookid+"'  " ;  
                ps=connect.prepareStatement(logquery);
                dbrs =ps.executeQuery();
                if(dbrs.next()){
                    int amt = dbrs.getInt(1);
                    String bookname = dbrs.getString(2);
                    int back = amt+quantity ;
        //            if (remove < 0){
        //               JOptionPane.showMessageDialog(null, "insufficient quantity");
        //           }
        //           else if(remove>=0){

        // check if date limit has not reached


                     String q = "DELETE FROM borrow WHERE bookId='"+bookid+"' and matricule='"+matricule+"' and quantity='"+quantity+"'  ";
                     mystat=connect.createStatement();
                    int rowsaffected=mystat.executeUpdate(q);
                    if (rowsaffected==0){
                        JOptionPane.showMessageDialog(null, "You owe more of this books in crese the quantity");
                    }else{
                        String query="UPDATE books SET quantity = '"+back+"' WHERE bookId = '"+bookid+"' " ; 
                        mystat=connect.createStatement();
                        mystat.executeUpdate(query);

                        String qr ="SELECT numberOfBooksBorrowed FROM students WHERE matricule='"+matricule+"'  " ;  
                        ps=connect.prepareStatement(qr);
                        dbrs =ps.executeQuery();
                        if(dbrs.next()){
                            int numbBooksBorrowed = dbrs.getInt(1);
                            int newNumbBooksBorrowed = numbBooksBorrowed-quantity;
                            String queryQ="UPDATE students SET numberOfBooksBorrowed = '"+newNumbBooksBorrowed+"' WHERE matricule = '"+matricule+"' " ; 
                            mystat=connect.createStatement();
                            mystat.executeUpdate(queryQ);
                            JOptionPane.showMessageDialog(null, "returned successfully");
                        }
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"book not found");
            }
    }catch(SQLException e){
        JOptionPane.showMessageDialog(null, "an error occured unexpectedly"); 
    }       
      
   }
       
   
   

   public void payFines(float amt, student st) {
        Connection connect;
        ResultSet dbrs;
        PreparedStatement ps;
        Statement mystat;
        connect= JavaConnect.connectDb();
    try{  
        String query="select matricule from students where username='"+st.userName+"' and password='"+st.password+"' " ;
        ps=connect.prepareStatement(query);
        dbrs =ps.executeQuery();
        if(dbrs.next()){
           int mat = dbrs.getInt(1);
            
            String logquery="select bookId,fines from borrow where matricule='"+mat+"'" ;
            ps=connect.prepareStatement(logquery);
            dbrs =ps.executeQuery();
            if(dbrs.next()){
                int bookid = dbrs.getInt(1);
                float fine = dbrs.getFloat(2);
                if( amt < fine){
                    JOptionPane.showMessageDialog(null, "You have to pay every thing at once");          
                }
                else{
                   float fine1 = fine-amt;
                   String queryQ="UPDATE borrow SET fines = '"+fine1+"' WHERE matricule = '"+mat+"' and bookId='"+bookid+"' " ; 
                    mystat=connect.createStatement();
                    mystat.executeUpdate(queryQ);
                    JOptionPane.showMessageDialog(null, "Successfull payment");          
                    JOptionPane.showMessageDialog(null, "Please return the book emidiately after payment");          

                }
    //            fine -= amt ;
        }
        }
    }
    catch(SQLException e){
            JOptionPane.showMessageDialog(null, "an error occured unexpectedly");          
    }
       // to display fines
       //we first check if borrowed 
       //we have to select where > date limit if rs.next then fine += amt 
//      fines -= amount;
      System.out.println("Fines paid successfully.");
   }    
   
   
   public ResultSet login (student st) throws SQLException{
        Connection connect;
        ResultSet dbrs;
        PreparedStatement ps;
        connect= JavaConnect.connectDb();
           String logquery="select * from students where username='"+st.userName+"' and password='"+st.password+"' " ;
           ps=connect.prepareStatement(logquery);
           dbrs =ps.executeQuery();
           return dbrs;
    }
    public boolean registerStud(student st){
        //database variables
      Statement mystat;
      Connection connect;
      connect= JavaConnect.connectDb();
        try {
            String query="insert into students values('"+st.password+"','"+st.userName+"','"+st.email+"','"+st.matricule+"','"+st.numBooksBorrowed+"') "; 
            mystat=connect.createStatement();
            mystat.executeUpdate(query);  
            JOptionPane.showMessageDialog(null, "account created");       
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "an error ocurred during creation");       
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
}
//public class DateComparisonExample {
//    public static void main(String[] args) {
//        Date date1 = new Date();
//        Date date2 = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24); // add one day to date1
//        
//        int result = date1.compareTo(date2);
//        
//        if (result < 0) {
//            System.out.println("date1 is before date2");
//        } else if (result > 0) {
//            System.out.println("date1 is after date2");
//        } else {
//            System.out.println("date1 is equal to date2");
//        }
//    }
//}

}
