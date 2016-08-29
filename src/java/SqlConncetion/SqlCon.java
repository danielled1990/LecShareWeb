/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SqlConncetion;
import java.sql.*;

/**
 *
 * @author Danielle
 */
public abstract class  SqlCon {
   // private Connection myCon;
    //private Statement myStat;
   // private ResultSet myResS;
   // private String user = "root";
   // private String password = "DAnielle136";
    private static final String DB_NAME = "LecShare";
    private static final String DB_URL = String.format("jdbc:mysql://localhost:3306/%s?user=root&password=562345!&autoReconnect=true", DB_NAME);
    private static final String SERVER_URL = "jdbc:mysql://localhost:3306?user=root&password=562345!&autoReconnect=true";
    private static final String SQL_DRIVER = "com.mysql.jdbc.Driver";
    
    public static Connection dbConnection = null;
    public static String s = "";
    public static void init(){}
    
    static {
        try{
            Class.forName(SQL_DRIVER).newInstance();
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LecShare","root","562345");
    //        dbConnection = DriverManager.getConnection(SERVER_URL);
            s="ok";
            
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e){
            System.out.println("not working");
            s="not ok";
        }
    }
    
    
}
