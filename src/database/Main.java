package database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Exception {
//		createTable();
//		insertTable();
		anfragen();
		
	}
	
	public static void insertTable() throws Exception{
		try{
			Connection con = getConnection();
			
			String csvFile = "/Users/Serkan/git/dograce/greyhounddata.csv";
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ";";
			
			try {

				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
				        // use comma as separator
					String[] greyhounddata = line.split(cvsSplitBy);
//					System.out.println(" "+greyhounddata[0]+" "+greyhounddata[1]+" "+greyhounddata[2]+" "+greyhounddata[3]+" "
//							+ ""+greyhounddata[4]+" "+greyhounddata[5]+" "+greyhounddata[6]+" "+greyhounddata[7]+" "+greyhounddata[8]+" "
//									+ ""+greyhounddata[9]);
					
					PreparedStatement insert_Zwinger = con.prepareStatement("INSERT INTO Zwinger (Name) SELECT '"+zwinger(greyhounddata[3])+"'"
							+ " WHERE NOT EXISTS ( SELECT Name FROM Zwinger WHERE Name = '"+zwinger(greyhounddata[3])+"');");
					insert_Zwinger.executeUpdate();
					
					PreparedStatement insert_Hund = con.prepareStatement("INSERT INTO Hund(Mama,Geburtsland,Vater,Name"
									+ ",Aufenthaltsland,Geburtsjahr,Geschlecht,z_name) "
							+ "SELECT '"+Babo(greyhounddata[6])+"','"+geburtsland(greyhounddata[3])+"','"+Babo(greyhounddata[5])+"','"
									+ ""+name(greyhounddata[3])+"','"+aufenthaltsland(greyhounddata[3])+"',"+geburtsjahr(greyhounddata[3])+""
									+ ",'"+geschlecht(greyhounddata[4])+"','"+zwinger(greyhounddata[3])+"'"
							+ "WHERE NOT EXISTS (SELECT Mama,Geburtsland,Vater,Name,Aufenthaltsland,Geburtsjahr,Geschlecht "
							+ "FROM Hund "
							+ "WHERE Geschlecht = '"+geschlecht(greyhounddata[4])+"' AND Vater='"+Babo(greyhounddata[5])+"' "
									+ "AND Mama='"+Babo(greyhounddata[6])+"' AND Geburtsland='"+geburtsland(greyhounddata[3])+"' "
									+ "AND Aufenthaltsland='"+aufenthaltsland(greyhounddata[3])+"' "
									+ "AND Geburtsjahr="+geburtsjahr(greyhounddata[3])+" AND Name='"+name(greyhounddata[3])+"');");
					insert_Hund.executeUpdate();
					
					PreparedStatement get_h_id = con.prepareStatement("SELECT ID FROM Hund "
							+ "WHERE Geschlecht = '"+geschlecht(greyhounddata[4])+"' AND Vater='"+Babo(greyhounddata[5])+"' "
							+ "AND Mama='"+Babo(greyhounddata[6])+"' AND Geburtsland='"+geburtsland(greyhounddata[3])+"' "
							+ "AND Aufenthaltsland='"+aufenthaltsland(greyhounddata[3])+"' "
							+ "AND Geburtsjahr="+geburtsjahr(greyhounddata[3])+" AND Name='"+name(greyhounddata[3])+"';");
					
					ResultSet result = get_h_id.executeQuery();
					int h_id = 0;
					while(result.next()){
						h_id=result.getInt("ID");
					}
					PreparedStatement insert_Ergebnis = con.prepareStatement("INSERT INTO Ergebnis(durchs_Renndistanz,Rang,kumulierte_Punktzahl,"
							+ "Land,Jahr,Anzahl_der_Rennen,h_id) "
							+ "VALUES ("+durchs_Renndistanz(greyhounddata[9])+","+greyhounddata[2]+","+(int)Double.parseDouble(greyhounddata[8])+""
									+ ",'"+greyhounddata[0]+"',"+greyhounddata[1]+","+greyhounddata[7]+","+h_id+");");
					
					insert_Ergebnis.executeUpdate();
					
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

	public static ArrayList<String> anfragen() throws Exception{
		try{
		Connection con = getConnection();
		PreparedStatement a1 = con.prepareStatement("SELECT h.ID, h.Name "
				+ "FROM Hund h, Ergebnis e "
				+ "GROUP BY h.ID "
				+ "ORDER BY (sum(e.kumulierte_Punktzahl)/sum(e.Anzahl_der_Rennen)) "
				+ "DESC LIMIT 20;");
		ResultSet result_a1 =a1.executeQuery();
		ArrayList<String> array = new ArrayList<String>();
		while(result_a1.next()){
			System.out.print(result_a1.getString("ID"));
			System.out.print(":  ");
			System.out.println(result_a1.getString("Name"));
			
			array.add(result_a1.getString("ID"));
			array.add(result_a1.getString("Name"));
		}
		System.out.println("All records have been selected!");
		return array;
		}catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	public static String name(String string) {
		String result = "";
		String [] blub;
		String [] leerzeichen;
		
		if(string.contains("'s ")){
			blub=string.split("'s ");
			leerzeichen=blub[1].split(" ");
			for (int i = 0; i < leerzeichen.length; i++) {
				if(leerzeichen[i].contains("[")){
					break;
				}else{
					result=result+" "+leerzeichen[i];
				}
			}
			return Babo(result);
		}else if(string.split(" ").length>3){
			blub=string.split(" ");
			for (int i = 0; i < blub.length; i++) {
				if(blub[i+1].contains("[")){
					break;
				}else{
					result = result + " " + blub[i];
				}
			}
			return Babo(result);
		}
		else{
			return string.split(" ")[0];
		}
	}
	
	public static int geburtsjahr(String string) {
		String result="";
		result=string.substring(string.length()-5,string.length()-1);
		return Integer.parseInt(result);
	}

	public static int durchs_Renndistanz(String string) {
		String[] res = null;
		res = string.split(" ",2);
		return Integer.parseInt(res[0]);
	}

	public static String Babo(String string){

		return string.replace("'","");
	}

	public static String geschlecht(String string) {
		// TODO Auto-generated method stub
		String result="";
		if(string.equals("m") || string.equals("f")){
			result=string;
		}else{
			result=" ";
		}
		return result;
	}

	public static String aufenthaltsland(String string) {
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
		String result = "";
		for (int i = 0; i < string.length(); i++) {
			if(string.charAt(i)=='['){
				while(string.charAt(i+1)!='/'){
					
					result=result+string.charAt(i+1);

					if(string.charAt(i+1)==' '){
						return result;
					}
					i++;
				}
			}
			
		}
		return result; 
	}
	
	public static void createTable() throws Exception{
		try{
			Connection con =getConnection();
			
			PreparedStatement create_Zwinger = con.prepareStatement("CREATE TABLE IF NOT EXISTS Zwinger(Name VARCHAR(252) PRIMARY KEY);");
			
			PreparedStatement create_Hund = con.prepareStatement("CREATE TABLE IF NOT EXISTS Hund(ID SERIAL PRIMARY KEY,Mama VARCHAR(252),"
					+ "Geburtsland VARCHAR(5),Vater VARCHAR(252),Name VARCHAR(252),Aufenthaltsland VARCHAR(5),Geburtsjahr INT,"
					+ "Geschlecht VARCHAR(1),z_name VARCHAR(252),FOREIGN KEY (z_name) REFERENCES Zwinger (Name));");
			
			PreparedStatement create_Ergebnis = con.prepareStatement("CREATE TABLE IF NOT EXISTS Ergebnis(ID SERIAL PRIMARY KEY,h_id INT,"
					+ "durchs_Renndistanz INT,Rang INT,kumulierte_Punktzahl INT,Land VARCHAR(252),Jahr INT,Anzahl_der_Rennen INT,"
					+ "FOREIGN KEY (h_id) REFERENCES Hund (ID));");
			
			create_Zwinger.executeUpdate();
			create_Hund.executeUpdate();
			create_Ergebnis.executeUpdate();
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

	public static String zwinger(String string){
		string=starKiller(string);
		String result = "";
		String [] blub;
		
		if(string.contains("'s ")){
			blub=string.split("'s ");
			return Babo(blub[0]);
		}else if(string.split(" ").length>3){
			blub=string.split(" ");
			
			for (int i = 1; i < blub.length; i++) {
				
				if(blub[i+1].contains("[")){
					result=result+" "+blub[i];
					break;
				}
			}
			return Babo(result);
		}else{
			return "";
		}
	}

	public static String starKiller(String string){
		return string.replace("* ", "");
	}
}