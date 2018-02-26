package O2jam_script;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class gui {
	
	private static volatile boolean quit;
    private static HHOOK hhk;
    private static LowLevelKeyboardProc keyboardHook;

	private JFrame frame;
	private TextField textField_5;
	
	boolean s_key = false;
	boolean d_key = false;
	boolean f_key = false;
	boolean k_key = false;
	boolean l_key = false;
	
	int start_frame;
	int current_frame = 0;
	int last_frame = 0;
	boolean last_key = true;
	int remainder;
	int remainder_2 = 0;
	String folder_path = "C:\\";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
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
	public gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 450, 148);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final TextField textField = new TextField();
		textField.setEditable(false);
		textField.setBounds(10, 75, 50, 22);
		frame.getContentPane().add(textField);
		
		TextField textField_1 = new TextField();
		textField_1.setEditable(false);
		textField_1.setBounds(66, 75, 50, 22);
		frame.getContentPane().add(textField_1);
		
		TextField textField_2 = new TextField();
		textField_2.setEditable(false);
		textField_2.setBounds(122, 75, 50, 22);
		frame.getContentPane().add(textField_2);
		
		TextField textField_3 = new TextField();
		textField_3.setEditable(false);
		textField_3.setBounds(178, 75, 50, 22);
		frame.getContentPane().add(textField_3);
		
		TextField textField_4 = new TextField();
		textField_4.setEditable(false);
		textField_4.setBounds(233, 75, 50, 22);
		frame.getContentPane().add(textField_4);
		
		TextField txtSdfs = new TextField();
		txtSdfs.setBounds(10, 30, 86, 20);
		frame.getContentPane().add(txtSdfs);
		txtSdfs.setColumns(10);
		
		JLabel lblStart = new JLabel("Start");
		lblStart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStart.setForeground(Color.WHITE);
		lblStart.setBounds(10, 11, 86, 14);
		frame.getContentPane().add(lblStart);
		
		textField_5 = new TextField();
		textField_5.setColumns(10);
		textField_5.setBounds(106, 30, 86, 20);
		frame.getContentPane().add(textField_5);
		
		
		TextField textField_6 = new TextField();
		textField_6.setColumns(10);
		textField_6.setBounds(198, 30, 86, 20);
		frame.getContentPane().add(textField_6);
		
		JButton btnNewButton = new JButton("Enable");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){
				String buffer;
				buffer = txtSdfs.getText();
				if (!buffer.matches("^?\\d+$")){
					JOptionPane.showMessageDialog(frame, "Invalid input on start");
					return;
				}
				textField_5.setText(txtSdfs.getText());
				textField_6.setText(txtSdfs.getText());
				
				current_frame = Integer.parseInt(txtSdfs.getText());
				last_frame = Integer.parseInt(txtSdfs.getText());
				
//////////////////////Keyboard Struct////////////////////////////////////////////////////////////////////////
				final User32 lib = User32.INSTANCE;
		        HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
		        keyboardHook = new LowLevelKeyboardProc() {
		            @Override
		            public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
		                if (nCode >= 0) {
		                	//Color cmodel;
		                    switch(wParam.intValue()) {
		                    //case WinUser.WM_KEYUP:
		                    case WinUser.WM_KEYDOWN:
		                    //case WinUser.WM_SYSKEYUP:
		                    //case WinUser.WM_SYSKEYDOWN:
		                    try{
		                        //System.err.println("in callback, key=" + info.vkCode);
		                    	if (info.vkCode == 39) {
		                    		String val;
		                    		int edit = 0;
		                    		val = textField_5.getText();
		                    		edit = Integer.parseInt(val);
		                    		edit++;
		                    		textField_5.setText(Integer.toString(edit));
		                    		current_frame = edit;
		                    	}
		                    	else if (info.vkCode == 37) {
		                    		String val;
		                    		int edit = 0;
		                    		val = textField_5.getText();
		                    		edit = Integer.parseInt(val);
		                    		edit--;
		                    		textField_5.setText(Integer.toString(edit));
		                    		current_frame = edit;
		                    	}
		                    	else if (info.vkCode == 83) {
		                    		//s		          
		                    		s_key = !s_key;
		                    		if(s_key){
		                    			textField.setBackground(Color.GREEN);
		                    			writeToFile( folder_path + "Z updown.txt", "1");
		                    		}
		                    		else{
		                    			textField.setBackground(new Color(-986896, true));
		                    			writeToFile( folder_path + "Z updown.txt", "0");
		                    		}
		                    		writeToFile( folder_path + "Z keys.txt", "83");
		                    		one_one(folder_path);
	                   				last_frame = current_frame;
	                   				textField_6.setText(Integer.toString(current_frame));
		                    	}
		                    	else if (info.vkCode == 68) {
		                    		//s		          
		                    		d_key = !d_key;
		                    		if(d_key){
		                    			textField_1.setBackground(Color.GREEN);
		                    			writeToFile( folder_path + "Z updown.txt", "1");
		                    		}
		                    		else{
		                    			textField_1.setBackground(new Color(-986896, true));
		                    			writeToFile( folder_path + "Z updown.txt", "0");
		                    		}
		                    		writeToFile( folder_path + "Z keys.txt", "68");
		                    		one_one(folder_path);
	                   				last_frame = current_frame;
	                   				textField_6.setText(Integer.toString(current_frame));
		                    	}
		                    	else if (info.vkCode == 70) {
		                    		//s		          
		                    		f_key = !f_key;
		                    		if(f_key){
		                    			textField_2.setBackground(Color.GREEN);
		                    			writeToFile( folder_path + "Z updown.txt", "1");
		                    		}
		                    		else{
		                    			textField_2.setBackground(new Color(-986896, true));
		                    			writeToFile( folder_path + "Z updown.txt", "0");
		                    		}
		                    		writeToFile( folder_path + "Z keys.txt", "70");
		                    		one_one(folder_path);
	                   				last_frame = current_frame;
	                   				textField_6.setText(Integer.toString(current_frame));
		                    	}
		                    	else if (info.vkCode == 75) {
		                    		//s		          
		                    		k_key = !k_key;
		                    		if(k_key){
		                    			textField_3.setBackground(Color.GREEN);
		                    			writeToFile( folder_path + "Z updown.txt", "1");
		                    		}
		                    		else{
		                    			textField_3.setBackground(new Color(-986896, true));
		                    			writeToFile( folder_path + "Z updown.txt", "0");
		                    		}
		                    		writeToFile( folder_path + "Z keys.txt", "75");
		                    		one_one(folder_path);
	                   				last_frame = current_frame;
	                   				textField_6.setText(Integer.toString(current_frame));
		                    	}
		                    	else if (info.vkCode == 76) {
		                    		//s		          
		                    		l_key = !l_key;
		                    		if(l_key){
		                    			textField_4.setBackground(Color.GREEN);
		                    			writeToFile( folder_path + "Z updown.txt", "1");
		                    		}
		                    		else{
		                    			textField_4.setBackground(new Color(-986896, true));
		                    			writeToFile( folder_path + "Z updown.txt", "0");
		                    		}
		                    		writeToFile( folder_path + "Z keys.txt", "76");
		                    		one_one(folder_path);
	                   				last_frame = current_frame;
	                   				textField_6.setText(Integer.toString(current_frame));
		                    	}
		                    	else if (info.vkCode == 81) {
		                            quit = true;
		                        }
		                    }
		                    catch (IOException e) {
		                    	
		                    }
		                    }
		                }

		                Pointer ptr = info.getPointer();
		                long peer = Pointer.nativeValue(ptr);
		                return lib.CallNextHookEx(hhk, nCode, wParam, new LPARAM(peer));
		            }
		        };
		        hhk = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hMod, 0);
		        System.out.println("Keyboard hook installed, type anywhere, 'q' to quit");
		        new Thread() {
		            @Override
		            public void run() {
		                while (!quit) {
		                    try { Thread.sleep(10); } catch(Exception e) { }
		                }
		                System.err.println("unhook and exit");
		                lib.UnhookWindowsHookEx(hhk);
		                Thread.currentThread().interrupt();
		                return;
		                //System.exit(0);
		            }
		        }.start();

		        // This bit never returns from GetMessage
		        int result;
		        MSG msg = new MSG();
		        while ((result = lib.GetMessage(msg, null, 0, 0)) != 0 && !quit) {
		            if (result == -1) {
		                System.err.println("error in get message");
		                break;
		            }
		            else {
		                System.err.println("got message");
		                lib.TranslateMessage(msg);
		                lib.DispatchMessage(msg);
		            }
		        }
		        lib.UnhookWindowsHookEx(hhk);
			}
		});
