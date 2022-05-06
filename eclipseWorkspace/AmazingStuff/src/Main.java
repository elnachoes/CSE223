import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSplitPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Canvas;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Button;
import java.awt.TextField;
import java.awt.Panel;

public class Main extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\corbi\\Pictures\\Saved Pictures\\blueMeteroritre.png"));
		setTitle("this is the title frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 796, 711);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("farts");
			}
		});
		contentPane.setBackground(Color.RED);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(498, 66, 229, 199);
		contentPane.add(panel);
		
		Button button = new Button("Player 2");
		button.setBounds(498, 38, 70, 22);
		contentPane.add(button);
		
		Button button_1 = new Button("Player 1");
		button_1.setBounds(498, 10, 70, 22);
		contentPane.add(button_1);
		
		TextField textField = new TextField();
		textField.setBounds(574, 10, 153, 22);
		contentPane.add(textField);
		
		TextField textField_1 = new TextField();
		textField_1.setBounds(574, 38, 153, 22);
		contentPane.add(textField_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 450, 396);
		contentPane.add(panel_1);
		
		Canvas canvas = new Canvas();
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		panel_1.add(canvas);
	}
}
