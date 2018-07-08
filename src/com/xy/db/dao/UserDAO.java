package com.xy.db.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.PseudoColumnUsage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.xy.db.entity.Employee;
import com.xy.db.entity.Employees;
import com.xy.db.util.TransformUtil;

public class UserDAO implements IUserDAO{
	
	/*
	 * 设置连接数据库的地址，用户名，和密码
	 */
	private static	String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	private static	String user = "scott";
	private static	String password = "123456";
	private static Employees employees;
	
	
	public static Employees getEmployees() {
		return employees;
	}

	/*
	 * 加载驱动
	 */
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *登陆时验证合法性
	 */
	@Override
	public boolean loginValidate(String name,String pwd) {
		
		try(Connection conn = DriverManager.getConnection(url,user,password)) {
			
			String sql = "select * from manager where name=? and pwd=?";
			PreparedStatement ps= conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, pwd);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}

	/*
	 *查找所有数据并返回结果
	 */
	@Override
	public Vector<Vector<Object>> getData() {
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		try(Connection conn = DriverManager.getConnection(url,user,password)) {
			String sql = "select * from employee";
			PreparedStatement ps= conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Vector<Object> rows = new Vector<Object>();
				rows.add(rs.getInt(1));
				rows.add(rs.getObject(2));
				rows.add(rs.getObject(3));
				rows.add(rs.getObject(4));
				rows.add(rs.getObject(5));
				rows.add(rs.getObject(6));
				data.add(rows);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}

	/*
	 * 增加数据
	 */
	@Override
	public boolean addData(Employee emp) {
		String sql = "insert into employee values(?,?,?,?,?,?)";
		
		try(Connection conn = DriverManager.getConnection(url,user,password)) {
			PreparedStatement ps= conn.prepareStatement(sql);
			ps.setInt(1, emp.getId());
			ps.setString(2, emp.getName());
			ps.setString(3, emp.getSex());
			ps.setString(4, emp.getDept());
			ps.setString(5, emp.getRank());
			ps.setString(6, emp.getSalary());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 *更新数据
	 */
	@Override
	public boolean updateData(Employee emp,int tagId) {
		String sql = "update employee set emp_name=?,emp_sex=?,emp_dept=?"
				+ ",emp_rank=?,emp_salary=? where emp_id=?";
		String sqlUpdateAll = "update employee set emp_id=?,emp_name=?,emp_sex=?,emp_dept=?"
				+ ",emp_rank=?,emp_salary=? where emp_id=?";
		try(Connection conn = DriverManager.getConnection(url,user,password)) {
			//////////////////////////////////////////////
			/*
			 * 更新前先判断改id是否存在，如果存在,更新ID失败
			 */
			if(tagId!=0) {
				try {
					String queryId = "select * from employee where emp_id=?";
					PreparedStatement psQI= conn.prepareStatement(queryId);
					psQI.setInt(1, emp.getId());
					if(psQI.executeQuery().next()) {
						return false;
					}else {
						System.out.println(emp.getId());
						PreparedStatement updateAll= conn.prepareStatement(sqlUpdateAll);
						updateAll.setInt(1, emp.getId());
						updateAll.setString(2, emp.getName());
						updateAll.setString(3, emp.getSex());
						updateAll.setString(4, emp.getDept());
						updateAll.setString(5, emp.getRank());
						updateAll.setString(6, emp.getSalary());
						updateAll.setInt(7, tagId);
						System.out.println(updateAll.executeUpdate());
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
			//////////////////////////////////////////////////
			PreparedStatement ps= conn.prepareStatement(sql);
			ps.setString(1, emp.getName());
			ps.setString(2, emp.getSex());
			ps.setString(3, emp.getDept());
			ps.setString(4, emp.getRank());
			ps.setString(5, emp.getSalary());
			ps.setInt(6, emp.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/*
	 * 删除数据
	 */
	@Override
	public boolean deleteData(Integer id) {
		String sql = "delete from employee where emp_id=?";
		try(Connection conn = DriverManager.getConnection(url,user,password)) {
				PreparedStatement ps= conn.prepareStatement(sql);
				ps.setInt(1, id);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	
	/*
	 * 按类别查找
	 */
	public Vector<Vector<Object>> findByType(String type, String value) {
		Vector<Vector<Object>> data = new Vector<>();
	
		String sql = "select * from employee where "+type+"=?";
		try(Connection conn = DriverManager.getConnection(url,user,password)) {
				PreparedStatement ps= conn.prepareStatement(sql);
				if(type.equals("emp_id")) {
					ps.setInt(1, Integer.valueOf(value));
				}else {
					ps.setString(1,value);
				}
				ResultSet rs = ps.executeQuery();
		
				while(rs.next()){
					Vector<Object> rows = new Vector<Object>();
					rows.add(rs.getObject(1));
					rows.add(rs.getObject(2));
					rows.add(rs.getObject(3));
					rows.add(rs.getObject(4));
					rows.add(rs.getObject(5));
					rows.add(rs.getObject(6));
					data.add(rows);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return data;
		}

	/*
	 * 按类型进行统计
	 */
	public Vector<Vector<Object>> getCount(String type) {
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		try(Connection conn = DriverManager.getConnection(url,user,password)) {
			
			String sql = "select "+type+",count(*) from employee group by "+type;
			PreparedStatement ps= conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Vector<Object> rows = new Vector<Object>();
				rows.add(rs.getObject(1));
				rows.add(rs.getObject(2));
				data.add(rows);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}
	
	/*
	 * 保存为文本
	 */
	public boolean saveAsJson(Vector<Vector<Object>> data, File path) {
		//将data数据转化成可以存储的对象
		Employees employees = TransformUtil.vectorToEmployees(data);
		Gson gson = new Gson();
		String jsonData = gson.toJson(employees);
		/*
		 * 将数据写入到自定义的文件
		 */
		try (FileWriter fileWriter = new FileWriter(new File(path.getAbsolutePath()))){
			//System.out.println(path.getAbsolutePath());
			//File file = new File(path.getAbsolutePath());
			//FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(jsonData);
			fileWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean readJson(File path) {
		try {
			/*
			 * 读取文件并把转换成字符串
			 * 利用Gson解析成对象存储在employees中
			 */
			String data = Files.readAllLines(path.toPath()).stream().collect(Collectors.joining());
			Gson gson = new Gson();
			employees = gson.fromJson(data, Employees.class);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}

