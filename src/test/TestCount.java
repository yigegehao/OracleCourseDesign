package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.gson.Gson;
import com.xy.db.dao.UserDAO;
import com.xy.db.entity.Employee;
import com.xy.db.entity.Employees;
import com.xy.db.util.TransformUtil;

class TestCount {

	@Test
	void test() {
		//String jsonDat = "{'name':'john','age'=20}";
		Person person = new Person();
		
		person.setName("jack");
		person.setAge(11);
		persons persons = new persons();
		List<Person> list = new ArrayList<>();
		list.add(person);
		persons.setPerson(list);
		Gson gson = new Gson();
		
		System.out.println(gson.toJson(persons));
		/*UserDAO userDAO = new UserDAO();
		boolean isOk = userDAO.saveAsJson(userDAO.getData());
		if(isOk) {
			System.out.println("ok");
		}else {
			System.out.println("not ok");
		}*/
	}
	
	@Test
	void test2() {
		Path path = Paths.get("employ.txt");
		try {
			StringBuilder stringBuilder = new StringBuilder();
			
			String data = Files.readAllLines(path).stream().collect(Collectors.joining());
			System.out.println(data);
			//while()
			Gson gson = new Gson();
			Employees employees = gson.fromJson(data, Employees.class);
			System.out.println(employees.getEmployees().get(0).getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3() {
		UserDAO userDAO = new UserDAO();
		Employees employees = TransformUtil.vectorToEmployees(userDAO.getData());
		Gson gson = new Gson();
		String jsonData = gson.toJson(employees);
		System.out.println(jsonData);
			try {
				File file = new File("employ.txt");
				FileWriter fileWriter=new FileWriter(file);
				fileWriter.write(jsonData);
				fileWriter.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
		

}
