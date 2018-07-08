package com.xy.db.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.xy.db.dao.IUserDAO;
import com.xy.db.dao.UserDAO;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FindDialog extends JDialog {
	private JTable table;
	private JTable table_1;
	private JTextField tfTypeValue;
	private IUserDAO userDAO = new UserDAO();
	private Vector<Vector<Object>> data=new Vector<>();
	
	
	/**
	 * Create the dialog.
	 */
	public FindDialog() {
		setTitle("查询");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
		JComboBox cboxType = new JComboBox();
		tfTypeValue = new JTextField();
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.NORTH);
			{
				cboxType.setModel(new DefaultComboBoxModel(new String[] {"员工号", "姓名", "性别", "部门", "等级"}));
				panel.add(cboxType);
			}
			{
				panel.add(tfTypeValue);
				tfTypeValue.setColumns(10);
			}
			{
				JButton btnFind = new JButton("查询");
				btnFind.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String type = (String) cboxType.getSelectedItem();
						String value = tfTypeValue.getText();
						data.clear();
						if(value.trim().equals("")) {
							table.updateUI();
						}else {
							data.addAll(userDAO.findByType(typeTransform(type),value));
							table.updateUI();
						}
					}
				});
				panel.add(btnFind);
			}
		}
		{
			table_1 = new JTable();
			getContentPane().add(table_1, BorderLayout.CENTER);
		}
		/*
		 * 添加表头
		 */
		
		data=userDAO.getData();
		Vector<String>head = new Vector<>(); 
		head.add("员工号");
		head.add("姓名");
		head.add("性别");
		head.add("部门");
		head.add("等级");
		head.add("薪水");
		table = new JTable(data,head);
		JScrollPane sp = new JScrollPane(table);
		getContentPane().add(sp, BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	/*
	 * 按照查找的类型跟数据库字段进行映射
	 */
	public String typeTransform(String type) {
		switch (type) {
		case "员工号":
			return "emp_id";
		case "姓名":
			return "emp_name";
		case "性别":
			return "emp_sex";
		case "部门":
			return "emp_dept";
		case "等级":
			return "emp_rank";
		}
		return null;
	}
	
}
