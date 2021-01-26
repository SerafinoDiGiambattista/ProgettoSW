package tesi;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Rfc {
	private ArrayList<Document> ndoc = new ArrayList<Document>();
	private HashMap<String,Integer> map = new HashMap<String,Integer>();

	public Rfc(ArrayList<Document> getArray) {
		this.ndoc=getArray;
	}

	public void calcolo() {

		for(int i=0; i<this.ndoc.size(); i++){

			Document doc = this.ndoc.get(i);
			NodeList nclass = doc.getElementsByTagName("class");

			for(int j=0; j<nclass.getLength(); j++){

				Node cla = nclass.item(j);
				funzionilocali(cla);
				chiamate(cla);
			}
		}
	}

	private void chiamate(Node cla) {

		int posizione=0;
		String nomeclasse;
		int conto=0;
		Element l = (Element) cla;
		NodeList child = cla.getChildNodes();

		for(int n=0; n<child.getLength(); n++){

			if(child.item(n).getNodeName().equals("attribute")){

				Element ni = (Element) child.item(n);
				NodeList nomi = ni.getElementsByTagName("name");
				posizione = posizione+nomi.getLength();
			}
		}
		nomeclasse = l.getElementsByTagName("name").item(posizione).getTextContent();
		NodeList ncall = l.getElementsByTagName("call");
		conto= conto+ncall.getLength();
		conto = this.map.get(nomeclasse)+conto;
		this.map.put(nomeclasse, conto);
		conto=0;
	}

	/*
 	* per funzioni locali si intede sia il costruttore sia i metodi implementati
 	* a se non � presente il costruttore di solito si dovrebbe contare quello automatico
 	*/

	private void funzionilocali(Node cla) {

		int posizione=0;
		String nomeclasse;
		Element l = (Element) cla;
		NodeList nfun = l.getElementsByTagName("function");
		NodeList child = cla.getChildNodes();

		for(int n=0; n<child.getLength(); n++){

			if(child.item(n).getNodeName().equals("attribute")){

				Element ni = (Element) child.item(n);
				NodeList nomi = ni.getElementsByTagName("name");
				posizione = posizione+nomi.getLength();
			}
		}

		nomeclasse = l.getElementsByTagName("name").item(posizione).getTextContent();
		NodeList nc = l.getElementsByTagName("constructor");
		//considero anche il costruttore automatico che sar� presente anche C#

		this.map.put(nomeclasse, nfun.getLength()+nc.getLength());
	}

	public HashMap<String, Integer> getMap() {
		return map;
	}

}
