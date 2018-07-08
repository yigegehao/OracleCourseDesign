package com.xy.db.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.xy.db.dao.IUserDAO;
import com.xy.db.dao.UserDAO;
import com.xy.db.entity.Employee;

import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AddDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTextField tfId;
	private JTextField tfName;
	private JTextField tfSalary;
	private JRadioButton rdbtnM;
	private JRadioButton rdbtnF;
	private JComboBox cboxRank;
	private JComboBox cboxDept;
	
	private IUserDAO userDAO = new UserDAO();  
	private Employee emp;
	private int tagId=0;  //标记员工号是否修改，0为没有修改
	

	/**
	 * Create the dialog.
	 */
	public AddDialog() {
		this(null);
	}
	
	public AddDialog(final Vector<Object> vector) {
		initUI();
		/*
		 * 如果选中数据不为空
		 * 则把数据显示在修改界面中
		 */
		if(vector!=null){
			tfId.setText(""+vector.get(0));
			tagId = (int) vector.get(0);  //
			tfName.setText(""+vector.get(1));
			if(vector.get(2).equals("女")) {
				rdbtnF.setSelected(true);
			}
			cboxDept.setSelectedItem(vector.get(3));
			cboxRank.setSelectedItem(vector.get(4));
			tfSalary.setText(""+vector.get(5));
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						emp = new Employee(); //创建一个新员工类保存新增员工信息
						emp.setId(Integer.valueOf(tfId.getText()));
						emp.setName(tfName.getText());
						emp.setSex(rdbtnM.isSelected()?"男":"女");
						emp.setDept((String)cboxDept.getSelectedItem());
						emp.setRank((String)cboxRank.getSelectedItem()); 
						emp.setSalary(tfSalary.getText());
						boolean isOK=false;
						if(vector==null){
							isOK =userDAO.addData(emp);
						}else{
							if(emp.getId()!=tagId)
								 isOK =userDAO.updateData(emp,tagId);
							else 
								 isOK =userDAO.updateData(emp,0);
						}
						hint(isOK);
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AddDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
		
	}
	
	/*
	 * 提示操作是否成功
	 */
	private void hint(Boolean isOK) {
		if(isOK){
			JOptionPane.showMessageDialog(null, "添加成功");
			AddDialog.this.dispose();
		}else{
			JOptionPane.showMessageDialog(null, "数据库添加重复");
		}
	}
	
	/*
	 * 初始化UI
	 */
	private void initUI() {
		setTitle("AddDialog");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblEId = new JLabel("员工号");
			lblEId.setBounds(85, 10, 54, 15);
			contentPanel.add(lblEId);
		}
		
		tfId = new JTextField();
		tfId.setBounds(162, 7, 122, 21);
		contentPanel.add(tfId);
		tfId.setColumns(10);
		
		JLabel lblName = new JLabel("姓名");
		lblName.setBounds(85, 38, 54, 15);
		contentPanel.add(lblName);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(162, 35, 122, 21);
		contentPanel.add(tfName);
		
		JLabel lblSex = new JLabel("性别");
		lblSex.setBounds(85, 84, 54, 15);
		contentPanel.add(lblSex);
		
		rdbtnM = new JRadioButton("男");
		rdbtnM.setBounds(162, 80, 70, 23);
		contentPanel.add(rdbtnM);
		
		rdbtnF = new JRadioButton("女");
		rdbtnF.setBounds(234, 80, 60, 23);
		contentPanel.add(rdbtnF);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnM);
		bg.add(rdbtnF);
		
		cboxDept = new JComboBox();
		cboxDept.setModel(new DefaultComboBoxModel(new String[] {"技术部", "销售部"}));
		cboxDept.setSelectedIndex(1);
		cboxDept.setBounds(162, 109, 122, 21);
		contentPanel.add(cboxDept);
		
		JLabel lblDept = new JLabel("部门");
		lblDept.setBounds(85, 112, 46, 15);
		contentPanel.add(lblDept);
		
		JLabel lblRank = new JLabel("等级");
		lblRank.setBounds(85, 151, 54, 15);
		contentPanel.add(lblRank);
		
		cboxRank = new JComboBox();
		cboxRank.setModel(new DefaultComboBoxModel(new String[] {"经理", "技术员", "销售员", "销售经理"}));
		cboxRank.setSelectedIndex(2);
		cboxRank.setBounds(162, 148, 122, 21);
		contentPanel.add(cboxRank);
		
		JLabel lblSalary = new JLabel("薪水");
		lblSalary.setBounds(85, 187, 54, 15);
		contentPanel.add(lblSalary);
		
		tfSalary = new JTextField();
		tfSalary.setBounds(162, 184, 122, 21);
		contentPanel.add(tfSalary);
		tfSalary.setColumns(10);
	}
}
