

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.Headers;


/*
 * a simple static http server
*/
public class RemCtrlHttpServer 
{
	
	static PCA9685LED LED_1;
	static boolean ON = false;
	

  public static void main(String[] args) throws Exception 
  {
    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/test", new MyHandler());
    server.setExecutor(null); //
    server.start();
    
    System.out.println("HTTP Server Started");
    

	PCA9685Board servoBoardTest; 
	servoBoardTest = new PCA9685Board(0x41);
	servoBoardTest.setPWMFreq(60); // Set frequency to 60 Hz
	LED_1 = new PCA9685LED(servoBoardTest, 15, true);
	ON = false;
    
    
    
    
  }

  static class MyHandler implements HttpHandler 
  {
    public void handle(HttpExchange t) throws IOException 
    {
      
    
      // "http://mysite/myhandler.ashx?test=hello"
      //HttpContext context = t.getHttpContext();
      //String test = context.Request.QueryString["test"];
      
    	String result = t.getRequestURI().getQuery();
      
    	int number = Integer.parseInt(result);
    	
    	if (number<0 || number >100) number = 0;
    	
    	result = "DIM: " + number;
    	
        result = "<h1>RemoteControl Test Page</h1> " + result;
      
        byte [] response = result.getBytes();
      
      
        
        LED_1.Dim(number);
        
        
        /*
        
      if (ON)
      {
    	  LED_1.Aus();
          ON = false;
    	  
      }
      else
      {
         LED_1.An();
         ON = true;
      }
      
      */
      
      t.sendResponseHeaders(200, response.length);
      OutputStream os = t.getResponseBody();
      os.write(response);
      os.close();
    }
  }
}