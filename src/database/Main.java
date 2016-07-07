package database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		createTable();
		insertTable();

	}
	
	public static void insertTable() throws Exception{
		try{
			Connection con = getConnection();
			
			String csvFile = "/Users/Serkan/workspace/HelloDatabaseWorldJava-master/greyhounddata.csv";
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ";";
			
			try {

				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
				        // use comma as separator
					String[] greyhounddata = line.split(cvsSplitBy);
					System.out.println(" 0	"+greyhounddata[0]+" 1	"+greyhounddata[1]+" 2	"+greyhounddata[2]+" 3	"+greyhounddata[3]+" 4	"+greyhounddata[4]+" 5	"+greyhounddata[5]+" 6		"+greyhounddata[6]+" 7	"+greyhounddata[7]+" 8	"+greyhounddata[8]+" 9	"+greyhounddata[9]);
					PreparedStatement insert = con.prepareStatement("INSERT INTO Hund(Land,Jahr,Jahresrang,Geschlecht) VALUES ('"+greyhounddata[0]+"',"+greyhounddata[1]+","+greyhounddata[2]+","+geschlecht(greyhounddata[4])+");");
					insert.executeUpdate();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static String geschlecht(String string) {
		// TODO Auto-generated method stub
		String result="";
		if(string.equals('m') || string.equals('w') ){
			result=string;
		}else{
			result="";
		}
		return result;
	}

	public static String aufenthaltsland(String string) {
		// TODO Auto-generated method stub
		String result = "";
		for (int i = 0; i < string.length(); i++) {
			if(string.charAt(i)=='/'){
				while(string.charAt(i+1)!=' '){
					result=result+string.charAt(i+1);
					i++;
				}
			}
			
		}
		return result; 
	}

	public static String geburtsland(String string) {
		// TODO Auto-generated method stub
		String result = "";
		for (int i = 0; i < string.length(); i++) {
			if(string.charAt(i)=='['){
				while(string.charAt(i+1)!='/'){
					result=result+string.charAt(i+1);
					i++;
				}
			}
			
		}
		return result; 
	}
	
	
	public static void createTable() throws Exception{
		try{
			Connection con =getConnection();
			PreparedStatement create_Hund = con.prepareStatement("CREATE TABLE IF NOT EXISTS Hund(Hund_ID SERIAL PRIMARY KEY, Land VARCHAR(252), Jahr INT, Jahresrang INT, Geschlecht VARCHAR(1));");
			PreparedStatement create_Rennen = con.prepareStatement("CREATE TABLE IF NOT EXISTS Rennen(Hund_ID SERIAL references Hund(Hund_ID),durch_Rennpunkte INT);");
			PreparedStatement create_Zwinger = con.prepareStatement("CREATE TABLE IF NOT EXISTS Zwinger(name VARCHAR(252) PRIMARY KEY);");
			create_Hund.executeUpdate();
			create_Rennen.executeUpdate();
			create_Zwinger.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}
		finally{
			System.out.println("The CREATE Statements complete");
		}
	}
	
	
	public static Connection getConnection() throws Exception{
		try{
			String driver = "org.postgresql.Driver";
			String dbUser   = "postgres";
			String password = "postgres";
			String url = "jdbc:postgresql://localhost:5432/dograce";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url, dbUser , password);
			System.out.println("The database "+dbUser+"@"+url+" has been connected successfully");
			return conn;
		
		}catch(Exception e){
			System.out.println(e);
			
		}
		
		return null;
	}


}

