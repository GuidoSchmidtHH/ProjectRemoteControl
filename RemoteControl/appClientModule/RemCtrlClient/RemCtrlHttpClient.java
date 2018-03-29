package RemCtrlClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//
// JAVA Client GUI
// RemCtrlHttpClient
// 
//
public class RemCtrlHttpClient 
{

	//static public String server_url = "localhost";
	static public String server_url = "192.168.178.65";
	static public int server_port = 8000; 
	static public String server_dir = "test"; 
	
	
	public  int SERVO_MAX(int pin)
	{
		
		try 
		{
			HttpRequestGet(pin+",SERVO_MAX");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 100;
	}
	
	public  int SERVO_MIN(int pin)
	{
		
		try 
		{
			HttpRequestGet(pin+",SERVO_MIN");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -100;
	}
	
	
	public  int SERVO_NEUTRAL(int pin)
	{
		
		try 
		{
			HttpRequestGet(pin+",SERVO_NEUTRAL");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public  int SERVO_SPEED(int pin, int value)
	{
		
		try 
		{
			HttpRequestGet(pin+",SERVO_SPEED,"+value);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public  int GET_SERVO_SPEED(int pin)
	{
		String result = "";
		
		int value = 0;
		
		try 
		{
			result = HttpRequestGet(pin+",GET_SERVO_SPEED");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "error";
		}
		
		value = Integer.parseInt(result);
		
		return value;
	}
	
	private static String HttpRequestGet(String command) throws IOException
	{
		
		URL connection_url = null;
		String content ="";
		
		try 
		{
			connection_url = new URL("http://"+server_url +":"+server_port+"/"+server_dir+"?"+command);
		} 
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpURLConnection connection = null;
		try 
		{
			 connection = (HttpURLConnection) connection_url.openConnection();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (connection != null)
		{
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "text/html");
			if (connection.getResponseCode() !=200)
			{
				//error
				
			}
			else
			{
			
				//ok
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine = "";
				//StringBuffer content = new StringBuffer();
				
				//int i=0;
				//while 
			    if (((inputLine = in.readLine()) != null)) //&& i==0) 
				{
			    	content = inputLine;
					//content.append(inputLine);
					//i++;
				}
			    else
			    {
			    	content ="error";
			    }
				in.close();
				
			}
			
			
			connection.disconnect();
				
		}
				
		return content;
		
	}
	
	
	//
	// test
	//
	
	public static void main(String[] args) throws Exception 
	{
		
		RemCtrlHttpClient HttpClient = new RemCtrlHttpClient();
		
		HttpClient.SERVO_MAX(3);
		
		TimeUnit.SECONDS.sleep(1);
		
		HttpClient.SERVO_MIN(3);
		
		TimeUnit.SECONDS.sleep(1);
		
		HttpClient.SERVO_NEUTRAL(3);
		
		
	}
	
	
	
}
