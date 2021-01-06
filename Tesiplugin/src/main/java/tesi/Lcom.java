package tesi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Lcom {

private HashMap<String,Float> mappa = new HashMap<String, Float>();
private  ArrayList<Document> ndoc = new ArrayList<Document>();
private int zero=0;
private int uno=0;


public Lcom(ArrayList<Document> getArray) {
	this.ndoc=getArray;
}


public void calcolo()  {
Document doc;

String nomeclasse;
for(int j=0; j<this.ndoc.size(); j++){
	doc=this.ndoc.get(j);
NodeList nclass = doc.getElementsByTagName("class");
for(int i=0; i<nclass.getLength(); i++){
	int posizione=0;
	Node clas = nclass.item(i);
	Element t = (Element) clas;
	NodeList child = clas.getChildNodes();
	for(int n=0; n<child.getLength(); n++){
		if(child.item(n).getNodeName().equals("attribute")){
			Element ni = (Element) child.item(n);
			NodeList nomi = ni.getElementsByTagName("name");
		posizione = posizione+nomi.getLength();
		}
	}
	nomeclasse = t.getElementsByTagName("name").item(posizione).getTextContent();	
float a=attributi(clas);
//System.out.println("\nLa classe :"+nomeclasse +"-->Numero di attributi : "+a);
float n=metodi(clas);
//System.out.println("\nLa classe :"+nomeclasse +"-->Numero di funzioni  : "+n);
float u=referenziati(clas);
//System.out.println("\nLa classe :"+nomeclasse +"-->Numero di riferimenti   : "+u);
float lcom=(((1/a)*u)-n)/(1-n);
//System.out.println("\nLCOM della classe : "+lcom);
if(lcom<0.01){
	lcom=(float) 0.0;
}
if(lcom==-0.0){
	lcom=(float) 0.0;
}


this.mappa.put(nomeclasse, lcom);
}}

}



private float  referenziati(Node clas) {
	Set<String> set = new HashSet<String>();
	Set<String> cop = new HashSet<String>();
	float u=0;
	
Element l = (Element) clas;
Node blocco = l.getElementsByTagName("block").item(0);
NodeList figli = blocco.getChildNodes();
for(int i=0; i<figli.getLength(); i++){
	if(figli.item(i).getNodeName().equals("decl_stmt")){
		Element co = (Element) figli.item(i);
		NodeList nele = co.getElementsByTagName("literal");
		if(nele.getLength()==0){
			
		
		int posizione=0;
		Element attr = (Element) figli.item(i);
	NodeList attri = attr.getElementsByTagName("attribute");
	if(attri.getLength()>0){
		for(int j=0; j<attri.getLength(); j++){
			Element no = (Element) attri.item(j);
			NodeList nom = no.getElementsByTagName("name");
			posizione = posizione + nom.getLength();
		}
	}
	Node tipo = attr.getElementsByTagName("type").item(0);
	Element not = (Element) tipo;
	NodeList nomet = not.getElementsByTagName("name");
	posizione = posizione + nomet.getLength();
	String nome = attr.getElementsByTagName("name").item(posizione).getTextContent();
	
			set.add(nome);
	}
	}
}

	
for(int i=0; i<figli.getLength(); i++){
	if(figli.item(i).getNodeName().equals("function")){
		Element b = (Element) figli.item(i);
		Node blo = b.getElementsByTagName("block").item(0);
		Element nn = (Element) blo;
		NodeList nom = nn.getElementsByTagName("name");
		for(int j=0; j<nom.getLength(); j++){
			cop.add(nom.item(j).getTextContent());
		}
	/*
		Node para = b.getElementsByTagName("parameter_list").item(0);
		Element co = (Element) para;
		NodeList pa = co.getElementsByTagName("name");
		for(int n=0; n<pa.getLength(); n++){
			for(Iterator<String> ite = cop.iterator(); ite.hasNext();){
				String indice2 = ite.next();
				if(indice2.equals(pa.item(n))){
					ite.remove();
				}
			}
		}
		*/
		for(Iterator<String> iterator = set.iterator(); iterator.hasNext();){
	String indice = iterator.next();
	for(Iterator<String> ite = cop.iterator(); ite.hasNext();){
		String indice2 = ite.next();
		if(indice.equals(indice2)){
			u++;
		}
	}
}
		cop.clear();
	}

	if(figli.item(i).getNodeName().equals("constructor")){
		Element b = (Element) figli.item(i);
		Node blo = b.getElementsByTagName("block").item(0);
		Element nn = (Element) blo;
		NodeList nom = nn.getElementsByTagName("name");
		for(int j=0; j<nom.getLength(); j++){
			cop.add(nom.item(j).getTextContent());
		}
		for(Iterator<String> iterator = set.iterator(); iterator.hasNext();){
			String indice = iterator.next();
			for(Iterator<String> ite = cop.iterator(); ite.hasNext();){
				String indice2 = ite.next();
				if(indice.equals(indice2)){
					u++;
				}
			}
		}
				cop.clear();
	}
	if(figli.item(i).getNodeName().equals("property")){
		Element b = (Element) figli.item(i);
		Node blo = b.getElementsByTagName("block").item(0);
		Element nn = (Element) blo;
		NodeList nom = nn.getElementsByTagName("name");
		for(int j=0; j<nom.getLength(); j++){
			cop.add(nom.item(j).getTextContent());
		}
		for(Iterator<String> iterator = set.iterator(); iterator.hasNext();){
			String indice = iterator.next();
			for(Iterator<String> ite = cop.iterator(); ite.hasNext();){
				String indice2 = ite.next();
				if(indice.equals(indice2)){
					u++;
				}
			}
		}
				cop.clear();
	}
}
//System.out.println("\n valore di u:"+u);



	return u;
}

private float  metodi(Node  clas) {
	float n = 0;
	Element l = (Element) clas;
	Node blocco = l.getElementsByTagName("block").item(0);
	NodeList figli = blocco.getChildNodes();
	for(int i=0; i<figli.getLength(); i++){
		if(figli.item(i).getNodeName().equals("function")){
			n++;
		}
		if(figli.item(i).getNodeName().equals("constructor")){
			n++;
		}
		if(figli.item(i).getNodeName().equals("property")){
			Element lf =(Element) figli.item(i);
			NodeList funn = lf.getElementsByTagName("function");
			n=n+funn.getLength();
		}
	}
	
	
	

	return n;
}


private float  attributi( Node clas) {
float a=0;
Element at = (Element) clas;
Node blocco = at.getElementsByTagName("block").item(0);
NodeList figli = blocco.getChildNodes();
for(int i=0; i<figli.getLength(); i++){
	if(figli.item(i).getNodeName().equals("decl_stmt")){
		Element co = (Element) figli.item(i);
		NodeList nele = co.getElementsByTagName("literal");
		if(nele.getLength()==0){
			a++;
		}
	}
}
	return a;
}


public HashMap<String, Float> getMappa() {
	return mappa;
}


public int getZero() {
	return zero;
}


public int getUno() {
	return uno;
}



}
