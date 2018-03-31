

package RemCtrlServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
//import com.sun.net.httpserver.HttpContext;
//import com.sun.net.httpserver.Headers;

import PCA9685.PCA9685LED;
import PCA9685.PCA9685Servo;
import PCA9685.PCA9685Board;


/*
 * Static HTTP Server
 */
public class RemCtrlHttpServer 
{

	static PCA9685LED[] LED = new PCA9685LED[16]  ;
	static PCA9685Servo[] SERVO = new PCA9685Servo[16]  ;

	static HttpServer server;


	public static void main(String[] args) throws Exception 
	{
		server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/test", new MyHandler());
		server.setExecutor(null); //

		server.start();
		System.out.println("HTTP Server Started");


		//
		// Configure LEDs
		//
		PCA9685Board servoBoardLED; 
		servoBoardLED = new PCA9685Board(0x41);
		servoBoardLED.setPWMFreq(60); // Set frequency to 60 Hz
		for (int i=0; i<=15; i++)
		{

			LED[i] = new PCA9685LED(servoBoardLED, i, true);

		}

		//
		// Configure Servos
		//
		//  Servo FS90 = 130, 590
		//  Fahrtenregler = 300, 560 (volksregler 1)
		//  Servo MC410 = 214,585
		//  Multiplex Servo 942383 = 100,700


		PCA9685Board servoBoardServo; 
		servoBoardServo = new PCA9685Board(0x40);
		servoBoardServo.setPWMFreq(60); // Set frequency to 60 Hz
		for (int i=0; i<=15; i++)
		{
			if       (i==3) SERVO[i] = new PCA9685Servo(servoBoardServo, i, 130,590, true);
			else  if (i==7) SERVO[i] = new PCA9685Servo(servoBoardServo, i, 214,585, true);
			else  if (i==11) SERVO[i] = new PCA9685Servo(servoBoardServo, i, 300, 560, true);
			else  if (i==15) SERVO[i] = new PCA9685Servo(servoBoardServo, i, 100,700, true);
			else SERVO[i] =new PCA9685Servo(servoBoardServo, i,0,4095,true);
		}


	}

	static class MyHandler implements HttpHandler 
	{

		private void All_Neutral()
		{
			
			for (int i=0; i<=15; i++)
			{
				LED[i].Aus();
			}

			for (int i=0; i<=15; i++)
			{
				if (i==3 || i==7 || i==11 || i==15) SERVO[i].GotoNeutral();
				else SERVO[i].GotoMin();
			}
			
			
			
		}


		public void handle(HttpExchange t) throws IOException 
		{

            boolean html = true;
			String result = "<h1>RemoteControl</h1> \r\n";
			result = result + "<ul> \r\n";

			//
			// request method
			// GET or POST

			final String requestMethod = t.getRequestMethod(); 	
			result = result + "<li> Request Method : " +  requestMethod + "\r\n";     	

			//
			// Query
			//   pin_nr; command; parameters      
			//
			String query =  t.getRequestURI().getQuery();
			
			result = result + "<li>query: " + query + "\r\n";

			if (query.equalsIgnoreCase("STOP"))
			{
				System.out.println("HTTTP Server STOPPING");

				//All_Neutral();
				
						
				server.stop(0);

				System.out.println("HTTP Server Stopped");
				//return;

			}
			else
			{	

				

				String[] command = query.split(",");

				int pin_number = Integer.parseInt(command[0]);
				if ( (pin_number<0 || pin_number >15) && pin_number !=1000)
				{
					//error
					return;
				}  	
				result = result + "<li> ... PIN: " + pin_number+ "\r\n";
                html = true;
				
				//
				// Excecute Command
				//
				if (command[1].equalsIgnoreCase("NEUTRAL"))
				{
					if (pin_number == 1000) All_Neutral();
					
				}
				else if (command[1].equalsIgnoreCase("LED_DIM"))
				{
					int value = Integer.parseInt(command[2]);
					LED[pin_number].Dim(value);
					result = result + "<li> ... Command: LED_DIM value: " + value + "\r\n";
				}
				else if (command[1].equalsIgnoreCase("LED_ON"))
				{
					LED[pin_number].An();
					result = result + "<li> ... Command: LED_ON \r\n";
				}
				else if (command[1].equalsIgnoreCase("LED_OFF"))
				{
					LED[pin_number].Aus();
					result = result + "<li> ... Command: LED_OFF \r\n";
				}
				else if (command[1].equalsIgnoreCase("SERVO_NEUTRAL"))
				{
					SERVO[pin_number].GotoNeutral();
					result = result + "<li> ... Command: SERVO_NEUTRAL \r\n";
				}
				else if (command[1].equalsIgnoreCase("SERVO_MIN"))
				{
					SERVO[pin_number].GotoMin();
					result = result + "<li> ... Command: SERVO_MIN \r\n";
				}	
				else if (command[1].equalsIgnoreCase("SERVO_MAX"))
				{
					SERVO[pin_number].GotoMax();
					result = result + "<li> ... Command: SERVO_MAX \r\n";
				}
				else if (command[1].equalsIgnoreCase("SERVO_SPEED"))
				{
					
					int value = Integer.parseInt(command[2]);
					SERVO[pin_number].Speed(value);
					result = result + "<li> ... Command: SERVO_SPEED " + value + " \r\n";
				}
				else if (command[1].equalsIgnoreCase("GET_SERVO_SPEED"))
				{
					
					//int value = Integer.parseInt(command[2]);
					int value = SERVO[pin_number].GetServoValueSpeed();
					result = ""+value;
					html = false;
					
					//result = result + "<li> ... Command: SERVO_SPEED " + value + " \r\n";
				}
				
				
				if (html) result = result + "</ul>";
			}



			
			byte [] response = result.getBytes();

			t.sendResponseHeaders(200, response.length);
			OutputStream os = t.getResponseBody();
			os.write(response);
			os.close();
		}
	}
}