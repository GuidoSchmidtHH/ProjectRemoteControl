
//package i2c.servo.pwm;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

import java.util.concurrent.TimeUnit;


/*
 * Servo Driver
 */
public class PCA9685Board 
{
	public final static int PCA9685_ADDRESS = 0x40;
	public final static int SUBADR1 = 0x02;
	public final static int SUBADR2 = 0x03;
	public final static int SUBADR3 = 0x04;
	public final static int MODE1 = 0x00;
	public final static int PRESCALE = 0xFE;
	public final static int LED0_ON_L = 0x06;
	public final static int LED0_ON_H = 0x07;
	public final static int LED0_OFF_L = 0x08;
	public final static int LED0_OFF_H = 0x09;
	public final static int ALL_LED_ON_L = 0xFA;
	public final static int ALL_LED_ON_H = 0xFB;
	public final static int ALL_LED_OFF_L = 0xFC;
	public final static int ALL_LED_OFF_H = 0xFD;

	private static boolean verbose = true;
	//private int freq = 60;

	private I2CBus bus;
	private I2CDevice servoDriver;

	public PCA9685Board() throws I2CFactory.UnsupportedBusNumberException 
	{
		this(PCA9685_ADDRESS); // 0x40 obtained through sudo i2cdetect -y 1
	}

	public PCA9685Board(int address) throws I2CFactory.UnsupportedBusNumberException 
	{
		try {
			// Get I2C bus
			bus = I2CFactory.getInstance(I2CBus.BUS_1); // Depends on the RasPI version
			if (verbose) {
				System.out.println("Connected to bus. OK.");
			}

			// Get the device itself
			servoDriver = bus.getDevice(address);
			if (verbose) {
				System.out.println("Connected to device. OK.");
			}
			// Reseting
			servoDriver.write(MODE1, (byte) 0x00);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	
	
	/**
	 * @param freq 40..1000
	 */
	public void setPWMFreq(int freq) 
	{
		//this.freq = freq;
		float preScaleVal = 25000000.0f; // 25MHz
		preScaleVal /= 4096.0;           // 4096: 12-bit
		preScaleVal /= freq;
		preScaleVal -= 1.0;
		if (verbose) {
			System.out.println("Setting PWM frequency to " + freq + " Hz");
			System.out.println("Estimated pre-scale: " + preScaleVal);
		}
		double preScale = Math.floor(preScaleVal + 0.5);
		if (verbose) {
			System.out.println("Final pre-scale: " + preScale);
		}

		try {
			byte oldmode = (byte) servoDriver.read(MODE1);
			byte newmode = (byte) ((oldmode & 0x7F) | 0x10); // sleep
			servoDriver.write(MODE1, newmode);              // go to sleep
			servoDriver.write(PRESCALE, (byte) (Math.floor(preScale)));
			servoDriver.write(MODE1, oldmode);
		//	delay(5);
			servoDriver.write(MODE1, (byte) (oldmode | 0x80));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	

	/**
	 * @param channel 0..15
	 * @param on      0..4095 (2^12 positions)
	 * @param off     0..4095 (2^12 positions)
	 */
	public void setPWM(int channel, int on, int off) throws IllegalArgumentException 
	{
		if (channel < 0 || channel > 15) {
			throw new IllegalArgumentException("Channel must be in [0, 15]");
		}
		if (on < 0 || on > 4095) {
			throw new IllegalArgumentException("On must be in [0, 4095]");
		}
		if (off < 0 || off > 4095) {
			throw new IllegalArgumentException("Off must be in [0, 4095]");
		}
		if (on > off) {
			throw new IllegalArgumentException("Off must be greater than On");
		}
		try {
			servoDriver.write(LED0_ON_L + 4 * channel, (byte) (on & 0xFF));
			servoDriver.write(LED0_ON_H + 4 * channel, (byte) (on >> 8));
			servoDriver.write(LED0_OFF_L + 4 * channel, (byte) (off & 0xFF));
			servoDriver.write(LED0_OFF_H + 4 * channel, (byte) (off >> 8));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

		
	public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, InterruptedException 
	{
		int freq = 60;
		if (args.length > 0) 
		{
			freq = Integer.parseInt(args[0]);
		}
		PCA9685Board servoBoard = new PCA9685Board();
		servoBoard.setPWMFreq(freq); // Set frequency to 60 Hz
		int servoMin = 120;   // 122 - 130;   // was 150. Min pulse length out of 4096
		int servoMax = 615;   // 615 - was 600. Max pulse length out of 4096
	
		servoBoard.setPWM(1, 0, servoMin);  
		servoBoard.setPWM(0, 0, 4095);
		servoBoard.setPWM(2, 0, 4095);
		
		TimeUnit.SECONDS.sleep(1);
		
		//servoBoard.setPWM(0, 0, 2000);
		
		int light = 4095;
		
		
		for (int i=servoMin; i<=servoMax; i=i+25 )
		{
			servoBoard.setPWM(1, 0, i);
			
			TimeUnit.MILLISECONDS.sleep(100);
			light = light - 200;
			if (light <0) light = 0;
			
			
			servoBoard.setPWM(0, 0, light);
			
						
		}
			
				
		TimeUnit.SECONDS.sleep(1);
		
		servoBoard.setPWM(1, 0, servoMax);
		servoBoard.setPWM(0, 0, 0);
		servoBoard.setPWM(2, 0, 0);
					
	}

	
}
