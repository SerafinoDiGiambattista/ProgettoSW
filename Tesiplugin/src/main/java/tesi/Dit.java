package tesi;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Dit {
	private  ArrayList<Document> ndoc = new ArrayList<Document>();
	private HashMap<String,Integer> map = new HashMap<String,Integer>();
	private   Set<String>  noutente = new HashSet<String>();
	public Dit(ArrayList<Document> getArray) {
		this.ndoc=getArray;
	}



	public void calcolo() {
		Document doc;
		String nomeclasse;
		HashMap<String,String> mappa = new HashMap<String,String>();
		int dit=0;

		for(int i=0; i<this.ndoc.size();i++ ){

			doc=this.ndoc.get(i);
			NodeList nclass = doc.getElementsByTagName("class");

			for(int j=0;j<nclass.getLength(); j++){

				int posizione=0;
				Node nsuper = nclass.item(j);
				Element q = (Element)nsuper;
				NodeList child = nsuper.getChildNodes();

				for(int n=0; n<child.getLength(); n++){

					if(child.item(n).getNodeName().equals("attribute")){
						Element ni = (Element) child.item(n);
						NodeList nomi = ni.getElementsByTagName("name");
						posizione = posizione+nomi.getLength();
					}
				}

				nomeclasse =q.getElementsByTagName("name").item(posizione).getTextContent();
				/*
			 	* noe del super da aggiustare perch� nel caso in cui sono presenti pi� super prende tutti i nomi
			 	*/
				mappa.put(nomeclasse, "null");
				NodeList figli = nsuper.getChildNodes();

				for(int n=0; n<figli.getLength(); n++){
					if(figli.item(n).getNodeName().equals("super")) {
						NodeList nipoti = figli.item(n).getChildNodes();

						for(int m=0; m<nipoti.getLength(); m++){
							if(nipoti.item(m).getNodeName().equals("name")){
								String nome = nipoti.item(m).getTextContent();

								if(nome.charAt(0) != 'I' || !Character.isUpperCase(nome.charAt(1))){
									//	System.out.println("\n"+nome);
									mappa.put(nomeclasse, nome);
									this.noutente.add(nome);
								}
							}
						}
					}
				}
			}
		}
		Set<String> keys2 = mappa.keySet();

		for (String k : keys2) {
			for (String k1 : keys2) {
				if(mappa.get(k).equals(k1)){
					this.noutente.remove(mappa.get(k));
				}
			}
		}

		//eliminare nel noutente i doppioni
		Set<String> keys = mappa.keySet();
		for (String k : keys){
			this.map.put(k, 0);
		}

		for (String k : keys) {
			dit=conto(mappa.get(k),dit , mappa, k);
	  		dit=0;
		}

		//stampa();
	}

	private int conto(String v, int conto, HashMap<String, String> mappa, String v2) {
		Set<String> keys = mappa.keySet();

		for (String k : keys) {

			if(k.equals(v)){

				conto++;

				//System.out.println("\n Chiave risconstrata: "+k+" Valore della chiave riscontrata : "+mappa.get(k)+" La classe ha un Dit ="+conto);
				this.map.put(v2, conto);
				//this.map.replace(v2, conto);
				//System.out.println("\n Classe "+v2 +" "+conto+" "+v);

				conto(mappa.get(k),conto, mappa,v2);
			}
		}

		for(String indice : this.noutente){

			if(v.equals(indice)){
				conto++;
				this.map.put(v2, conto);
				//System.out.println("\n Classe "+v2 +" "+conto+" "+v);
			}
		}
		return conto;
	}

	public HashMap<String, Integer> getMap() {
		return map;
	}
}
