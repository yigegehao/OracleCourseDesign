package com.xy.db.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;

import com.xy.db.entity.Employee;
import com.xy.db.entity.Employees;

public class TransformUtil {
	/*
	 * 将Vector<Vector<Object>>对象转换成Employees对象
	 */
	public static Employees vectorToEmployees(Vector<Vector<Object>> v) {
		Employees employees = new Employees();
		List<Employee> list = new ArrayList<Employee>();
		for(Vector<Object> vemp:v) {
				Employee employee = new Employee();
				employee.setId((int)vemp.get(0));
				employee.setName((String)vemp.get(1));
				employee.setSex((String) vemp.get(2));
				employee.setDept((String) vemp.get(3));
				employee.setRank((String) vemp.get(4));
				employee.setSalary((String) vemp.get(5));
				list.add(employee);
		}
		employees.setEmployees(list);
		return employees;
	}
	
	/*
	 * 将Employees对象转换成Vector<Vector<Object>>对象
	 */
	public static Vector<Vector<Object>> employeesToVector(Employees employees) {
		Vector<Vector<Object>> vector = new Vector<>();
		for(Employee emps:employees.getEmployees()) {
			Vector<Object> v = new Vector<>();
			v.add(emps.getId());
			v.add(emps.getName());
			v.add(emps.getSex());
			v.add(emps.getDept());
			v.add(emps.getRank());
			v.add(emps.getSalary());
			vector.add(v);
		}
		return vector;
	}
	
}
