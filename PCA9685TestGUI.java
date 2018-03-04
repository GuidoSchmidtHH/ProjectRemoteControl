
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

/**
 * Test GUI for PCA
 */


public class PCA9685TestGUI extends JFrame 
implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea;
	JButton buttonHello;


	static PCA9685LED LED_1;

	public static void main(String[] args) throws UnsupportedBusNumberException 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});



		PCA9685Board servoBoardTest; 
		servoBoardTest = new PCA9685Board(0x41);
		servoBoardTest.setPWMFreq(60); // Set frequency to 60 Hz


		LED_1 = new PCA9685LED(servoBoardTest, 3, true);


	}

	private static void createAndShowGUI() 
	{
		PCA9685TestGUI myFrame = new PCA9685TestGUI();

		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myFrame.prepareUI();

		myFrame.pack();
		myFrame.setVisible(true);
	}

	private void prepareUI()
	{
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane panel = new JScrollPane(textArea);
		panel.setPreferredSize(new Dimension(300, 100));

		buttonHello = new JButton("LED");
		buttonHello.addActionListener(this);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(buttonHello, BorderLayout.PAGE_END);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		textArea.setText("An/ Aus");

		if (LED_1.GetLEDValue()<50)
		{
			LED_1.An(); 	
			textArea.setText("An");
		}
		else
		{
			LED_1.Aus();
			textArea.setText("Aus");
		}


	}

}
