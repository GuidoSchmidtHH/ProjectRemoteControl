
import java.sql.*;  


class RemCtrlDB
{  

	public static void main(String args[]){  
		try
		{  

			//Class.forName("com.mysql.jdbc.Driver");  
			
			Class.forName("mysql-connector-java-5.1.38-bin");
			
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/Test","root","********");  
			
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from TestRemote");  
			while(rs.next())  
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			con.close();  
		}catch(Exception e){ System.out.println(e);}  
	}  
}  