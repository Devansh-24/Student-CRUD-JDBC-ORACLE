import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class JdbcConnection {


	/*
	 path for database Connection  Ojdbc.jar file:-
	 C:\oraclexe\app\oracle\product\10.2.0\server\jdbc\lib\ojdbc14.jar
	 */
	public static void main(String[] args) {

		// STEP :1-Load Driver::
		// STEP :2-Connection To DataBase::
		// STEP :3-How to save data in database...
		//-----------------------------------------------------------------------------------//
		// STEP :1-Load Driver::
		// Add jar
		try {
			int ch;

			Class.forName("oraclejdbc.OracleDrive");

			// STEP :2-Connection To DataBase::

			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","System","123456789");
			System.out.println("Connection "+con);
			// THIN IS DRIVER

			Scanner sc=new Scanner(System.in);
			//		     int ch;
			int rollno = 0;
			String name;
			float perct;
			PreparedStatement pstate=null;
			Statement st=null;
			ResultSet rs=null;  // result set store data in table format. 
			do
			{
				System.out.println("\n0-CREATE NEW TABLE\n1-INSERT\n2-DELETE\n3-UPDATE\n4-Search\n5-Display All");
				System.out.println("Enter Your Choice");
				ch=sc.nextInt();
				switch(ch)
				{
				case 0:
					System.out.println("ENTER THE NAME TO CREATE TABLE");
					name=sc.next();
					 st=con.createStatement();	 // STATIC QUERY			
					String sql= "Create table "+name
							+"( rollno INTEGER not NULL,"+
							"name varchar2(50),"+
							"perct float)";
					st.executeUpdate(sql);
					System.out.println("Table Created successfully");
					break;
				case 1:
					// INSERT RECORD IN DATABASE..
					System.out.println("ENTER THE ROLL No. OF STUDENT");
					rollno=sc.nextInt();
					System.out.println("ENTER THE NAME OF STUDENT");
					name=sc.next();
					System.out.println("ENTER THE PERCENTAGE  OF STUDENT");
					perct=sc.nextFloat();

					pstate=con.prepareStatement("insert into student values(?,?,?)");  // PreparedStatement is interface
					pstate.setInt(1, rollno);
					pstate.setString(2, name);
					pstate.setFloat(3, perct);

					int i=pstate.executeUpdate();  //insert record or upadate or delete // DML COMMMAND
					if(i>0)
					{
						System.out.println("Record Saved");
					}
					else
					{
						System.out.println("Not Save");
					}
					break;
				case 2:
					// DELETE FROM DATABASE......
					System.out.println("ENTER THE ROLL No. OF STUDENT");
					rollno=sc.nextInt();

					pstate=con.prepareStatement("delete from student where rollno=?");  // PreparedStatement is interface
					pstate.setInt(1, rollno);

					i=pstate.executeUpdate();  //insert record or upadate or delete // DML COMMMAND
					if(i>0)
					{
						System.out.println("Record Deleted");
					}
					else
					{
						System.out.println("Not Save");
					}

					break;
				case 3:
					// UPDATE RECORDE IN DATABASE

					System.out.println("ENTER THE ROLL No. OF STUDENT");
					rollno=sc.nextInt();
					System.out.println("ENTER THE PERCENTAGE  OF STUDENT");
					perct=sc.nextFloat();



					pstate=con.prepareStatement("Update student set perct=? where rollno=?");  // PreparedStatement is interface
					pstate.setInt(2, rollno);
					pstate.setFloat(1, perct);

					i=pstate.executeUpdate();  //insert record or upadate or delete // DML COMMMAND
					if(i>0)
					{
						System.out.println("Record Updated");
					}
					else
					{
						System.out.println("Not Save");
					}
					break;
				case 4:
					System.out.println("ENTER THE ROLL NO OF STUDENT");
					ch=sc.nextInt();
					pstate=con.prepareStatement("Select * from student where rollno=?");  // PreparedStatement is interface
					pstate.setInt(1, ch);

				     rs=pstate.executeQuery();  //insert record or upadate or delete // DQL COMMMAND
//					i=pstate.executeUpdate();
					if(rs.next())
					{
						System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getFloat(3));
					}
					else
					{
						System.out.println("Record not found");
					}

					break;
					
				case 5:
					 String str="select * from student";
					 st=con.createStatement();
					 rs=st.executeQuery(str);
					 
					 ResultSetMetaData rsmd=rs.getMetaData();
					 for(i=1;i<=rsmd.getColumnCount();i++)
					 {
						 System.out.print(rsmd.getColumnName(i)+"\t");
					 }
					System.out.println("\n----------------------------------------------------------"); 
					 while(rs.next())
						 
					 {   
							System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getFloat(3));
							System.out.println("\n-----------------------------------------------------");
					 }
					break;
				}
				
				System.out.println("YOU WANT TO CONTINUE PRESS 1....");
				ch=sc.nextInt();
				System.out.println("--------------THANK YOU----------------");
			}while(ch==1);
			con.close();
		}
		
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	}
}
