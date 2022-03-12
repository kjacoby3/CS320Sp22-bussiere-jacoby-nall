package cs320.TBAG.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class CSVReader {
	public static void main(String[] args) {
		
		String path = "C:\\Users\\nicho\\Dropbox\\School\\YORK\\CS-320\\Team Project\\Repository\\CS320Sp22-bussiere-jacoby-nall\\TBAG_Project\\Rooms Table Trial2.csv";
		String line;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			while((line = br.readLine()) != null) {
				String[] columns = line.split(",");
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
