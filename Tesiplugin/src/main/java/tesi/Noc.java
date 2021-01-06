package tesi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Noc {

private  ArrayList<Document> ndoc = new ArrayList<Document>();
private HashMap<String,Integer> mappa = new HashMap<String,Integer>();
	
	public Noc(ArrayList<Document> getArray) {
	this.ndoc=getArray;
	}

	public void ListaNomi()  {
Set<String> nomi = new HashSet<String>(); 
ArrayList<String> listanomi = new ArrayList<String>();
Document doc; 
for(int y=0 ; y<this.ndoc.size(); y++){
doc=this.ndoc.get(y);
	NodeList nsuper = doc.getElementsByTagName("class");
for(int i=0 ; i<nsuper.getLength(); i++) {
	NodeList figlio = nsuper.item(i).getChildNodes();
	
	for(int n=0; n<figlio.getLength(); n++){
		if(figlio.item(n).getNodeName().equals("super")){
			Node name =figlio.item(n);
			NodeList nipoti = name.getChildNodes();
			for(int j=0; j<nipoti.getLength(); j++){
				if(nipoti.item(j).getNodeName().equals("name")){
					String nome = nipoti.item(j).getTextContent();
					
					if(nome.charAt(0) != 'I' || !Character.isUpperCase(nome.charAt(1))){
						nomi.add(nome);
			listanomi.add(nome);

					}
				}
			}
			
			
			
	}
}
}
}
		
Calcolo(listanomi, nomi);
}

private void Calcolo(ArrayList<String> listanomi, Set<String> nomi) {
	int noc=0;
       
	Iterator<String> iter = nomi.iterator();
	while(iter.hasNext()) {
		String f = iter.next();
		noc=Collections.frequency(listanomi, f);	
		this.mappa.put(f, noc);
	
	}
		

}



public HashMap<String, Integer> getMappa() {
	return mappa;
}



}
