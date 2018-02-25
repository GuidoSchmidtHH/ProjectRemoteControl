import java.util.concurrent.TimeUnit;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;


public class PCA9685Test 
{
	
	private static PCA9685Board servoBoardTest; 

	public  void testServo (int pin, int min, int max) throws UnsupportedBusNumberException, InterruptedException
	{
		 
		
		PCA9685Servo Servo_1 = new PCA9685Servo(servoBoardTest, pin, min, max, true);   //min = -90 Grad; max = 90 Grad
		TimeUnit.SECONDS.sleep(1);
		
		
		
		// 1st run - 90 Degree
		Servo_1.GotoMax();
		TimeUnit.SECONDS.sleep(1);
		Servo_1.GotoMin();
		TimeUnit.SECONDS.sleep(1);
		Servo_1.GotoNeutral();
		TimeUnit.SECONDS.sleep(1);
				
				
		//2nd run - 90 Degree	
		Servo_1.GotoRad(90);
		TimeUnit.SECONDS.sleep(1);
		Servo_1.GotoRad(-90);
		TimeUnit.SECONDS.sleep(1);
		Servo_1.GotoRad(0);
		TimeUnit.SECONDS.sleep(1);
		
		//3rd run  - 45 Degree
		Servo_1.GotoRad(45);
		TimeUnit.SECONDS.sleep(1);
		Servo_1.GotoRad(-45);
		TimeUnit.SECONDS.sleep(1);
		Servo_1.GotoRad(0);
		TimeUnit.SECONDS.sleep(1);
		
				
		//4th run -90 to 90 Degree Loop
		for (int i=-90; i <=90; i = i + 5)
		{
			Servo_1.GotoRad(i);
			TimeUnit.MILLISECONDS.sleep(200);
			
		}
		
		TimeUnit.SECONDS.sleep(1);
		Servo_1.GotoRad(0);
		
	}
	
	public  void testMotor (int pin, int min, int max) throws UnsupportedBusNumberException, InterruptedException
	{
		
				
		PCA9685Servo Servo_1 = new PCA9685Servo(servoBoardTest,pin, 300, 560, true);   //140 = -90 Grad; 640 = 90 Grad
		TimeUnit.SECONDS.sleep(1);
			
		
		for (int i=-100; i <=100; i = i + 5)
		{
			Servo_1.Speed(i);
			//System.out.println("..."+i);
			TimeUnit.MILLISECONDS.sleep(200);
			
		}
		
		TimeUnit.SECONDS.sleep(1);
		
		Servo_1.Speed(100);
		TimeUnit.SECONDS.sleep(5);
		Servo_1.Speed(-100);
		TimeUnit.SECONDS.sleep(5);
		Servo_1.Speed(0);
			
	}
	
	
	
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws UnsupportedBusNumberException 
	 */
	public static void main(String[] args) throws UnsupportedBusNumberException, InterruptedException 
	{
		// TODO Auto-generated method stub
		
		servoBoardTest = new PCA9685Board();
		servoBoardTest.setPWMFreq(60); // Set frequency to 60 Hz
		
		
        PCA9685Test testbucket = new PCA9685Test(); 
		
        
//      Servo FS90 = 130, 590
//      Fahrtenregler = 300, 560 (volksregler 1)
//      Servo MC410 = 214,585
        
        testbucket.testServo(1, 130,590);
		
		
		//testbucket.testMotor(3, 300, 560);
		
		
		

	}

}
