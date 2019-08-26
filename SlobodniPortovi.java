/*traziSlPortove je funkcija koja vraca niz slobodnih portova od 9000 pa nadalje,
  cija je duzina broj unesenih servera.*/ 
import java.io.*;
import java.util.*;
import java.net.*;

public class SlobodniPortovi{
	
public static int[] traziSlPortove(int BrServera){
	int[] niz=new int[BrServera];
	int brojac=0;
	for(int i=9000;i<=65535 && brojac!=BrServera;i++){
			try(ServerSocket server=new ServerSocket(i)){ //pokusavamo napraviti server na portu 'i',ako uspijemo,taj indeks 'i' dodajemo u niz
			niz[brojac]=i;
			brojac++; //povecavamo brojac koji nam govori o popunjenosti niza
		}catch(IOException e){
		//ako baci izuzetak,znaci da na portu 'i' vec imamo server,pa taj indeks 'i' preskacemo i nastavljamo sa provjerom
		}
	}
		return niz;
}	
	
 public static void main (String[] args) {
 	
 	int BrServera=300; 
 	int[] niz=new int[BrServera];
 	niz=traziSlPortove(BrServera);
 	for (int i = 0; i<BrServera; i++) {
 		System.out.print (" "+niz[i]);
}
}
}