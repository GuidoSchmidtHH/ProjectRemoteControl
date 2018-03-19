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

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;


public class RemCtrlClientGUI extends JFrame  implements ActionListener
{

	RemCtrlHttpClient HttpClient = new RemCtrlHttpClient();
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea;
	JButton buttonHello;

	public static void main(String[] args) throws UnsupportedBusNumberException 
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
	
		RemCtrlClientGUI myFrame = new RemCtrlClientGUI();

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

		buttonHello = new JButton("SERVO");
		buttonHello.addActionListener(this);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(buttonHello, BorderLayout.PAGE_END);
	}

	public void actionPerformed(ActionEvent e) 
	{
		textArea.setText("An/ Aus");
		
		HttpClient.SERVO_MAX(3);
	
	}

}
