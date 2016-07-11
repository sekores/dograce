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
		createTable();
		insertTable();
//		System.out.println("Zwinger: "+zwinger("My Boy Snoop [AU/AU 2012]"));
//		System.out.println("Name: "+name("My Boy Snoop [AU/AU 2012]"));
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
					System.out.println(" "+greyhounddata[0]+" "+greyhounddata[1]+" "+greyhounddata[2]+" "+greyhounddata[3]+" "+greyhounddata[4]+" "+greyhounddata[5]+" "+greyhounddata[6]+" "+greyhounddata[7]+" "+greyhounddata[8]+" "+greyhounddata[9]);
					PreparedStatement insert = con.prepareStatement("INSERT INTO Hund(Land,Jahr,Jahresrang,Geschlecht,Papi,Mama,Anzahl_der_Rennen,kumulierte_Punkte,durchs_Renndistanz,geburtsland,aufenthaltsland,geburtsjahr,name,zwinger) VALUES ('"+greyhounddata[0]+"',"+greyhounddata[1]+","+greyhounddata[2]+",'"+geschlecht(greyhounddata[4])+"','"+Babo(greyhounddata[5])+"','"+Babo(greyhounddata[6])+"','"+greyhounddata[7]+"',"+(int)Double.parseDouble(greyhounddata[8])+","+durchs_Renndistanz(greyhounddata[9])+",'"+geburtsland(greyhounddata[3])+"','"+aufenthaltsland(greyhounddata[3])+"',"+geburtsjahr(greyhounddata[3])+",'"+name(greyhounddata[3])+"','"+zwinger(greyhounddata[3])+"');");
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
		}else{
			blub=string.split(" ");
			for (int i = 0; i < blub.length; i++) {
				if(blub[i+1].contains("[")){
					break;
				}else{
					result=result+" "+blub[i];
				}
			}
			return Babo(result);
		}
	}
	
	public static int geburtsjahr(String string) {
		String result="";
		result=string.substring(string.length()-5,string.length()-1);
		return Integer.parseInt(result);
	}

	public static String Babo_2(String string) {
		String result = "";
		for (int i = 0; i < string.length(); i++) {
			while(string.charAt(i)!='['){
				result=result+string.charAt(i);
				i++;
			}
			if(string.charAt(i)=='['){
				return result;
			}
		}
		return result;
	}

	public static int durchs_Renndistanz(String string) {
		String[] res = null;
		res = string.split(" ",2);
		return Integer.parseInt(res[0]);
	}

	public static String Babo(String string){

		return string.replace("'","");
	}

	public static boolean isFirstWord(String string){
		
		int sIndex =0;
		int lIndex = 0;
		String trash="'s ";
		
		
		for (int i = 0; i < string.length(); i++) {
			if(string.charAt(i)==trash.charAt(0) && string.charAt(i+1)==trash.charAt(1))
				sIndex = i;
		}
		
		for (int i=0; i<string.length(); i++){
			if(string.charAt(i)==trash.charAt(2)){
				lIndex = i;
				break;
			}
		}
		return sIndex < lIndex;
	}

	public static String geschlecht(String string) {
		// TODO Auto-generated method stub
		String result="";
		if(string.equals("m") || string.equals("w")){
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
			PreparedStatement create_Hund = con.prepareStatement("CREATE TABLE IF NOT EXISTS Hund(Hund_ID SERIAL PRIMARY KEY, Land VARCHAR(252), Jahr INT, Jahresrang INT, Name VARCHAR(252), Zwinger VARCHAR(252), Geburtsland VARCHAR(5), Aufenthaltsland VARCHAR(5), Geburtsjahr INT, Geschlecht VARCHAR(1), Papi VARCHAR(252), Mama VARCHAR(252), Anzahl_der_Rennen INT, kumulierte_Punkte INT, durchs_Renndistanz INT);");
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

	public static String zwinger(String string){
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
}