//////////////////////END Keyboard Struct////////////////////////////////////////////////////////////////////////
		btnNewButton.setBounds(289, 74, 135, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblCurrent = new JLabel("Current");
		lblCurrent.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrent.setForeground(Color.WHITE);
		lblCurrent.setBounds(106, 11, 86, 14);
		frame.getContentPane().add(lblCurrent);
		
		JLabel lblLast = new JLabel("Last");
		lblLast.setHorizontalAlignment(SwingConstants.CENTER);
		lblLast.setForeground(Color.WHITE);
		lblLast.setBounds(198, 11, 86, 14);
		frame.getContentPane().add(lblLast);
	}
	
	public void writeToFile( String path, String writeThis ) throws IOException{
		FileWriter write = new FileWriter( path , true );
		PrintWriter print_line = new PrintWriter( write );
		
		print_line.printf( "%s" + "%n" , writeThis);
		print_line.close();
	}
	
	public void one_one (String path) throws IOException{
		int delay;
		double decimal;
		if (current_frame == last_frame){
			delay = 1;
			remainder_2++;
		}
		else{
			delay = ((current_frame - last_frame) * 100 / 6);
			decimal = (((current_frame - last_frame) * 100 / 6.0) % delay);
			if ( decimal < 0.4  && decimal > 0.2 )
				remainder++;
			else if ( decimal < 0.8 )
				remainder += 2;
			if (remainder > 3){
				delay++;
				remainder -= 3;
			}
			delay -= remainder_2;
			remainder_2 = 0;
		}
		writeToFile(path + "Z delay.txt", Integer.toString(delay));
	}
}
