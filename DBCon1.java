package test;
import java.sql.*;
import java.util.*;


public class DBCon1 
{
	final String userName,pass;
	public DBCon1(String userName,String pass)
	{
		this.userName = userName;
		this.pass = pass;
	}

	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		try(s)
		{
			System.out.println("Enter the User Name & Password..");
			DBCon1 obj = new DBCon1(s.nextLine(),s.nextLine());
			
			// loading drivers from jar file..
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//connecting to DB
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",obj.userName,obj.pass);
			try(con)
			{
				System.out.println("Connection is established..");
				
				//creating the Statement object
				Statement stm = con.createStatement();
				
				
				//running the select query in executeQuery()
				//creating the resultSet object
				String query = "Select * from tab"; // String Literal
				ResultSet rs = stm.executeQuery(query);
				
				//showing the all table in the DB..
				System.out.println("tables in the DB..");
				while(rs.next())
				{
					System.out.println("\t"+rs.getString(1));
				}
				
				//showing the one table..
				System.out.println("Enter the table name..");
				String table = s.nextLine();
				query = "select * from "+table;
				rs = stm.executeQuery(query);
				
				//getting the column-count and columnName from metadata
				ResultSetMetaData rsm = rs.getMetaData();
				int count = rsm.getColumnCount();
				for(int i=1;i<=count;i++) {
					System.out.print(rsm.getColumnName(i)+"\t\t");;
				}
				System.out.println("");
				while(rs.next())
				{
					for(int i=1;i<=count;i++) {
						String spc = (6 < (rs.getString(i) != null ? rs.getString(i).length() : 4)) ? " \t" : "\t\t";
						System.out.print(rs.getString(i) + spc);
					}
					System.out.println("");
				}
			}
			catch(Exception e)
			{				
				System.out.println("Error from DB Connection..\n"+e.toString());
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Error from Input..\n"+e.toString());
		}
	}

}
