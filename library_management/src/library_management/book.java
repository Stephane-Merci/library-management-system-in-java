/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_management;

import java.util.Scanner ;
/**
 *
 * @author dell
 */
public class book {
   private int bookId;
   private String bookName;
   private String author;
   private String publisher;
   private String availability;
   public int quantity;
   
   public book(){};
   public book( String bookName, String author, String publisher, String availability, int quantity) {
      this.bookId = bookId;
      this.bookName = bookName;
      this.author = author;
      this.publisher = publisher;
      this.availability = availability;
      this.quantity = quantity;
   }

   public int getBookId() {
      return bookId;
   }

   public String getBookName() {
      return bookName;
   }

   public String getAuthor() {
      return author;
   }

   public String getPublisher() {
      return publisher;
   }

   public boolean isAvailable() {
       if (this.quantity < 1 ){
           return false ;
       }
       else return true ;
   }

   public void setAvailability(String availability) {
      this.availability = availability;
   }

   public boolean borrowBook() {
       System.out.println("How many books do you wish to borrow: ");
       Scanner sc = new Scanner(System.in);
       int qty=sc.nextInt();
       
        if ((this.quantity - qty) >= 0) {      //check availability
            this.quantity -= qty;             // reducing the number of books borrowed from the total
            System.out.println("Book borrowed successfully.");    // should add in the borrowing queue
            return true;
       }else {
         System.out.println("Book not available for borrowing.");  
         return false;
      }
   }

   public void returnBook() {
       System.out.println("How many books do you wish to return: ");
       Scanner sc = new Scanner(System.in);
       int qty=sc.nextInt();
       this.quantity += qty ;
       System.out.println("Book returned successfully.");               // remove from the reserved or  borrow queue
   }

   public void reserveBook() {
        System.out.println("How many books do you wish to reserve: ");
        Scanner sc = new Scanner(System.in);
        int qty=sc.nextInt();
        if ((this.quantity - qty) >= 0 ) {
            this.quantity -= qty ;                      // reducing the number of books reserved from the total 
            System.out.println("Book reserved successfully.");    //should add in the reserving queue
        }else{
            System.out.println("Book not available for reservation.");
        }
   }
}




