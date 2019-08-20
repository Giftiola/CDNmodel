/**
 * @(#)Pokreni.java
 *
 *
 * @author 
 * @version 1.00 2019/8/14
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.math.*;

public class Pokreni {

    public static void main (String[] args) {
    	ArrayList<String> podaci=PocetnePostavke.citaj("C:\\Projekat\\Parametri.txt");
    	
    	int brojS=Integer.parseInt(podaci.get(0));
    	int brojK=Integer.parseInt(podaci.get(1));
    	double maxOptServ=Double.parseDouble(podaci.get(2));
		double maxPrenosKap=Double.parseDouble(podaci.get(3));
		int vrijemCek=Integer.parseInt(podaci.get(4));
		
		PocetnePostavke.setujFoldere(brojS,brojK);
    	
    	JFrame frame =new JFrame();
    	 Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
    	 frame.setSize(dim.width,dim.height);
	     frame.setLocationRelativeTo(null);
	     frame.setTitle("Raspored Servera i klijenata");
	     frame.setVisible(true);
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     
	     
	     Panel panel=new Panel(brojS,brojK);
	     frame.add(panel);
	     panel.dodajLabele();



	}
    
    
}