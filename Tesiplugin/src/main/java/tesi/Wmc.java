package tesi;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Wmc {
	private ArrayList<Document> ndoc = new ArrayList<Document>();
	private HashMap<String,Integer> mappa = new HashMap<String, Integer>();

	public Wmc(ArrayList<Document> getArray) {
		this.ndoc=getArray;
	}

	public void calcolo()  {

		String nomeclasse;
		int wmc = 0;
		Document doc;

		for(int z=0; z<this.ndoc.size(); z++){

			doc=this.ndoc.get(z);
			NodeList nclass = doc.getElementsByTagName("class");

			for(int y=0; y<nclass.getLength(); y++){

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
				NodeList nfun = ncla.getElementsByTagName("function");

				for(int k=0; k<nfun.getLength(); k++){
					wmc =wmc+ contapredicati(nfun.item(k));
				}

				//wmc= contapredicati(cla);
				// System.out.println("\n"+nomeclasse+" "+wmc);

				this.mappa.put(nomeclasse, wmc+1);
				wmc=0;
			}
		}
	}

	private int contapredicati( Node block)  {

		int wmc=0;
		Element el = (Element) block;
		NodeList nif = el.getElementsByTagName("if");
		NodeList nwhile = el.getElementsByTagName("while");
		NodeList nfor = el.getElementsByTagName("for");
		NodeList nswitch = el.getElementsByTagName("switch");

		wmc=nif.getLength();
		wmc = wmc + nwhile.getLength();
		wmc= wmc + nfor.getLength();
		wmc= wmc+nswitch.getLength();
		//System.out.println("\n"+nif.getLength());

		for(int z=0 ; z<nif.getLength(); z++){

			NodeList ncon = nif.item(z).getChildNodes();
			NodeList esp = ncon.item(1).getChildNodes();
			//System.out.println("\n"+esp.item(1).getTextContent());
			NodeList nop = esp.item(1).getChildNodes();

			for(int i =0; i<nop.getLength(); i++){

				if(nop.item(i).getTextContent().equals("||")||nop.item(i).getTextContent().equals("&&")){
					wmc++;
					//System.out.println("\nOperatore  "+nop.item(i).getTextContent());
				}
			}
		}

		for(int z=0 ; z<nwhile.getLength(); z++){

			Element ncon = (Element) nwhile.item(z).getChildNodes().item(1);
			NodeList noper = ncon.getElementsByTagName("operator");

			for(int i=0; i<noper.getLength(); i++){
				if(noper.item(i).getTextContent().equals("||")||noper.item(i).getTextContent().equals("&&")){
					wmc++;
				}
			}
		}

		for(int z=0 ; z<nswitch.getLength(); z++){

			Element ncon = (Element) nswitch.item(z).getChildNodes().item(1);
			NodeList nope = ncon.getElementsByTagName("operator");

			for(int i=0; i<nope.getLength(); i++){

				if(nope.item(i).getTextContent().equals("||")||nope.item(i).getTextContent().equals("&&")){
					wmc++;
				}
			}

			Element bloc =  (Element) nswitch.item(z).getChildNodes().item(3);

			if(bloc!=null){

				NodeList ncase = bloc.getElementsByTagName("case");
				wmc= wmc+ncase.getLength();
			}
		}
		return wmc;
	}

	public HashMap<String, Integer> getMappa() {
		return mappa;
	}
}
