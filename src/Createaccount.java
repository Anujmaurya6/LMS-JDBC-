import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Createaccount {
    static String DB_URL = "jdbc:mysql://localhost:3306/ATMsystem";
    static String USER = "root";
    static String PASSWORD = "@Anuj69k";
    static Connection conn;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("CONNECTED SUCCESSFULLY!");
            while (true) {
                System.out.println("ATM SYSTEM MENU!");
                System.out.println("1.CREATE ACCOUNT");
                System.out.println("2.EXIT");
                System.out.println("Enter your choice:");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        System.out.println("EXITING... Thank you!");
                        return;
                    default:
                        System.out.println("INVALID CHOICE! Please enter the correct choice.");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR IN CONNECTION: " + e.getMessage());
        }
    }

    public static void createAccount() throws SQLException {
        // Read input
        sc.nextLine();  // To consume the newline left by nextInt()
        System.out.println("Enter your name:");
        String name = sc.nextLine();
        System.out.println("Set your account number:");
        int accountnumber = sc.nextInt();
        System.out.println("Set 4-digit pin:");
        int pin = sc.nextInt();
        double balance = 0.0;  // New holder's balance is always zero

        // Insert query using prepared statement
        String sql = "INSERT INTO accounts (accountnumber, name, pin, balance) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, accountnumber);
        pstmt.setString(2, name);
        pstmt.setInt(3, pin);
        pstmt.setDouble(4, balance);

        // Execute the query
        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Failed to create account!");
        }
    }
}
