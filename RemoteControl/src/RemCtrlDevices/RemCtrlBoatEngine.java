package RemCtrlDevices;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.util.Scanner;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

import PCA9685.PCA9685Board;
//import PCA9685.PCA9685LED;
import PCA9685.PCA9685Servo;

public class RemCtrlBoatEngine 
{

	PCA9685Servo motor1; //left engine
	PCA9685Servo motor2; //right engine
	PCA9685Servo rudder;
	
	
	//
	// Contructor
	//
	public RemCtrlBoatEngine(PCA9685Servo motor1, PCA9685Servo motor2, PCA9685Servo rudder)
	{
	
	    if (motor1 != null && motor2 != null && rudder != null)
	    {
	    	this.motor1 = motor1;
	    	this.motor2 = motor2;
	    	this.rudder = rudder;
	    	this.motor1.GotoNeutral();
	    	this.motor2.GotoNeutral();
	    	this.rudder.GotoNeutral();
	    }
	    else
	    {
	    	// to_do error handling
	    	
	    }
	} // end of contructor
	
	
	//
	// BoatRun 
	//
	//    speed: -100 .. 100
	//    steerRad: -90 Degree .. 90 Degree
	//
	public void BoatRun (int speed, int steerRad)
	{

		// Normalfahrt
		if (Math.abs(speed) > 10)
		{
			motor1.Speed(speed); //left
			motor2.Speed(speed); // right
			rudder.GotoRad(steerRad);
		}
		// Rangieren
		else
		{
            motor1.Speed (steerRad); 
            motor2.Speed (-1*steerRad);
            rudder.GotoRad(steerRad);
        }

	}

	
	
	//
	// Test
	//
	public static void main(String[] args) throws UnsupportedBusNumberException 
	{
		
		PCA9685Board servoBoardTest; 
		servoBoardTest = new PCA9685Board(0x40);
		servoBoardTest.setPWMFreq(60); // Set frequency to 60 Hz

//
//	    Servo FS90 = 130, 590
//	    Fahrtenregler = 300, 560 (volksregler 1)
//	    Servo MC410 = 214,585
//	    Multiplex Servo 942383 = 100,700

		PCA9685Servo Motor1 = new PCA9685Servo(servoBoardTest, 3,130 ,590, true);   
		PCA9685Servo Motor2 = new PCA9685Servo(servoBoardTest, 7,214 ,585, true);   
		PCA9685Servo Rudder = new PCA9685Servo(servoBoardTest, 15,100 ,700, true); 
		
		RemCtrlBoatEngine boat = new RemCtrlBoatEngine(Motor1, Motor2,Rudder); 

		
		int speed = 1;
		int direction = 1;

		
		System.out.println("-------------------------------");
		
		Scanner sc = null;
		while ( !(speed == 0 && direction == 0))
		{

			

			System.out.println("Enter speed -100 ... 100:");
			speed = 0;    
			sc = new Scanner(System.in);
			if (sc.hasNextInt())
			{
				speed = sc.nextInt();
			}
			

			System.out.println("Enter direction -90 ... 90:");
			direction = 0;
			sc = new Scanner(System.in);
			if (sc.hasNextInt())
			{
				direction = sc.nextInt();
			}
			

			boat.BoatRun(speed, direction );

			System.out.println("-------------------------------");
			
		}

		boat.BoatRun(0, 0 );
		sc.close();
		
		
	}

}
