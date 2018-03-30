package RemCtrlClient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class RemCtrlClientGUI 
{

	
	RemCtrlHttpClient HttpClient = new RemCtrlHttpClient();
	
	private int pin = 3;
	private JFrame frame;
	//private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textFieldServo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					RemCtrlClientGUI window = new RemCtrlClientGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RemCtrlClientGUI() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		
		
		pin = 3;
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(27, 179, 324, 71);
		frame.getContentPane().add(textArea);
		
		textFieldServo = new JTextField();
		textFieldServo.setText("3");
		textFieldServo.setBounds(30, 30, 86, 20);
		frame.getContentPane().add(textFieldServo);
		textFieldServo.setColumns(10);
		
		
		JSlider slider = new JSlider();
		slider.setValue(0);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				textArea.setText("Slider:" + slider.getValue());
			//	int pin = Integer.parseInt(textFieldServo.getText());
				HttpClient.SERVO_SPEED(pin,slider.getValue());
											
			}
		});
		slider.setMinimum(-100);
		slider.setBounds(82, 108, 200, 26);
		frame.getContentPane().add(slider);
		
		
		JButton btnLeft = new JButton("LEFT");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				 textArea.setText("SERVO MIN");
				 HttpClient.SERVO_MIN(pin);
				 slider.setValue(-100);
				
			}
		});
		btnLeft.setBounds(27, 61, 89, 23);
		frame.getContentPane().add(btnLeft);
		
		
		
		
		
		JButton btnCenter = new JButton("Neutral");
		btnCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				textArea.setText("SERVO NEUTRAL");
				HttpClient.SERVO_NEUTRAL(pin);
				 slider.setValue(0);
			}
		});
		btnCenter.setBounds(136, 61, 89, 23);
		frame.getContentPane().add(btnCenter);
		
		JButton btnRight = new JButton("RIGHT");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				textArea.setText("SERVO MAX");
				HttpClient.SERVO_MAX(pin);
				 slider.setValue(100);
				
			}
		});
		btnRight.setBounds(262, 61, 89, 23);
		frame.getContentPane().add(btnRight);
		
		JButton btnSETButton = new JButton("SET");
		btnSETButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				pin = Integer.parseInt(textFieldServo.getText());
				int value = HttpClient.GET_SERVO_SPEED(pin);
				slider.setValue(value);
				
			}
		});
		btnSETButton.setBounds(136, 29, 89, 23);
		frame.getContentPane().add(btnSETButton);
		
		
	}
}
