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
		if (con == null)
			throw new NullPointerException(
					"Connection object reference is null");
		return con;
	}

	public List<Employee> getEmployeeList() {
		List<Employee> employeeList = new ArrayList<>();

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
				employeeList.add(employee);
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
		return employeeList;

	}

	public Employee getEmployee(int id) {
		Employee employee = new Employee();
		String sql = "select id,name,department,mobile_no,mail_id,address from employee inner join employee_personal_details"
				+ " on employee.id=employee_personal_details.eid where id=?";
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
			try {
				id = insertEmployee(con, employee);
				insertEmployeePersonal(con, employee, id);
				con.commit();
			} catch (Exception ex) {
				con.rollback();
			}

		} catch (Exception ex) {

			System.out.println(ex);
		}

	}

	public int insertEmployee(Connection con, Employee employee)
			throws Exception {
		int id = 0, i = 0;

		String sql = "insert into employee(name,department)values(?,?)";
		try (PreparedStatement ps1 = con.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);) {
			ps1.setString(1, employee.getName());
			ps1.setString(2, employee.getDept());
			i = ps1.executeUpdate();
			try (ResultSet rs = ps1.getGeneratedKeys();) {
				if (rs.next()) {
					id = rs.getInt(1);
					System.out.println(id);
				}
			}
		} catch (Exception ex) {
			if (i == 0)
				throw new Exception("Method insertEmployee() failed");
			System.out.println(ex);
		}
		return id;
	}

	public void insertEmployeePersonal(Connection con, Employee employee, int id)
			throws Exception {
		int i = 0;
		String sql1 = "insert into employee_personal_details(eid,mobile_no,mail_id,address)"
				+ "values(?,?,?,?)";
		try (PreparedStatement ps2 = con.prepareStatement(sql1);) {
			ps2.setInt(1, id);
			ps2.setString(2, employee.getMobileNo());
			ps2.setString(3, employee.getMailId());
			ps2.setString(4, employee.getAddress());
			i = ps2.executeUpdate();
		} catch (Exception ex) {
			if (i == 0)
				throw new Exception("Method insertEmployeePersonal() failed");
			System.out.println(ex);
		}

	}

	public void update(Employee employee) {
		try (Connection con = getConnect();) {
			con.setAutoCommit(false);

			try {
				updateEmployee(con, employee);
				updateEmployeePersonal(con, employee);
				con.commit();
			} catch (Exception ex) {
				con.rollback();
				System.out.println(ex);
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void updateEmployee(Connection con, Employee employee)
			throws Exception {
		int i = 0;
		String sql = "update employee set name=?,department=? where id=?";
		try (PreparedStatement ps1 = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			ps1.setString(1, employee.getName());
			ps1.setString(2, employee.getDept());
			ps1.setInt(3, employee.getId());
			i = ps1.executeUpdate();
		} catch (Exception ex) {
			if (i == 0)
				throw new Exception("Method updateEmployee() failed");
			System.out.println(ex);
		}

	}

	public void updateEmployeePersonal(Connection con, Employee employee)
			throws Exception {
		int i = 0;
		String sql1 = "update employee_personal_details set mobile_no=?,mail_id=?,address=? where eid=?";
		try (PreparedStatement ps2 = con.prepareStatement(sql1);) {
			ps2.setString(1, employee.getMobileNo());
			ps2.setString(2, employee.getMailId());
			ps2.setString(3, employee.getAddress());
			ps2.setInt(4, employee.getId());
			i = ps2.executeUpdate();
		} catch (Exception ex) {
			if (i == 0)
				throw new Exception("Method updateEmployeePersonal() failed");
			System.out.println(ex);
		}
	}

	public void delete(int id) {
		try (Connection con = getConnect();) {
			con.setAutoCommit(false);

			try {
				deleteEmployeePersonal(con, id);
				deleteEmployee(con, id);
				con.commit();
			} catch (Exception ex) {
				con.rollback();
				System.out.println(ex);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void deleteEmployee(Connection con, int id) throws Exception {
		int i = 0;
		String sql = "delete from employee where id=?";

		try (PreparedStatement ps1 = con.prepareStatement(sql);) {
			ps1.setInt(1, id);
			i = ps1.executeUpdate();
		} catch (Exception ex) {
			if (i == 0)
				throw new Exception("Method deleteEmployee() failed");
			System.out.println(ex);
		}
	}

	public void deleteEmployeePersonal(Connection con, int id) throws Exception {
		int i = 0;

		String sql1 = "delete from employee_personal_details where eid=?";
		try (PreparedStatement ps2 = con.prepareStatement(sql1);) {
			ps2.setInt(1, id);
			i = ps2.executeUpdate();

		} catch (Exception ex) {
			if (i == 0)
				throw new Exception("Method deleteEmployeePersonal() failed");
			System.out.println(ex);
		}

	}
}
