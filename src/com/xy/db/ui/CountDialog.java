package com.xy.db.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.xy.db.dao.IUserDAO;
import com.xy.db.dao.UserDAO;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CountDialog extends JDialog {
	private JTable table;
	private IUserDAO userDAO = new UserDAO();
	private Vector<String> head=new Vector<>();
	private Vector<Vector<Object>> counts = new Vector<>(); 
	
	/**
	 * Create the dialog.
	 */
	public CountDialog() {
		setTitle("统计");
		setBounds(100, 100, 450, 300);
		JComboBox cboxType = new JComboBox();
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.NORTH);
			{
				
				cboxType.setModel(new DefaultComboBoxModel(new String[] {"部门", "性别", "等级"}));
				panel.add(cboxType);
			}
			{
				JButton button = new JButton("统计数量");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String type = (String) cboxType.getSelectedItem();
						counts.clear();
						//改变表头
						table.getColumnModel().getColumn(0).setHeaderValue(type);
						/*
						 * 按类别查询返回数据
						 * 并更新界面
						 */
						if(type.equals("等级"))
							counts.addAll(userDAO.getCount("emp_rank"));
						if(type.equals("部门"))
							counts.addAll(userDAO.getCount("emp_dept"));
						if(type.equals("性别"))
							counts.addAll(userDAO.getCount("emp_sex"));
						System.out.println(counts);
						table.updateUI();
					}
				});
				panel.add(button);
			}
		}
		{
			
			counts.addAll(userDAO.getCount("emp_dept"));
			head.add("部门");
			head.add("人数");
	
			table = new JTable(counts,head);
			JScrollPane sp = new JScrollPane(table);
			getContentPane().add(sp, BorderLayout.CENTER);
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		this.setVisible(true);
	}
	
}
