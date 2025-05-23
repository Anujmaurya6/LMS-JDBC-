import java.sql.*;
import java.util.Scanner;

public class Librarymanagement {
    static final String DB_URL="jdbc:mysql://localhost:3306/library";
    static final String user="root";
    static final String password="@Anuj69k";
    static Connection conn;
    static Scanner sc=new Scanner(System.in);

    public static void main(String[] args) {
        try{
            conn= DriverManager.getConnection(DB_URL,user,password);
            System.out.println("connected successfully");
        }catch(Exception e ){
            e.printStackTrace();
            System.out.println("error!Not connected");
        }
        int choice;
        do{
            System.out.println("\n----Library menu----");
            System.out.println("1.Addbooks");
            System.out.println("2.view all books");
            System.out.println("3.borrow books");
            System.out.println("4.return books");
            System.out.println("5.delete books");
            System.out.println("6.0 exit");
            choice =sc.nextInt();
            switch(choice){
                case 1:
                    try {
                        addbooks();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    try {
                        viewbooks();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    try {
                        borrowbooks();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    try {
                        returnbooks();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    try {
                        deletebooks();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 6:
                    System.out.println("exit.");
                default:
                    System.out.println("invalid option");
            }
        }while(choice !=0);
        choice=sc.nextInt();
        sc.nextLine();
    }
    static void addbooks()throws SQLException { 
        System.out.println("enter book title:");
        String title=sc.nextLine();
        System.out.println("enter author name:");
        String author=sc.nextLine();
        String Query="Insert INTO books(title,author)VALUES(?,?)";
        PreparedStatement ps=conn.prepareStatement(Query);
        ps.setString(1,title);
        ps.setString(2,author);
        int result=ps.executeUpdate();
        System.out.println("books added succesfully.");
        if(result >0){
            System.out.println("BOOKS HAVE BEEN ADDED SUCCESSFULLY:");
        }else{
            System.out.println("THERE IS SOME TECHNICAL ISSUE ");
        }
    }
    static void viewbooks()throws SQLException{
        String Query ="SELECT*FROM BOOKS";
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(Query);
        System.out.println("Book list:");
        while(rs.next());
        int id=rs.getInt("id");
        String title=rs.getString("title");
        String author=rs.getString("author");
       boolean isAvailable=rs.getBoolean("is available:");
        System.out.println("ID:"+id+",title"+title+",Author:"+author);
    }
    static void borrowbooks()throws SQLException{
        System.out.println("enter the book id to be borrowed:");
        int id=sc.nextInt();
        sc.nextLine();
        String checkQuery="SELECT*FROM BOOKS id=?";
        PreparedStatement checkstmt=conn.prepareStatement(checkQuery);
        checkstmt.setInt(1,id);
        ResultSet rs=checkstmt.executeQuery();
        if(rs.next()){
            System.out.println("you borrowed:"+rs.getString("title"));
        }else{
            System.out.println("book not found:");
        }
    }
    static void returnbooks()throws SQLException{
        System.out.println("enter book id returning");
        int id=sc.nextInt();
        String checkQuery="update books Set is available =true WHERE id=?";
        PreparedStatement ps=conn.prepareStatement(checkQuery);
        ps.setInt(1,id);
        int rowsAffected=ps.executeUpdate();
        if(rowsAffected>0){
            System.out.println("book returned sucessfully");
        }else{
            System.out.println("invalid option :");
        }
    }
    static void deletebooks()throws SQLException{
        System.out.println("enter the books u want to delete:");
        int id=sc.nextInt();
        String query="DELETE FROM books WHERE id=?";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setInt(1,id);
        int result=ps.executeUpdate();
        if(id>0) {
            System.out.println("the books deleted succesfully");
        }else{
            System.out.println("the books not deleted successfully");
        }
    }

}
