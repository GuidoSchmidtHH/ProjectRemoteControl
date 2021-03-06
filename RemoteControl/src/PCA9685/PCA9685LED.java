package PCA9685;

import java.util.concurrent.TimeUnit;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;


// --------------------------------------------------------------------
// PCA9685LED
// ==========
//
// LED Object for PCA 9685
//
// --------------------------------------------------------------------


public class PCA9685LED 
{

	public  PCA9685Board ServoBoard = null;
	private int pin=0;
	private boolean debug = true;  // true (wahr) = 1; false (falsch) = 0
	
	int  LEDValue; 
	boolean Blink = false;
	
	
	// -----------------------------------------------------------------
	// Contructor
	// -----------------------------------------------------------------
	public PCA9685LED(PCA9685Board ServoBoard, int pin, boolean debug)
	{
		
				
		if (ServoBoard != null)
		{
		   this.ServoBoard = ServoBoard;
		}
		else
		{
			// generate error
		}
		
		if (pin >=0 && pin <= 15)
		{
			this.pin = pin;
		}
		else
		{
			this.pin = 0;
		}
		
		ServoBoard.setPWM(this.pin, 0, 0);
		LEDValue = 0;
		
		//
		if (debug)
		{
	       System.out.println("LED initiated:");
		   System.out.println("...Pin:      " + this.pin);
		}
		
		this.debug = debug;
			
	}// end of contructor
	
	
	// -----------------------------------------------------------------
	// LED An
	// -----------------------------------------------------------------
	public  void An()
	{
		ServoBoard.setPWM(pin, 0, 4095);
		LEDValue = 100;
		Blink = false;
		
		if (debug)
		{
	        System.out.println("LED: " + this.pin + " AN");
		}
		
	}
	
	// -----------------------------------------------------------------
	// LED Aus
	// -----------------------------------------------------------------
	public  void Aus()
	{
		ServoBoard.setPWM(pin, 0, 0);
		LEDValue = 0;
		Blink = false;
		
		if (debug)
		{
	        System.out.println("LED: " + this.pin + " AUS");
		}
		
	}
	
	// -----------------------------------------------------------------
	// LED DIM
	// -----------------------------------------------------------------
	public  void Dim(int value) // value 0 ..100
	{
		ServoBoard.setPWM(pin, 0, value*4095/100);
		LEDValue = value;
		Blink = false;
		
		if (debug)
		{
	        System.out.println("LED: " + this.pin + " Light " + value + "%");
		}
	}
	
	// -----------------------------------------------------------------
	// Get LED Value 0 ... 100
	// -----------------------------------------------------------------
	public int GetLEDValue()
	{
		
		// getPWM abfragen
		// LEDValue setzen
				
		return LEDValue;
		
	}
	
	// -----------------------------------------------------------------
	// LED Blink
	//    1 sec
	// -----------------------------------------------------------------
	public  void Blink() 
	{
		
		// to do über bool blink steuern
		
		Blink = true;
		
		if (debug)
		{
	        System.out.println("LED: " + this.pin + " Blink");
		}
		
		
		Thread thread = new Thread()
		{
		    public void run()
		    {
		    	
		     while (Blink)
		     {
		    	 ServoBoard.setPWM(pin, 0, 4095); 
		    	 LEDValue = 100; 
		    	 try 
		    	 {
					TimeUnit.SECONDS.sleep(1);
				 } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	 ServoBoard.setPWM(pin, 0, 0);
		    	 LEDValue = 0;
		    	 try 
		    	 {
					TimeUnit.SECONDS.sleep(1);
				 } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	 
		    	 
		     }
		    }
		  };

		  thread.start();
		
		
		
	}
	
	// -----------------------------------------------------------------
	// MAIN Test
	// -----------------------------------------------------------------
	public static void main(String[] args) throws UnsupportedBusNumberException, InterruptedException 
	{
		// TODO Auto-generated method stub

		PCA9685Board servoBoardTest; 
		servoBoardTest = new PCA9685Board(0x41);
		servoBoardTest.setPWMFreq(60); // Set frequency to 60 Hz
		
		
		PCA9685LED LED_1 = new PCA9685LED(servoBoardTest, 0, true);
	    LED_1.An(); 	
	    TimeUnit.SECONDS.sleep(1);
	    LED_1.Aus();
	    TimeUnit.SECONDS.sleep(1);
	    
	    LED_1.Blink();
	    TimeUnit.SECONDS.sleep(10);
	  
	    LED_1.Aus();
	    
	    /*
	    
	    for (int i = 100; i>=0; i=i-1)
	    {
	    	LED_1.Dim(i);
	    	TimeUnit.MILLISECONDS.sleep(100);
	    	
	    	
	    }
	    */
		
		
	} // end main

}// END CLASS
