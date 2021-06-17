package LoginAndRegister;
import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
    public static Connection getConnection(){
        
        Connection connect = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://remotemysql.com/K74c1CEtnS", "K74c1CEtnS","5HlVA1v9rX");
            
        }catch(Exception e){
            
            System.out.print(e.getMessage());
        }
       return connect; 
    }
    
}
