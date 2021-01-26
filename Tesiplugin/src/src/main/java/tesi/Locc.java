package tesi;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/*
 * si deve aggiustare 
 * perch� manca per i get e set automatici di c# e manca anche il costruttore 
 */
public class Locc {
	private HashMap<String,Integer> mappa = new HashMap<String,Integer>();
	private ArrayList<Document> ndoc = new ArrayList<Document>();

	public Locc(ArrayList<Document> getArray) {
		this.ndoc=getArray;
	}

	public void calcolo() throws IOException {
		int locc=0;
		int commento=0;
		Document doc;
		String nomeclasse;

		for(int z=0; z<this.ndoc.size(); z++){

			doc=this.ndoc.get(z);
			NodeList nclass = doc.getElementsByTagName("class");

			for(int i=0 ; i<nclass.getLength(); i++){

				int posizione=0;
				Node clas = nclass.item(i);

				if (clas.getNodeType() == Node.ELEMENT_NODE) {
					Element nna =(Element) clas;
					NodeList child = clas.getChildNodes();

					for(int n=0; n<child.getLength(); n++){

						if(child.item(n).getNodeName().equals("attribute")){

							Element ni = (Element) child.item(n);
							NodeList nomi = ni.getElementsByTagName("name");
							posizione = posizione+nomi.getLength();
						}
					}

					nomeclasse = nna.getElementsByTagName("name").item(posizione).getTextContent();
					Element nfu = (Element) clas;
					NodeList nList = nfu.getElementsByTagName("function");

					for(int temp=0; temp<nList.getLength(); temp++){

						Node nNode = nList.item(temp);
						Element eElement = (Element) nNode;
						NodeList list = eElement.getElementsByTagName("block");
						Node block = list.item(0);
						locc=  locc+contarighe(block);

						//System.out.println("\nNome della classe senza costruttore -->"+nomeclasse+" Numero Locc calcolato in quella classe : "+locc);
						//devo contare i commenti all'interno della funzione
						Element com = (Element) block;

						NodeList ncom = com.getElementsByTagName("comment");

						for(int y=0; y<ncom.getLength(); y++){

							Node comment = ncom.item(y);

							if(comment!=null){

								commento = commento+contacommenti(comment);
								//System.out.println("\nCommento che conta-->"+commento);
							}
						}
						//System.out.println("\nNome della classe senza costruttore -->"+nomeclasse+" Numero Locc calcolato in quella classe : "+locc);
					}

					Element ncon = (Element) clas;
					NodeList nconstr = ncon.getElementsByTagName("constructor");

					for(int j=0; j<nconstr.getLength(); j++){
						Node constr = nconstr.item(j);

						if(constr!=null){

							Element l = (Element) constr;
							NodeList blo = l.getElementsByTagName("block");
							Node block = blo.item(0);
							locc= locc+ contarighe(block);
							//System.out.println("\nNome della classe con il costruttore -->"+nomeclasse+" Numero Locc calcolato in quella classe : "+locc);
							Element com = (Element) block;
							NodeList ncom = com.getElementsByTagName("comment");

							for(int y=0; y<ncom.getLength(); y++){
								Node comment = ncom.item(y);

								if(comment!=null){

									commento = commento+contacommenti(comment);
									//System.out.println("\nAll'interno della classe-->"+nomeclasse+" ci sono "+commento+" Righe da commento da considerare");
								}
							}
						}
					}
					Element pro =(Element) clas;
					NodeList npro= pro.getElementsByTagName("property");

					for(int k=0; k<npro.getLength(); k++){

						Node prop = npro.item(k);

						if(prop!=null){

							Element l = (Element) prop;
							NodeList blo = l.getElementsByTagName("block");
							Node block = blo.item(0);
							locc= locc+ contarighe(block);
							Element com = (Element) block;
							NodeList ncom = com.getElementsByTagName("comment");

							for(int y=0; y<ncom.getLength(); y++){

								Node comment = ncom.item(y);

								if(comment!=null){
									commento = commento+contacommenti(comment);
								}
							}
						}
					}
					locc= locc-commento;
					this.mappa.put(nomeclasse, locc);
					locc=0;
					commento=0;
				}
			}
		}
	}

	private int contacommenti(Node block) throws IOException {

		int commenti=0;
		File file= new File("filename.txt");
		FileWriter fw;
		fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(block.getTextContent());
		bw.close();
		FileReader f= new FileReader(file);
		BufferedReader br = new BufferedReader(f);
		String s=br.readLine();

		while ( s != null ) {

			commenti++;
			s=br.readLine();
		}
		br.close();
		return commenti;
	}

	private int contarighe( Node block) throws IOException {

		File file= new File("filename.txt");
		int loc=0;
		FileWriter fw;
		int commento=0;
		fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(block.getTextContent());
		bw.close();
		FileReader f= new FileReader(file);
		BufferedReader br = new BufferedReader(f);
		String s=br.readLine();
		//System.out.println("\nContenuto del file : "+block.getTextContent());

		while ( s != null ) {
			int num=0;

			if(!s.isEmpty() ) {

				for(int i=0; i<s.length(); i++) {
					//|| s.charAt(i)=='{' || s.charAt(i)=='}'
					if(s.charAt(i)==' '){
						num++;

						if(num==s.length()) {
							commento++;
						}
					}
				}
				loc++;
			}
			s=br.readLine();
		}

		//System.out.println("Numero totale contato : "+loc+" Numero linee non valide :"+commento+"\nNumero effettivo da contare : "+numero);
		br.close();
		return loc-commento;
	}

	public HashMap<String, Integer> getMappa() {
		return mappa;
	}



}


