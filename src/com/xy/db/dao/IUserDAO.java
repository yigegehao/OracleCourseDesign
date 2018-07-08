package com.xy.db.dao;

import java.io.File;
import java.sql.Date;
import java.util.Vector;

import com.xy.db.entity.Employee;

public interface IUserDAO {
	
	boolean addData(Employee emp);
	boolean deleteData(Integer id);
	public boolean readJson(File path);
	boolean updateData(Employee emp, int tag);
	boolean loginValidate(String name,String pwd);
	boolean saveAsJson(Vector<Vector<Object>> data, File path);
	Vector<Vector<Object>> getData();
	Vector<Vector<Object>> getCount(String type);
	Vector<Vector<Object>> findByType(String type, String value);
}
