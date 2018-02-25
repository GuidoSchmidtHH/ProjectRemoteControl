// --------------------------------------------------
// Class SERVO 
// 
//        -----------------
//        -               -
//        -    ---        -
//        -    ---        -
//        -----------------
//   -90       ---      90 
//   (min)     ---     (max)
//   backward  ---      forward
//              -  
//            0 Degree
//            Stop
//
//
//    Servo FS90 = 130, 590
//    Fahrtenregler = 300, 560 (volksregler 1)
//    Servo MC410 = 214,585
// --------------------------------------------------

public class PCA9685Servo 
{
	
	public  PCA9685Board ServoBoard = null;
	private int servoMin=120;  //-90 Degree      
	private int servoMax=630;  // 90 Degree
	private int pin=0;
	private boolean debug = true;
	
	
	//
	// Contructor
	//
	public PCA9685Servo(PCA9685Board ServoBoard, int pin, int servoMin, int servoMax, boolean debug)
	{
		
		if ((servoMin < servoMax) && (servoMin >0) && (servoMax < 4096))
		{
			this.servoMax = servoMax;
			this. servoMin = servoMin;
		}
			
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
		
		GotoNeutral();
		
		//
		
		if (debug)
		{
	       System.out.println("Servo initiated:");
		   System.out.println("...ServoMin: " + this.servoMin);
		   System.out.println("...ServoMax: " + this.servoMax);
		   System.out.println("...Pin:      " + this.pin);
		}
		
		this.debug = debug;
			
	}// end of contructor
	
	
	
	public  void GotoMin()
	{
		ServoBoard.setPWM(pin, 0, servoMin); 
		if (debug)
		{
	       System.out.println("Servo value: min") ;
		   
		}
				
	}
	
	public  void  GotoMax()
	{
		ServoBoard.setPWM(pin, 0, servoMax); 
		if (debug)
		{
	       System.out.println("Servo value: max") ;
		   
		}
				
	}
	
	public  void  GotoNeutral()
	{
		ServoBoard.setPWM(pin, 0, (int)(servoMin + (servoMax - servoMin)/2));
		if (debug)
		{
	       System.out.println("Servo value: neutral") ;
		   
		}
				
	}
	
	
	public  void  GotoValue(int value)
	{
		
		if (value < servoMin) value = servoMin;
		else if (value > servoMax) value = servoMax;
				
		if (debug)
		{
	       System.out.println("Servo value: " + value);
		   
		}
		
		ServoBoard.setPWM(pin, 0, value); 
				
	}
	
	public void GotoRad(int value)
	{
		
		if (value < -90) value = -90;
		else if (value > 90) value = 90;
				
		int radpos = servoMin + (value + 90)*(servoMax - servoMin)/180;
		
		if (debug)
		{
	       System.out.println("Servo value: "+ radpos + " (" + value + " Degree)") ;
		   
		}
		ServoBoard.setPWM(pin, 0, radpos ); 
				
	}
	
	
	public void Speed(int value)
	{
		
		if (value < -100) value = -100;
		else if (value > 100) value = 100;
				
		ServoBoard.setPWM(pin, 0, servoMin + (value + 100)*(servoMax - servoMin)/200 ); 
		if (debug)
		{
	       System.out.println("Speed value: " + value) ;
		   
		}
				
	}
	
	
	
}
