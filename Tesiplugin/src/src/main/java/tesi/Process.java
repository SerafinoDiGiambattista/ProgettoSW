package tesi;

import static tesi.Cbo.*;
import static tesi.Dit.*;
import static tesi.Documenti.*;
import static tesi.Lcom.*;
import static tesi.Locc.*;
import static tesi.Matrice.*;
import static tesi.Noc.*;
import static tesi.Rfc.*;
import static tesi.Wmc.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Process {

	public static void main(String[] args){

		Scanner scan = new Scanner(System.in);
		System.out.println("\nQuale operazione vuoi effettuare"+"\nScrivere calcolo per calcolare le metriche OO di un sistema"+
				"\nScrivere leggo per leggere i risultati dall'archivio");
		String comando = scan.nextLine();

		while(!comando.equals("leggo")&& !comando.equals("calcolo")){

			System.out.println("\nInserisci la parola calcolo o leggo ");
			comando = scan.nextLine();
		}

		if(comando.equals("calcolo")){

			System.out.println("\nInserisci il percorso file valido");
			String directoryName= scan.nextLine();
			Documenti doc = new Documenti();
			ArrayList<Document> ndoc = new ArrayList<Document>();
			ArrayList<String> files= new ArrayList<String>();
			files = doc.listf(directoryName, files,scan);

			for(String file  : files){
				//System.out.println("\n"+file);
				File inputFile = new File(file);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder;

				try {
					dBuilder = dbFactory.newDocumentBuilder();
					Document doci;
					doci = dBuilder.parse(inputFile);
					doci.getDocumentElement().normalize();
					ndoc.add(doci);

				}catch(ParserConfigurationException ebs){
					ebs.printStackTrace();
				}

				catch (IOException e) {
					e.printStackTrace();
				}

				catch (SAXException e) {
					e.printStackTrace();
				}
			}

			Wmc wmc = new Wmc(ndoc);
			wmc.calcolo();
			Dit dit = new Dit(ndoc);
			dit.calcolo();
			Rfc rfc = new Rfc( ndoc);
			rfc.calcolo();

			Lcom lcom = new Lcom( ndoc);
			lcom.calcolo();

			Noc noc = new Noc( ndoc);
			noc.listaNomi();
			Locc loc = new Locc( ndoc);

			try {
				loc.calcolo();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Cbo cbo = new Cbo( ndoc);
			cbo.calcolo();

			String sistema= directoryName.substring(directoryName.lastIndexOf('\\')+1);

			Matrice ce = new Matrice(sistema,ndoc,wmc, rfc, noc, loc, lcom, dit, cbo);
			ce.creazione();
			ce.lettura(sistema,scan);

			System.out.println("\nInserisci il comando termina per fermare il programma :");
			String term = scan.nextLine();
			scan.close();

			if(term.equals("termina")) {
				System.exit(0);
			}
		}

		if(comando.equals("leggo")){

			System.out.println("\nInserisci il nome del sistema");
			String tab = scan.nextLine();
			Matrice ma = new Matrice();
			ma.lettura(tab,scan);

			System.out.println("\nInserisci termina per stoppare il programma : ");
			String term = scan.next();

			if(term.equals("termina")) System.exit(0);
			scan.close();
		}
		scan.close();
	}
}
