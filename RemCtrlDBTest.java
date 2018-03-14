
import java.sql.*;  
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


class RemCtrlDBTest
{  

	static PCA9685LED LED_1;
	static Scanner sc ;
	
	
	public static void main(String args[])
	{  
		try
		{  
			
			PCA9685Board servoBoardTest; 
			servoBoardTest = new PCA9685Board(0x41);
			servoBoardTest.setPWMFreq(60); // Set frequency to 60 Hz
			LED_1 = new PCA9685LED(servoBoardTest, 0, true);
			
			try 
			{
				// The newInstance() call is a work around for some
				// broken Java implementations

				Class.forName("mysql-connector-java-5.1.46").newInstance();
			} catch (Exception ex) {
				// handle the error
			}

			 sc = new Scanner(System.in);
			 System.out.print("Password:");
			 String passwd = sc.next();
			 //System.out.println(passwd);
			 passwd = passwd.trim();
			
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/Test","pi",passwd);  

			System.out.println("Connected to DB");
			
			// Table Test Command   
			Statement stmt=con.createStatement();  
												
			//boolean done = stmt.execute("INSERT INTO `TestCommand`(`Value`) VALUES ('0')");  
			//done = stmt.execute("COMMIT");  
			
			ResultSet rs=stmt.executeQuery("select * from TestCommand");  
			
			System.out.println(rs.toString());
			
			while(rs.next())
			{
			
				int value = rs.getInt(1);
				System.out.println(value);
				LED_1.Dim(value);
				TimeUnit.SECONDS.sleep(1);
			
			}
			con.close();
			System.out.println("Connection closed");
						
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}  
	}  
}  