package RemCtrlClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

//import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;


public class RemCtrlClientGUITest extends JFrame  implements ActionListener
{

	RemCtrlHttpClient HttpClient = new RemCtrlHttpClient();
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea;
	JButton button_max;
	JButton button_min;
	
	public static void main(String[] args) //throws UnsupportedBusNumberException 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() 
			{
				createAndShowGUI();
			}
		});




	}

	private static void createAndShowGUI() 
	{
	
		RemCtrlClientGUITest myFrame = new RemCtrlClientGUITest();

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

		button_max = new JButton("SERVO max");
		button_max.addActionListener(this);
		button_min = new JButton("SERVO min");
		button_min.addActionListener(this);
		
		
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(button_min, BorderLayout.PAGE_START);
		getContentPane().add(button_max, BorderLayout.PAGE_END);
	}

	public void actionPerformed(ActionEvent e) 
	{
		
		if (e.getSource() == button_max)	
		{
		   textArea.setText("SERVO MAX");
		   HttpClient.SERVO_MAX(3);
		}
		else if (e.getSource() == button_min)	
		{
			   textArea.setText("SERVO MIN");
			   HttpClient.SERVO_MIN(3);
	    }
		
		
	}

}
