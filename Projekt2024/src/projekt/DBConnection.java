package projekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



	public class DBConnection {

	  private static volatile Connection dbConnection;

	  private DBConnection() {}

	  /**
	   * Metoda, kter� vytv��� "jedin��ka" �ili maxim�ln� jednu instanci p�ipojen� k datab�zi.
	   * 
	   * POZN: Nen� vhodn� vytv��et mnoho instanc� p�ipojen� k datab�zi, aby se datab�ze nezahltila
	   * 
	   * @return vrac� instanci, p�ipojen� k datab�zi
	   */
	  public static Connection getDBConnection() {
	    if (dbConnection == null) {
	      synchronized (DBConnection.class) {
	        if (dbConnection == null) {
	          try {
	        	  Class.forName("com.mysql.cj.jdbc.Driver");
	            dbConnection = DriverManager.getConnection("jdbc:sqlite:src/funguj.db");
	          } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace(); // log it
	          }
	        }
	      }
	    }
	    return dbConnection;
	  }

	  /**
	   * metoda uzav�raj�c� spojen� s datab�z� POZN. vhodn� volat tuto metodu a� po vykon�n� v�ech
	   * dotaz� nad datab�z�
	   */
	  public static void closeConnection() {
	    try {
	      dbConnection.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }

	}





