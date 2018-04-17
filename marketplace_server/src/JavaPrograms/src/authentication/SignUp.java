package authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUp {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/MarketPlace?user=root&password=root&useSSL=false");
			ps = conn.prepareStatement("Select userID from User where" +
					" username=?");
			ps.setString(1, args[0]);
			rs = ps.executeQuery();
			if (rs.next())
			{
				int userID = rs.getInt("userID");
				if (userID > 0)
				{
					System.out.println("-1");
				}
				else
				{
					ps = conn.prepareStatement("Insert into User (username, password) values (?, ?)");
					ps.setString(1, args[0]);
					ps.setString(2, args[1]);
					ps.executeUpdate();
					ps = conn.prepareStatement("Select userID from User where" +
							" username=? and password=?");
					ps.setString(1, args[0]);
					ps.setString(2, args[1]);
					rs = ps.executeQuery();
					rs.next();
					userID = rs.getInt("userID");
					if (userID > 0)
					{
						System.out.println(userID);
					}
					else
					{
						System.err.println("register error");
					}
				}
			}
			else
			{
				ps = conn.prepareStatement("Insert into User (username, password) values (?, ?)");
				ps.setString(1, args[0]);
				ps.setString(2, args[1]);
				ps.executeUpdate();
				ps = conn.prepareStatement("Select userID from User where" +
						" username=? and password=?");
				ps.setString(1, args[0]);
				ps.setString(2, args[1]);
				rs = ps.executeQuery();
				rs.next();
				int userID = rs.getInt("userID");
				if (userID > 0)
				{
					System.out.println(userID);
				}
				else
				{
					System.err.println("register error");
				}			
			}
		}
		catch (SQLException e)
		{
			System.err.println("SQL Error " + e.getMessage());
		}
		catch (ClassNotFoundException e)
		{
			System.err.println("Class Not found " + e.getMessage());
		}
		finally {
			try 
			{
				 if (rs != null)
					 rs.close();
				 if (ps != null)
					 ps.close();
				 if (conn != null)
					 conn.close();
				
			}
			catch (SQLException e)
			{
				System.err.println("Closing Error " + e.getMessage());
			}
		}

	}
}
