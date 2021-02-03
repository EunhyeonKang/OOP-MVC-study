package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {
	
	Scanner scanner = new Scanner(System.in);
	String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	//String jdbcUrl = "jdbc:mysql://localhost/javadb";
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public Main() {
		System.out.println("##�̺�Ʈ ����� ���� �̸��� �̸����� �Է��ϼ���");
		System.out.println("�̸�: ");
		String uname = scanner.next();
		System.out.println("�̸���: ");
		String uemail = scanner.next();
		DBConnection();
		registUser(uname,uemail);
		printList();
		closeDB();
		
	}
	public void DBConnection() {
		try {
			Class.forName(jdbcDriver);
			con =  DriverManager.getConnection("jdbc:mysql://localhost/javadb?serverTimezone=UTC","root","0000");	
		}catch(Exception e)
		{
			e.printStackTrace();
		//	System.out.println("�����ͺ��̽� ���� ���� : " +e.getMessage());
		}
	}
	public void closeDB() {
		try {
			pstmt.close();
			rs.close();
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	public void registUser(String uname, String uemail) {
		String sql = "insert into event values(?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uname);
			pstmt.setString(2, uemail);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void printList() {
		System.out.println("#����� ���");
		String sql = "select * from event";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("uname") + ","+rs.getString(2));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Main run = new Main();
	}
}