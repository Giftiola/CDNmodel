/**
 * @(#)PocetnePostavke.java
 *
 *
 * @author Aleksandar Lukac
 * @version 1.00 2019/8/14 u 
 *Ova klasa nam sluzi da procita parametre koji su zadani i da ih sacuva u Array listu
 *Citanje se vrsi pomocu funkcije citaj.
 *takodjer ova klasa ima funkciju setujFoldere koja na osnovu broja Servera i klijenata
 *pravi odredjeni broj foldera koji predstavljaju klijente i servere
 *i kopira odredjeni sadrzaj u server foldere
 *potrebno je imati direktorijume C:\Projekat,C:\Projekat\Serveri, C:\Projekat\Klijenti,C:\Projekat\Parametri.txt, C:\Projekat\Sadrzaj.jpg
 *
 */
import java.util.*;
import java.io.*;


public class PocetnePostavke {

    public static ArrayList<String> citaj(String lokacija){// funkcija citanja parametara
		ArrayList<String> rez=new ArrayList<String>();
		try{
			BufferedReader br=new BufferedReader(new FileReader(lokacija));
			String line;
			while((line=br.readLine())!=null){
				rez.add(line);
			}
			br.close();
		}
		catch(IOException e){
			System.out.println(e);
		}
		return rez;
	}
	
	public static void setujFoldere(int s, int k){// funkcija setovanja Foldera
		for(int i=1;i<s+1;i++)
      		new File("C:\\Projekat\\Serveri\\Server"+i).mkdir();
      
		for(int i=1;i<k+1;i++)
			 new File("C:\\Projekat\\Klijenti\\Klijent"+i).mkdir();
		
		for(int i=1;i<s+1;i++){
			String put="C:\\Projekat\\Serveri\\Server"+i;

			try{
				System.out.println(put);
				FileInputStream in=new FileInputStream("C:\\Projekat\\Sadrzaj.jpg");
				FileOutputStream out=new FileOutputStream(put+"\\Sadrzaj.jpg");
				int b;
				while((b=in.read())!=-1)
					out.write(b);
				in.close();
				out.close();
				}
				catch(Exception e){
				e.printStackTrace();
				}
		}
	}
    
}