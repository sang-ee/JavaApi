package com.tutorialspoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
	Connection con=null;
	public EmployeeRepository(){
	String url="jdbc:mysql://localhost:3306/employee";
	String user="root";
	String pass="";
	try{
		Class.forName("com.mysql.jdbc.Driver");  
		con=DriverManager.getConnection(url,user,pass);
		
	}
	catch(Exception e){
		System.out.println(e);
	}
}

public List<Employee> getEmps(){
	List<Employee> aliens=new ArrayList<>();
	String sql="select * from employee";
	try{
		Statement st = con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		while(rs.next()){
			Employee a=new Employee();
			a.setId(rs.getInt(1));
			a.setName(rs.getString(2));
			a.setPoints(rs.getInt(3));
			aliens.add(a);
		}
		
		
	}
	catch(Exception e){
		System.out.println(e);
	}
	return aliens;
	
}
public Employee getEmployee(int id){
	String sql="select * from employee where id="+id;
	Employee a=new Employee();
	try{
		Statement st = con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		if(rs.next()){
			//Alien a=new Alien();
			a.setId(rs.getInt(1));
			a.setName(rs.getString(2));
			a.setPoints(rs.getInt(3));
			
		}
		
		
	}
	catch(Exception e){
		System.out.println(e);
	}
return a;
}
public void create(Employee a1) {
	String sql="insert into employee values(?,?,?)";
	try{
		PreparedStatement st=con.prepareStatement(sql);
		st.setInt(1,a1.getId());
		st.setString(2,a1.getName());
		st.setInt(3,a1.getPoints());
		st.executeUpdate();
		
		
	}
	catch(Exception e){
		System.out.println(e);
	}

	
}
public void update(Employee a1) {
	String sql="update employee set name=?,points=? where id=?";
	try{
		PreparedStatement st=con.prepareStatement(sql);
	
		st.setString(1,a1.getName());
		st.setInt(2,a1.getPoints());
		//st.executeUpdate();
		
		st.setInt(3,a1.getId());
		st.executeUpdate();
	}
	catch(Exception e){
		System.out.println(e);
	}

	
}

public void delete(int id) {
	String sql="delete from employee where id=?";
	try{
		PreparedStatement st=con.prepareStatement(sql);
	
				st.setInt(1,id);
		st.executeUpdate();
	}
	catch(Exception e){
		System.out.println(e);
	}	
	
}

}
