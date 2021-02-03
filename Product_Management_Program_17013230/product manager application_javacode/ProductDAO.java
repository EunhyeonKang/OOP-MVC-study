package productproject;

import java.sql.*;
import java.util.*;

public class ProductDAO {
	String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://localhost/javadb?serverTimezone=UTC";
	Connection conn;
	
	PreparedStatement pstmt;
	ResultSet rs;
	
	Vector<String> items = null;
	String sql;
	
	// �޺��ڽ��� ��ǰ���� ��ȣ ����� ���� ���� ����
	public Vector<String> getItems() {
		return items;
	}
	
	// ��ü ��ǰ ����� ������ ���� �޼���
	public ArrayList<Product> getAll() {
		connectDB();
		sql = "select * from product";
		
		//��ü �˻� �����͸� �����ϱ� ���� ArrayList
		ArrayList<Product> datas = new ArrayList<Product>();
		
		// �����ڵ� �޺��ڽ� �����͸� ���� ���� �ʱ�ȭ
		items = new Vector<String>();
		items.add("��ü");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
		
			// �˻��� �����ͼ� ��ŭ ������ ���� Product ��ü�� ����� �̸� �ٽ� ArrayList �� �߰���.
			while(rs.next()) {
				Product p = new Product();
				p.setPrcode(rs.getInt("prcode"));
				p.setPrname(rs.getString("prname"));
				p.setPrice(rs.getInt("price"));
				p.setManufacture(rs.getString("manufacture"));
				datas.add(p);
				items.add(String.valueOf(rs.getInt("prcode")));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			closeDB();
		}
		return datas;
	}
	
	// ������ ��ǰ �ڵ忡 �ش��ϴ� ��ǰ ������ ������ ���� �޼���
	public Product getProduct(int prcode) {
		connectDB();
		sql = "select * from product where prcode=?";
		Product p = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prcode);
			rs = pstmt.executeQuery();
			rs.next();
			p = new Product();
			p.setPrcode(rs.getInt("prcode"));
			p.setPrname(rs.getString("prname"));
			p.setPrice(rs.getInt("price"));
			p.setManufacture(rs.getString("manufacture"));
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			closeDB();
		}
		return p;
	}
	
	// ���ο� ��ǰ�� ����ϴ� �޼���
	public boolean newProduct(Product product) {
		connectDB();
		
		// prcode �� �ڵ����� �÷��̹Ƿ� ���� �Է����� �ʴ´�.
		sql = "insert into product"
		+ "(prcode,prname,price,manufacture) values(prcode,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getPrname());
			pstmt.setInt(2, product.getPrice());
			pstmt.setString(3, product.getManufacture());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			closeDB();
		}
		return true;
	}
	
	// ������ ��ǰ�� �����ϴ� �޼���
	public boolean delProduct(int prcode) {
		connectDB();
		sql = "delete from product where prcode=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prcode);
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			closeDB();
		}
		return true;
	}
	
	// ������ ������ ��ǰ������ ������Ʈ �ϴ� �޼���
	public boolean updateProduct(Product product) {
		connectDB();
		sql = "update product set prname=?, price=?, manufacture=? where prcode=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getPrname());
			pstmt.setInt(2, product.getPrice());
			pstmt.setString(3, product.getManufacture());
			pstmt.setInt(4, product.getPrcode());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			closeDB();
		}
		return true;
	}
	
	// DB���� �޼���
	public void connectDB() {
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(jdbcUrl,"root", "0000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// DB ���� ���� �޼���
	public void closeDB() {
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
