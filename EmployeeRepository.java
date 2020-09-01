package com.Jerseyy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
	public Connection getConnect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/employee", "root", "");

		} catch (Exception ex) {
			System.out.println(ex);
		}
		return con;
	}

	public List<Employee> getEmployeeList() {
		List<Employee> employee_list = new ArrayList<>();

		String sql = "select id,name,department,mobile_no,mail_id,address from employee inner join employee_personal_details"
				+ " on employee.id=employee_personal_details.eid";
		try (Connection con = getConnect();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);) {
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getInt("id"));
				employee.setName(rs.getString("name"));
				employee.setDept(rs.getString("department"));
				employee.setMobileNo(rs.getString("mobile_no"));
				employee.setMailId(rs.getString("mail_id"));
				employee.setAddress(rs.getString("address"));
				employee_list.add(employee);
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
		return employee_list;

	}

	public Employee getEmployee(int id) {
		Employee employee = new Employee();
		String sql = "select id,name,department,mobile_no,mail_id,address from employee inner join employee_personal_details"
				+ " on employee.id=employee_personal_details.eid where employee.id=?";
		try (Connection con = getConnect();
				PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					employee.setId(rs.getInt("id"));
					employee.setName(rs.getString("name"));

					employee.setDept(rs.getString("department"));
					employee.setMobileNo(rs.getString("mobile_no"));
					employee.setMailId(rs.getString("mail_id"));
					employee.setAddress(rs.getString("address"));

				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return employee;
	}

	public void insert(Employee employee) {
		int id = 0;

		try (Connection con = getConnect();) {
			con.setAutoCommit(false);
			String sql = "insert into employee(name,department)values(?,?)";
			try (PreparedStatement ps1 = con.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);) {
				ps1.setString(1, employee.getName());
				ps1.setString(2, employee.getDept());
				ps1.executeUpdate();
				try (ResultSet rs = ps1.getGeneratedKeys();) {
					if (rs.next()) {
						id = rs.getInt(1);
						System.out.println(id);
					}
				}
			}
			String sql1 = "insert into employee_personal_details(eid,mobile_no,mail_id,address)values(?,?,?,?)";
			try (PreparedStatement ps2 = con.prepareStatement(sql1);) {
				ps2.setInt(1, id);
				ps2.setString(2, employee.getMobileNo());
				ps2.setString(3, employee.getMailId());
				ps2.setString(4, employee.getAddress());
				ps2.executeUpdate();

			}
			try {
				con.commit();
			} catch (Exception ex) {
				con.rollback();
				System.out.println(ex);
			}

		} catch (Exception ex) {

			System.out.println(ex);
		}

	}

	public void update(Employee employee) {
		String sql = "update employee set name=?,department=? where id=?";
		try (Connection con = getConnect();
				PreparedStatement ps1 = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			ps1.setString(1, employee.getName());
			ps1.setString(2, employee.getDept());
			ps1.setInt(3, employee.getId());
			ps1.executeUpdate();
			String sql1 = "update employee_personal_details set mobile_no=?,mail_id=?,address=? where eid=?";
			try (PreparedStatement ps2 = con.prepareStatement(sql1);) {
				ps2.setString(1, employee.getMobileNo());
				ps2.setString(2, employee.getMailId());
				ps2.setString(3, employee.getAddress());
				ps2.setInt(4, employee.getId());
				ps2.executeUpdate();
			}
			try {
				con.commit();
			} catch (Exception ex) {
				con.rollback();
				System.out.println(ex);
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void delete(int id) {
		String sql = "delete from employee_personal_details where eid=?";
		try (Connection con = getConnect();
				PreparedStatement ps1 = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			ps1.setInt(1, id);
			ps1.executeUpdate();
			String sql1 = "delete from employee where id=?";

			try (PreparedStatement ps2 = con.prepareStatement(sql1);) {
				ps2.setInt(1, id);
				ps2.executeUpdate();

			}
			try {
				con.commit();
			} catch (Exception ex) {
				con.rollback();
				System.out.println(ex);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
