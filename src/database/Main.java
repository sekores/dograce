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
		zwinger("Cyndie's Magic");

	}
	
	public static void insertTable() throws Exception{
		try{
			Connection con = getConnection();
			
			String csvFile = "/Users/Conrad1/git/dograce/greyhounddata.csv";
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ";";
			
			try {

				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
				        // use comma as separator
					String[] greyhounddata = line.split(cvsSplitBy);
					System.out.println(" "+greyhounddata[0]+" "+greyhounddata[1]+" "+greyhounddata[2]+" "+greyhounddata[3]+" "+greyhounddata[4]+" "+greyhounddata[5]+" "+greyhounddata[6]+" "+greyhounddata[7]+" "+greyhounddata[8]+" "+greyhounddata[9]);
					PreparedStatement insert = con.prepareStatement("INSERT INTO Hund(Land,Jahr,Jahresrang) VALUES ('"+greyhounddata[0]+"',"+greyhounddata[1]+","+greyhounddata[2]+");");
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
	
//	public static String geschlecht(String string) {
//		// TODO Auto-generated method stub
//		String result="";
//		if(string.equals("m") || string.equals("w") ){
//			result=string;
//		}else{
//			result="";
//		}
//		return result;
//	}
	public static void zwinger(String string){
		String result="";
		for (int i = 0; i < string.length(); i++) {
			String temp="";
			temp=temp+string.charAt(i)+string.charAt(i+1)+string.charAt(i+2);
			if(temp=="'s "){
				while(string.charAt(i)!='['){
					result=result+string.charAt(i);
					i++;
				}
			}
			
		}
		System.out.println(result);
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

