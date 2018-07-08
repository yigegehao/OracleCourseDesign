package com.xy.db.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.xy.db.dao.IUserDAO;
import com.xy.db.dao.UserDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.event.ActionEvent;

public class LoginDialog extends JDialog {

	/**
	 * 
	 */
	IUserDAO userDAO = new UserDAO();
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginDialog loginDialog = new LoginDialog();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the dialog.
	 */
	public LoginDialog() {
		setTitle("LoginWindow");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 442, 224);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("name");
			lblNewLabel.setBounds(96, 30, 54, 15);
			contentPanel.add(lblNewLabel);
		}
		
		textField = new JTextField();
		textField.setBounds(153, 27, 148, 21);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("pwd");
		label.setBounds(96, 73, 54, 15);
		contentPanel.add(label);
		
		textField_1 = new JPasswordField();
		textField_1.setColumns(10);
		textField_1.setBounds(153, 70, 148, 21);
		contentPanel.add(textField_1);
		JButton okButton = new JButton("login");
		okButton.setBounds(155, 122, 63, 23);
		contentPanel.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean result=false;
				result = userDAO.loginValidate(textField.getText(), textField_1.getText());
				System.out.println(result);
				//result=true;
				if(result){
					LoginDialog.this.dispose();
					mainWin frame = new mainWin();
					frame.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null, "error");
				}
			}
		});
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		
		{
			JButton cancelButton = new JButton("exit");
			cancelButton.setBounds(244, 122, 57, 23);
			contentPanel.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 234, 442, 32);
			
			getContentPane().add(buttonPane);
			{
				buttonPane.setLayout(null);
			}
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}
}
