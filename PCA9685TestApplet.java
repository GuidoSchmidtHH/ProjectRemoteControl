
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;


public class PCA9685TestApplet extends Applet implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static PCA9685LED LED_1;

	
	public void init() 
	{
		button1 = new Button("ON");
		add(button1);
		button1.addActionListener(this);

		button2 = new Button("OFF");
		add(button2);
		button2.addActionListener(this);
		
		PCA9685Board servoBoardTest = null; 
		try {
			servoBoardTest = new PCA9685Board(0x41);
		} catch (UnsupportedBusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		servoBoardTest.setPWMFreq(60); // Set frequency to 60 Hz


		LED_1 = new PCA9685LED(servoBoardTest, 15, true);
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1)
		{
			
		
			System.out.println("ON was pressed");
			LED_1.An(); 	
			
		}
			
		else
		{
			
		
			System.out.println("OFF was pressed");
			LED_1.Aus(); 	
			
		}
	}

	Button button1, button2;
}
