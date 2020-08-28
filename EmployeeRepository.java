package com.Jerseyy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
	
	Connection con=null;
	public Connection getConnect(){
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
	return con;
}

public List<Employee> getEmps(){
	List<Employee> emp=new ArrayList<>();

String sql="select eid,name,dept,mobile_no,mail_id,address from employee inner join personal_emp"
	+ " on employee.eid=personal_emp.id";
	try (Statement st = getConnect().createStatement();
		ResultSet rs=st.executeQuery(sql);){
		while(rs.next()){
			Employee a=new Employee();
			a.setId(rs.getInt("eid"));
			a.setName(rs.getString("name"));
			a.setDept(rs.getString("dept"));
			a.setMobileNo(rs.getString("mobile_no"));
			a.setMailId(rs.getString("mail_id"));
			a.setAddress(rs.getString("address"));
			emp.add(a);
		}
		
		
	}
	catch(Exception e){
		System.out.println(e);
	}
	return emp;
	
}
public Employee getEmployee(int id){
	String sql="select eid,name,dept,mobile_no,mail_id,address from employee inner join personal_emp"
	+ " on employee.eid=personal_emp.id="+id ;
	Employee a=new Employee();
	try(Statement st = getConnect().createStatement();
		ResultSet rs=st.executeQuery(sql);){
		if(rs.next()){
			a.setId(rs.getInt("eid"));
			a.setName(rs.getString("name"));
			a.setDept(rs.getString("dept"));
			a.setMobileNo(rs.getString("mobile_no"));
			a.setMailId(rs.getString("mail_id"));
			a.setAddress(rs.getString("address"));
			
			
		}
		
		
	}
	catch(Exception e){
		System.out.println(e);
	}
return a;
}

public boolean create(Employee a1) {
	String sql="insert into employee(name,dept)values(?,?)";
	String sql1="insert into personal_emp(id,mobile_no,mail_id,address)values(?,?,?,?)";
	int i=0,i1=0,id1=0;
	boolean j=false;
	try (PreparedStatement st= getConnect().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){
		st.setString(1,a1.getName());
		st.setString(2,a1.getDept());
		 i=st.executeUpdate();
		 try(ResultSet rs=st.getGeneratedKeys();){
		if(rs.next()){
			id1=rs.getInt(1);
			System.out.println(id1);
		}
		try( PreparedStatement st1=getConnect().prepareStatement(sql1);){
		 st1.setInt(1, id1);
		 st1.setString(2,a1.getMobileNo());
		 st1.setString(3,a1.getMailId());
		 st1.setString(4,a1.getAddress());
		 i1=st1.executeUpdate();
		j= (i>0 && i1>0);
		
		}
		catch(Exception e){
			System.out.println(e);
		}

		
	}
	catch(Exception e){
		System.out.println(e);
	}
	}
	catch(Exception e){
		System.out.println(e);
	}

	return j;
}
public boolean update(Employee a1) {
	String sql="update employee set name=?,dept=? where eid=?";
	String sql1="update personal_emp set mobile_no=?,mail_id=?,address=? where id=?";
	int i=0,i1=0;
	boolean j=false;
	try (PreparedStatement st= getConnect().prepareStatement(sql);
			 PreparedStatement st1=getConnect().prepareStatement(sql1);){
		st.setString(1,a1.getName());
		st.setString(2,a1.getDept());
		st.setInt(3,a1.getId());
		 i=st.executeUpdate();
		 st1.setString(1,a1.getMobileNo());
		 st1.setString(2,a1.getMailId());
		 st1.setString(3,a1.getAddress());
		 st1.setInt(4,a1.getId());
		 i1=st1.executeUpdate();
		j= (i>0 && i1>0);
		
		
	}
	catch(Exception e){
		System.out.println(e);
	}

	return j;
}



public boolean delete(int id) {
	String sql="delete from employee where eid=?";
	String sql1="delete from personal_emp where id=?";
	int i=0,i1=0;
	boolean j=false;
	try (PreparedStatement st=getConnect().prepareStatement(sql);
			PreparedStatement st1=getConnect().prepareStatement(sql1);){
	    st.setInt(1,id);
		i=st.executeUpdate();
	    st1.setInt(1,id);
		i=st1.executeUpdate();
		
		j=(i>0 && i1>0);
				
	}
	catch(Exception e){
		System.out.println(e);
	}	
	return j;
}

}
