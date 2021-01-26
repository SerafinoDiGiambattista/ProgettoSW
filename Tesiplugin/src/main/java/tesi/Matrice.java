package tesi;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.swing.JScrollPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
public class Matrice {
	

	private ArrayList<Document> ndoc = new ArrayList<Document>();
	
	private Rfc rfc; 
	private Wmc wmc ;
	private Noc noc;
	private Locc loc;
	private Lcom lcom;
	private Dit dit;
	private Cbo cbo;
	private String nome;
	
	
	public Matrice(String nome, ArrayList<Document> ndoc, Wmc wmc, Rfc rfc, Noc noc, Locc loc, Lcom lcom, Dit dit, Cbo cbo){
		this.ndoc=ndoc;
		this.wmc=wmc;
		this.rfc=rfc;
		this.noc=noc;
		this.loc=loc;
		this.lcom= lcom;
		this.dit= dit;
		this.cbo=cbo;
		this.nome=nome;
	}
	
	
	public Matrice() {
		
	}


	public void  creazione(){

		String[]colonne={"Nome Classe" ,"WMC","RFC","NOC","LOCC","LCOM","DIT","CBO"};
	 	String[] colonne2={"Nome Classe Libreria", "Noc"};
	 	String[][] valori={};
	 	String[][] valori2={};

	 	DefaultTableModel lista= new DefaultTableModel(valori, colonne);
		Document doc;
		HashMap<String,Integer> mappa = new HashMap<String, Integer>();
		mappa=this.noc.getMappa();
		Set<String> nomic = new HashSet<String>();

		for(int z=0; z<this.ndoc.size(); z++){

			doc=this.ndoc.get(z);
			NodeList nclass = doc.getElementsByTagName("class");

			for(int y=0; y<nclass.getLength(); y++){

				String nomeclasse;
				int posizione=0;
				Node cla = nclass.item(y);
				Element ncla = (Element) cla;
				NodeList child = cla.getChildNodes();

				for(int n=0; n<child.getLength(); n++){

					if(child.item(n).getNodeName().equals("attribute")){

						Element ni = (Element) child.item(n);
						NodeList nomi = ni.getElementsByTagName("name");
						posizione = posizione+nomi.getLength();
					}
				}
				nomeclasse = ncla.getElementsByTagName("name").item(posizione).getTextContent();
				nomic.add(nomeclasse);
			}
		}
		ArrayList<String> array = new ArrayList<String>();
		array.addAll(nomic);
		Collections.sort(array, String.CASE_INSENSITIVE_ORDER);

		for(String indice:array){

			int wmc=0;
			int rfc=0;
			int noc=0;
			int loc=0;
			float lcom=0;
			int dit =0;
			int cbo=0;
			wmc= this.wmc.getMappa().get(indice);

	  		String nwmc ;
			nwmc=String.valueOf(wmc).toString();

			rfc=this.rfc.getMap().get(indice);
			String nrfc= String.valueOf(rfc).toString();
			Set<String> keys = this.noc.getMappa().keySet();

			for (String k : keys) {
				if(indice.equals(k)){
					noc=this.noc.getMappa().get(k);
				}
			}

			String nnoc = String.valueOf(noc).toString();
			mappa.remove(indice);
			loc= this.loc.getMappa().get(indice);
			String nloc = String.valueOf(loc).toString();
			lcom= this.lcom.getMappa().get(indice);
		 	String nlcom = String.valueOf(lcom).toString();
		 	dit= this.dit.getMap().get(indice);
		 	String ndit = String.valueOf(dit).toString();
		 	cbo= this.cbo.getMappa().get(indice);
		 	String ncbo = String.valueOf(cbo).toString();

		 	lista.addRow(new String[]{indice, nwmc, nrfc, nnoc, nloc, nlcom, ndit, ncbo});
		}

		DefaultTableModel lista2= new DefaultTableModel(valori2, colonne2);
		Set<String> keys = mappa.keySet();
		ArrayList<String> arrayli = new ArrayList<String>();
		arrayli.addAll(keys);
		Collections.sort(arrayli, String.CASE_INSENSITIVE_ORDER);

		for (String indice : arrayli) {

			String valore= String.valueOf(mappa.get(indice)).toString();
			lista2.addRow(new String[]{indice,valore});
		}

		JTable tabella = new JTable(lista);
		JTable tabella2= new JTable(lista2);
		String filename = nome.concat("Risultati.txt");

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(tabella);
			oos.close();
		}
		catch(IOException e) {
			System.out.println("Problem creating table file: " + e);
			return;
		}

		System.out.println("JTable correctly saved to file " + filename);
		String filenoc = nome.concat("RisultatiNOC.txt");

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filenoc));
			oos.writeObject(tabella2);
			oos.close();
		}
		catch(IOException e) {
			System.out.println("Problem creating table file: " + e);
			return;
		}

		System.out.println("JTable correctly saved to file " + filenoc);
		//Visualizza(tabella, tabella2);
	}

	public void lettura(String fileo, Scanner scan){

		JTable table;
		JTable table2;
		JFrame frame1 = new JFrame(); 
		JFrame frame2 = new JFrame(); 
		String file =  fileo.concat("Risultati.txt");
		String file2 = fileo.concat("RisultatiNOC.txt");
		File filev= new File(file);

		while(!filev.isFile()){

			while(scan.hasNextLine()){

				System.out.println("\nInserisci nome file valido");
				String cos=scan.next();
				file= cos.concat("Risultati.txt");
				file2=cos.concat("RisultatiNOC.txt");
			}
			filev = new File(file);
		}

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			table = (JTable) ois.readObject();
			ois.close();
		}
		catch(Exception e) {
			System.out.println("Problem reading back table from file: " + file);
			return;
		}

		frame1.add(new JScrollPane(table));
		frame1.setSize(300,400);    
		frame1.setVisible(true);

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file2));
			table2 = (JTable) ois.readObject();
			ois.close();
		}
		catch(Exception e) {
			System.out.println("Problem reading back table from file: " + file2);
			return;
		}
		frame2.add(new JScrollPane(table2));
		frame2.setSize(300,400);    
		frame2.setVisible(true);
	}
}
