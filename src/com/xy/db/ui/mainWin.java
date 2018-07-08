package com.xy.db.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.xy.db.dao.IUserDAO;
import com.xy.db.dao.UserDAO;
import com.xy.db.entity.Employees;
import com.xy.db.util.TransformUtil;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class mainWin extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private static Vector<String> head = new Vector<String>();
	private UserDAO userDAO;
	
	/**
	 * Create the frame.
	 */
	public mainWin() {
		setTitle("UserMananger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JButton btnFind = new JButton("查找");
		
		//获取数据
		userDAO = new UserDAO();
		Vector<Vector<Object>> data = userDAO.getData();
		btnFind.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FindDialog findDialog = new FindDialog();
				findDialog.setVisible(true);
			}	
		});
		
		JButton button_2 = new JButton("刷新");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshData(data);
			}
		});
		panel.add(button_2);
		
		panel.add(btnFind);
		
		JButton btnAdd = new JButton("添加");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 new AddDialog();
				 refreshData(data);
			}
		});
		panel.add(btnAdd);
		
		JButton btnAlter = new JButton("修改");
		btnAlter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int index = table.getSelectedRow();
				 if(index<0){
					 JOptionPane.showMessageDialog(null, "没有选中行");
				 }else{
					 Vector<Object> row = data.get(index);
					 new AddDialog(row);
					 refreshData(data);
				 }
			}
		});
		panel.add(btnAlter);
		
		JButton btnDelete = new JButton("删除");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = table.getSelectedRow();
				//System.out.println(index);
				if(index<0 || index>=data.size()) {
					JOptionPane.showMessageDialog(null, "没有选择删除的行");
					return;
				}
				Integer id = (Integer) data.get(index).get(0);
				
				int ret = JOptionPane.showConfirmDialog(null, "确认删除");
				if (ret==JOptionPane.YES_OPTION) {
					
					userDAO.deleteData(id);
					refreshData(data);
				}
				
			}
		});
		panel.add(btnDelete);
		
		JButton button = new JButton("统计");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CountDialog();
			}
		});
		panel.add(button);
		
		JButton button_1 = new JButton("保存");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File path = getFile();
				Boolean isOk = userDAO.saveAsJson(userDAO.getData(),path);
				hint(isOk);
			}
		});
		panel.add(button_1);
		
		JButton btnNewButton = new JButton("读取");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File path = getFile();
				boolean isOk = userDAO.readJson(path);
				hint(isOk);
				Employees employees = userDAO.getEmployees();
				data.clear();
				data.addAll(TransformUtil.employeesToVector(employees));
				table.updateUI();
			}
		});
		panel.add(btnNewButton);
		
		JButton button_3 = new JButton("清空");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				data.clear();
				table.updateUI();
			}
		});
		panel.add(button_3);
		
		/*
		 * 添加表头
		 */
		head.add("员工号");
		head.add("姓名");
		head.add("性别");
		head.add("部门");
		head.add("等级");
		head.add("薪水");
		
		table = new JTable(data,head);
		JScrollPane sp = new JScrollPane(table);
		contentPane.add(sp, BorderLayout.CENTER);
	}

	private void refreshData(Vector<Vector<Object>> data) {
		data.clear();
		data.addAll(userDAO.getData());
		table.updateUI();
	}
	
	/*
	 * 通过文件访选择器选择文件
	 * 并返回文件
	 */
	public File getFile(){
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(this);
		File file_choosed = fc.getSelectedFile();
		return file_choosed;
	}
	/*
	 * 提示
	 */
	private void hint(Boolean isOK) {
		if(isOK){
			JOptionPane.showMessageDialog(null, "添加成功");
		}else{
			JOptionPane.showMessageDialog(null, "添加失败");
		}
	}
	
	
}
