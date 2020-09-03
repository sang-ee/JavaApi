package com.Jerseyy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
	public Connection getConnect() throws Exception {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/employee", "root", "");

		} catch (Exception ex) {
			throw ex;
		}
		return con;
	}

	public List<Employee> getEmployeeList() throws Exception {
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
			throw ex;
		}
		return employeeList;

	}

	public Employee getEmployee(int id) throws Exception {
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

			throw ex;
		}
		return employee;
	}

	public void insert(Employee employee) throws Exception {

		try (Connection con = getConnect();) {
			con.setAutoCommit(false);
			try {
				int id = insertEmployee(con, employee);
				insertEmployeePersonal(con, employee, id);
				con.commit();
			} catch (Exception ex) {
				con.rollback();
			}

		} catch (Exception ex) {
			throw ex;
		}

	}

	public int insertEmployee(Connection con, Employee employee)
			throws Exception {
		int id = 0;

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
		} catch (Exception ex) {

			throw ex;
		}
		return id;
	}

	public void insertEmployeePersonal(Connection con, Employee employee, int id)
			throws Exception {

		String sql1 = "insert into employee_personal_details(eid,mobile_no,mail_id,address)"
				+ "values(?,?,?,?)";
		try (PreparedStatement ps2 = con.prepareStatement(sql1);) {
			ps2.setInt(1, id);
			ps2.setString(2, employee.getMobileNo());
			ps2.setString(3, employee.getMailId());
			ps2.setString(4, employee.getAddress());
			ps2.executeUpdate();
		} catch (Exception ex) {
			throw ex;
		}

	}

	public void update(Employee employee) throws Exception {
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
			throw ex;
		}
	}

	public void updateEmployee(Connection con, Employee employee)
			throws Exception {

		String sql = "update employee set name=?,department=? where id=?";
		try (PreparedStatement ps1 = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			ps1.setString(1, employee.getName());
			ps1.setString(2, employee.getDept());
			ps1.setInt(3, employee.getId());
			ps1.executeUpdate();
		} catch (Exception ex) {

			throw ex;

		}

	}

	public void updateEmployeePersonal(Connection con, Employee employee)
			throws Exception {

		String sql1 = "update employee_personal_details set mobile_no=?,mail_id=?,address=? where eid=?";
		try (PreparedStatement ps2 = con.prepareStatement(sql1);) {
			ps2.setString(1, employee.getMobileNo());
			ps2.setString(2, employee.getMailId());
			ps2.setString(3, employee.getAddress());
			ps2.setInt(4, employee.getId());
			ps2.executeUpdate();
		} catch (Exception ex) {

			throw ex;

		}
	}

	public void delete(int id) throws Exception {
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
			throw ex;
		}
	}

	public void deleteEmployee(Connection con, int id) throws Exception {

		String sql = "delete from employee where id=?";

		try (PreparedStatement ps1 = con.prepareStatement(sql);) {
			ps1.setInt(1, id);
			ps1.executeUpdate();
		} catch (Exception ex) {

			throw ex;

		}
	}

	public void deleteEmployeePersonal(Connection con, int id) throws Exception {

		String sql1 = "delete from employee_personal_details where eid=?";
		try (PreparedStatement ps2 = con.prepareStatement(sql1);) {
			ps2.setInt(1, id);
			ps2.executeUpdate();

		} catch (Exception ex) {

			throw ex;

		}

	}
}
