package gui_system;

import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import Services.UtilizatorService;
import Utils.EncryptService;
import Utils.Functions;
import entity.Utilizator;
import main.MainFrame;

import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import Singleton.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class LoginPanel extends JPanel {
	private JTextField IdTF;
	private JPasswordField PasswordTF;
	private JButton LoginButton;
	private JLabel warningLbl;
	
	main.MainFrame parentFrame;

	public LoginPanel() {
		Singleton.getInstance().getCurrentWeek();
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(77, 89, 190, 73);
		add(panel);
		panel.setLayout(null);
		
		JLabel IdLbl = new JLabel("ID");
		IdLbl.setBounds(9, 3, 11, 14);
		panel.add(IdLbl);
		
		IdTF = new JTextField();
		IdTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					doLogin();
			}
		});
		IdTF.setHorizontalAlignment(SwingConstants.CENTER);
		IdTF.setBounds(35, 0, 155, 20);
		panel.add(IdTF);
		IdTF.setColumns(10);
		
		JLabel PasswordLbl = new JLabel("Parola");
		PasswordLbl.setBounds(0, 28, 30, 14);
		panel.add(PasswordLbl);
		
		PasswordTF = new JPasswordField();
		PasswordTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					doLogin();
			}
		});
		PasswordTF.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordTF.setBounds(35, 25, 155, 20);
		panel.add(PasswordTF);
		PasswordTF.setColumns(10);
		
		warningLbl = new JLabel("");
		warningLbl.setForeground(Color.RED);
		warningLbl.setBounds(77, 173, 146, 14);
		
		add(warningLbl);
		
		LoginButton = new JButton("Logare");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		LoginButton.setBounds(35, 50, 155, 23);
		panel.add(LoginButton);
		setPreferredSize(new Dimension(350, 250));
		
	}
	
	public void doLogin() {
		if(	!Functions.stringIsNullOrEmpty(IdTF.getText())){					
			Utilizator user = UtilizatorService.getUtilizatorByUsername(IdTF.getText());
			if(user != null){
				try {
					if(user.getPassword() == null){
						System.out.println("yeah");
						NewPasswordModal modalPanel = new NewPasswordModal(user);
						modalPanel.setParentFrame(parentFrame);
					}else{
						
						if(user.getPassword().equals(
								EncryptService.getHashOfString(String.valueOf(PasswordTF.getPassword())))){
							System.out.println("nooh");
							Singleton.getInstance().currentUser = user;
							PasswordTF.setText("");
							IdTF.setText("");
							parentFrame.showMainPanel();
						}else{
							warningLbl.setText("Datele nu corespund!");
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else{
				warningLbl.setText("Nu exista acest cont!");	
			}
		}else{										
			warningLbl.setText("Introduceti un nume de cont!");					
		}
	}
	
	public void setParentFrame(MainFrame frame) {
		parentFrame = frame;
	}
	
	
	public void resetState() {
		IdTF.setText("");
		PasswordTF.setText("");
	}
	
	public void createPasswordForNewUser(Utilizator x){
		//if(x.getPassword() == null){
			
		//}
		
		JPasswordField Password = new JPasswordField();
		JPasswordField PasswordAgain = new JPasswordField();
		JLabel warningLabel = new JLabel();
		warningLabel.setForeground(Color.red);
		final JComponent[] inputs = new JComponent[] {
		        new JLabel("Parola"),
		        Password,
		        new JLabel("Rescrieti parola"),
		        PasswordAgain,
		        warningLabel
		       
		};
		int result = JOptionPane.showConfirmDialog(null, inputs, "Seteaza o parola contului", JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			if(String.valueOf(PasswordAgain.getPassword()).equals(String.valueOf(PasswordAgain.getPassword()))){
				System.out.println("You entered " +
			    		String.valueOf(Password.getPassword()) + ", " +
			    		String.valueOf(PasswordAgain.getPassword()));
			}else{
				warningLabel.setText("Parolele nu corespund");
			}
		    
		} else {
		    System.out.println("User canceled / closed the dialog, result = " + result);
		}
	}
}
