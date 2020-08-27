package com.Jerseyy;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee {
	private int id;
private String name;
private String dept;
private String mobile_no;
private String mail_id;
private String address;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getDept() {
	return dept;
}
public void setDept(String dept) {
	this.dept = dept;
}
public String getMobileNo() {
	return mobile_no;
}
public void setMobileNo(String mobile_no) {
	this.mobile_no = mobile_no;
}
public String getMailId() {
	return mail_id;
}
public void setMailId(String mail_id) {
	this.mail_id = mail_id;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}


}
