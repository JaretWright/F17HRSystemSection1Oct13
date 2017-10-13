package hrsystemsection1;

import java.sql.*;

/**
 *
 * @author JWright
 */
public class HRSystemSection1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
            Connection conn = null;
            Statement statement = null;
            ResultSet resultSet = null;
            
            String userName = "student";
            String password = "student";
            
            try
            {
                //1. connect to the DB
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcDemo",
                                                    userName, password);
                
                //2. create a statement object
                statement = conn.createStatement();
                
                //3. execute the query
                resultSet = statement.executeQuery("SELECT * FROM employees");
                
                //4.  print the meta data to the screen
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                
                for (int col=1; col <= columnCount; col++)
                    System.out.printf("%20s", metaData.getColumnName(col));
                
                System.out.println();
                
                //5.  display the results
                while (resultSet.next())
                {
                    for (int col=1; col <=columnCount; col++)
                        System.out.printf("%20s", resultSet.getString(col));
                    
                    System.out.println();
                }
                    
                
                
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            }
            
    }
    
}
