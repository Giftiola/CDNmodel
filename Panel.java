/**
 * @(#)Panel.java
 *
 *
 * @author Aleksandar Lukac
 * @version 1.00 2019/8/14 u 
 *Ova klasa nam pravi okvir i raspored klijenata i servera na osnovu zadatog broja
 *izracunava udaljenosti klijenata od servera i daje listu najblizih servera za svakog klijenta u obliku matrice
 *Konstruktor dobija podatke o broju klijenat i servera i onda konstruktor poziva funkcije za pravljenje rasporeda
 *udaljenosti i najbliyih servera
 *Postoje i pomocne fukcije za ispis koje sluze za internu provjeru ispravnosti rada funkcija konstruktora
 *Funkcija dodaj labele sluzi za potavljanje labela iynad servera i klijenata,
 *
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.math.*;

public class Panel extends JPanel  {
	int[][] raspored;
	int brojS;
	int brojK;
	double [][] udaljenosti;
	int[][] najbliziServeri;

    public Panel(int x, int y) {
    	setLayout(null);
    	brojK=y;
    	brojS=x;
    	raspored=dajRaspored(y,x);
    	udaljenosti=dajUdaljenosti();
    	najbliziServeri=dajNablizeServere();
    }
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	for (int i = 0; i<brojS; i++) {
    		g.setColor(Color.RED);
    		g.fillOval(raspored[i][0],raspored[i][1],40,40);
    		g.setColor(Color.BLACK);
    		g.setFont( new Font("Arial",Font.BOLD,14));
    		g.drawString("S"+(i+1),raspored[i][0]+13,raspored[i][1]+23);
    		
		}
		for (int i = brojS; i<brojK+brojS; i++) {
			g.setColor(Color.GREEN);
			g.fillRect(raspored[i][0],raspored[i][1],40,40);
			g.setColor(Color.BLACK);
    		g.drawString("K"+(i+1-brojS),raspored[i][0]+13,raspored[i][1]+23);
    		//g.setFont(12);
		}
		
    	
    }
    
    public static int[][] dajRaspored (int kl, int ser){
    	int[][] ras=new int[kl+ser][2];
    	Random rand=new Random();
    	int x;int y;
    	for (int i = 0; i<kl+ser; i++) {
    		if (i==0){
    			x=rand.nextInt(1300);y=rand.nextInt(680);
    			ras[i][0]=x;ras[i][1]=y;
    		}else{
    			ras[i][0]=rand.nextInt(1300)+10;
    			ras[i][1]=rand.nextInt(680)+10;
    		}
    		
    			
    	}
    	return ras;
    }
    public double [][] dajUdaljenosti(){
    	double [][] rez=new double [brojK][brojS];
    	for (int i = 0; i<brojK; i++) {
    		for (int j = 0; j<brojS; j++) {
    			rez[i][j]=Math.pow(raspored[i+brojS][0]-raspored[j][0]+0.0,2.0)+Math.pow(raspored[i+brojS][1]-raspored[j][1]+0.0,2.0);
    			rez[i][j]=Math.sqrt(rez[i][j]);
			}
		}
		return  rez;
    } 
    
    public void dodajLabele(){
    	boolean prekidac=true;
    	JLabel[] labele=new JLabel[brojS+brojK];
    	JTextField[] tekst=new JTextField[brojS+brojK];
    	JLabel[] labeleFin=new JLabel[brojK];// labela koja je vidljiva po zavrsetku rada
    	JTextField[] tekstFin=new JTextField[brojK];// tekst pole koje je vidljivo po zavrsetku rada
    	JButton dugme=new JButton("promijeni");
    	for (int i = 0; i<brojS; i++) {
    		labele[i]=new JLabel("Opterecenje Mb/s");
    		tekst[i]=new JTextField("opterecenje");
    		//labele[i].
    		labele[i].setBounds(raspored[i][0]-50,raspored[i][1]-20,100,20);
    		tekst[i].setBounds(raspored[i][0]+50,raspored[i][1]-20,40,20);
    		this.add(labele[i]);
    		this.add(tekst[i]);
		}
		for (int i = brojS; i<brojK+brojS; i++) {
			labele[i]=new JLabel("Preuzima sa");
			labele[i].setVisible(prekidac);
			labele[i].setBounds(raspored[i][0]-50,raspored[i][1]-20,72,20);
			
    		tekst[i]=new JTextField("server");
    		tekst[i].setText("Server "+najbliziServeri[i-brojS][0]);
    		tekst[i].setVisible(prekidac);
    		tekst[i].setBounds(raspored[i][0]+22,raspored[i][1]-20,50,20);
    		
    		labeleFin[i-brojS]=new JLabel("Zavrseno u ");
    		labeleFin[i-brojS].setVisible(!prekidac);
    		labeleFin[i-brojS].setBounds(raspored[i][0]-50,raspored[i][1]-20,72,20);
    		
    		tekstFin[i-brojS]=new JTextField();
    		tekstFin[i-brojS].setVisible(!prekidac);
    		tekstFin[i-brojS].setBounds(raspored[i][0]+22,raspored[i][1]-20,50,20);
    		
    		this.add(labele[i]);
    		this.add(tekst[i]);
    		this.add(tekstFin[i-brojS]);
    		this.add(labeleFin[i-brojS]);
		}
		dugme.setVisible(true);
		dugme.setBounds(500,0,100,30);
		dugme.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				boolean pr=true;
				if(labele[brojS].isVisible())
					pr=false;
					
				for (int i = brojS; i<brojK+brojS; i++) {
					labele[i].setVisible(pr);
					labeleFin[i-brojS].setVisible(!pr);
					tekst[i].setVisible(pr);
					tekstFin[i-brojS].setVisible(!pr);
					}
				}
			});
		this.add(dugme);
    }
    
    
    
	
	public  void ispisUdaljenosti(){
		for(int i=0;i<brojK;i++){
			for(int j=0;j<brojS;j++){
				System.out.print(udaljenosti[i][j]+"\t");
			}
			System.out.println("");
		}		
	}
	
	public  void ispisNablizihServera(){
		for(int i=0;i<brojK;i++){
			for(int j=0;j<brojS;j++){
				System.out.print(najbliziServeri[i][j]+"\t");
			}
			System.out.println("");
		}		
	}
	
	public int[][] dajNablizeServere(){
		int sw=0;double swd=0.0;
		int[][] rez=new int[brojK][brojS];
		double[][] pom=new double [brojK][brojS];
		for (int i = 0; i<brojK; i++) {
			for (int j = 0; j<brojS; j++) {
				rez[i][j]=j+1;
				pom[i][j]=udaljenosti[i][j];
			}			
		}
		for (int i = 0; i<brojK; i++)
			for (int j = 0; j<brojS; j++)
				for(int k=brojS-1;k>j;k--)
					if(pom[i][k]<pom[i][k-1]){
						swd=pom[i][k];
						sw=rez[i][k];
						pom[i][k]=pom[i][k-1];
						rez[i][k]=rez[i][k-1];
						pom[i][k-1]=swd;
						rez[i][k-1]=sw;						
					}
		return rez;
		
	}
    
    
}