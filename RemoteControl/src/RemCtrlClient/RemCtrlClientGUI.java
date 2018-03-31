package RemCtrlClient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
//import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.ButtonGroup;
//import java.awt.GridLayout;
import javax.swing.JSlider;
import javax.swing.JTextField;
//import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

//
//
// RemCtrlClientGUI
//
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
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
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
		
		final JTextArea textArea = new JTextArea();
		final JSlider slider = new JSlider();
		
		pin = 3;
		frame = new JFrame();
		frame.setBounds(100, 100, 479, 511);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		
		textFieldServo = new JTextField();
		textFieldServo.setText("3");
		textFieldServo.setColumns(10);
		
		
        JLabel lblServoControl = new JLabel("SERVO CONTROL");
		
		JLabel lblLedControl = new JLabel("LED CONTROL");
		
		
		
		//
		// Slider
		//
		//JSlider slider = new JSlider();
		int value = HttpClient.GET_SERVO_SPEED(pin);
		slider.setValue(value);
		
		slider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				textArea.setText("Slider:" + slider.getValue());
			//	int pin = Integer.parseInt(textFieldServo.getText());
				HttpClient.SERVO_SPEED(pin,slider.getValue());
											
			}
		});
		slider.setMinimum(-100);
		
		//
		// Button Left
		//
		JButton btnLeft = new JButton("LEFT");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				 textArea.setText("SERVO MIN");
				 HttpClient.SERVO_MIN(pin);
				 slider.setValue(-100);
				
			}
		});
		
		//
		// Button Neutral
		//
		JButton btnCenter = new JButton("Neutral");
		btnCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				textArea.setText("SERVO NEUTRAL");
				HttpClient.SERVO_NEUTRAL(pin);
				 slider.setValue(0);
			}
		});
		
		//
		// Button Right
		//
		JButton btnRight = new JButton("RIGHT");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				textArea.setText("SERVO MAX");
				HttpClient.SERVO_MAX(pin);
				 slider.setValue(100);
				
			}
		});
		
		//
		// Button SET
		//
		JButton btnSETButton = new JButton("SET");
		btnSETButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				pin = Integer.parseInt(textFieldServo.getText());
				int value = HttpClient.GET_SERVO_SPEED(pin);
				slider.setValue(value);
				
			}
		});
		
		
		//
		// Button ALL NEUTRAL
		//
		
		
		JButton btnAllNeutral = new JButton("ALL NEUTRAL");
		btnAllNeutral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				HttpClient.ALL_NEUTRAL();
				
				// update Slider
				pin = Integer.parseInt(textFieldServo.getText());
				int value = HttpClient.GET_SERVO_SPEED(pin);
				slider.setValue(value);
				
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAllNeutral)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 324, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(lblLedControl, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblServoControl, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
									.addComponent(textFieldServo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(20)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(btnSETButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(btnCenter, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(btnRight, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))))
							.addComponent(slider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap(112, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addComponent(lblServoControl)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldServo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSETButton))
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLeft)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnCenter)
							.addComponent(btnRight)))
					.addGap(24)
					.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblLedControl)
					.addGap(137)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(21)
					.addComponent(btnAllNeutral)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout); 
		
		
	}
}
