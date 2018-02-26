import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;


public class GUI {

	private JFrame frame;
	public String lastFinal2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 704, 556);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextField formattedTextField = new JTextField();
		formattedTextField.setBounds(10, 11, 488, 20);
		frame.getContentPane().add(formattedTextField);
		
		JButton btnNewButton = new JButton("Browse Macro File");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser chooser;
				if (lastFinal2 != null) {
					chooser = new JFileChooser(lastFinal2);
				}
				else {
					chooser = new JFileChooser();
				}
				
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setDialogTitle("Locate Macro File");
				
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					formattedTextField.setText(chooser.getSelectedFile().getPath());
					lastFinal2 = formattedTextField.getText();
				}
			}
		});
		btnNewButton.setBounds(508, 10, 170, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("K ARRAY:");
		lblNewLabel.setBounds(20, 42, 61, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 67, 668, 99);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 202, 666, 97);
		frame.getContentPane().add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setWrapStyleWord(true);
		scrollPane_1.setViewportView(textArea_1);
		
		JLabel lblZArray = new JLabel("Z ARRAY:");
		lblZArray.setBounds(19, 176, 61, 14);
		frame.getContentPane().add(lblZArray);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 336, 666, 97);
		frame.getContentPane().add(scrollPane_2);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setWrapStyleWord(true);
		scrollPane_2.setViewportView(textArea_2);
		
		JLabel lblIArray = new JLabel("I ARRAY:");
		lblIArray.setBounds(19, 310, 61, 14);
		frame.getContentPane().add(lblIArray);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setBounds(477, 37, 106, 22);
		frame.getContentPane().add(textArea_3);
		
		JButton btnNewButton_1 = new JButton("START PARSING");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Keyparse run = new Keyparse(new File(lastFinal2));
					textArea.setText(run.K_array);
					textArea_1.setText(run.Z_array);
					textArea_2.setText(run.I_array);
					textArea_3.setText(String.valueOf(run.i));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_1.setBounds(10, 448, 656, 58);
		frame.getContentPane().add(btnNewButton_1);
		

		
		JLabel lblNewLabel_1 = new JLabel("Count:");
		lblNewLabel_1.setBounds(421, 42, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
