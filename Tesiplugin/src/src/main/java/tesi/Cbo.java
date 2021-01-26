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

public class Cbo {
	private HashMap<String,Integer> mappa = new HashMap<String,Integer>();
	private  ArrayList<Document> ndoc = new ArrayList<Document>();


	

	public Cbo(ArrayList<Document> ndoc) {
		this.ndoc=ndoc;
	}



	public void calcolo() {
		/*- uso di tipo (ovvero reference attributes , cio� attributi di A che
hanno come tipo un'altra classe)*/
		
		
		usoTipo();
	
		argomentiMetodi();
		chiamataMetodi();
		
		

	}

	private void chiamataMetodi(){

		Document doc;
		int conto=0;


		String nomeclasse;
		HashMap<String,String> mappal = new HashMap<String,String>();
		ArrayList<String> funzioni = new ArrayList<String>();
		Set<String> nomim = new HashSet<String>();

		for(int y=0 ; y<this.ndoc.size(); y++){
			doc=this.ndoc.get(y);
			
			NodeList nclass = doc.getElementsByTagName("class");

			for(int m=0; m<nclass.getLength(); m++){

				int posizione =0;
				Node clas = nclass.item(m);
				Element l = (Element) nclass.item(m);
				NodeList child = clas.getChildNodes();

				for(int n=0; n<child.getLength(); n++){
					if(child.item(n).getNodeName().equals("attribute")){
						Element ni = (Element) child.item(n);
						NodeList nomi = ni.getElementsByTagName("name");
						posizione = posizione+nomi.getLength();
					}
				}

				nomeclasse = l.getElementsByTagName("name").item(posizione).getTextContent();

				/*
				 * nel caso in cui il prof vuole il tipo devi vedere se esiste element se non esiste
				 * vedere il nome della variabile � presente in decml_stmt o argument list della sua funzione
				 * se non lo trovi non so. Il problema � come prendere il tipo della variabile negli argomenti della sua funzione
				 */

				NodeList ncall = l.getElementsByTagName("call");

				for(int i=0; i<ncall.getLength(); i++){
					//	System.out.println("\n Elemento della call "+ncall.item(i).getTextContent());
					String chiamata =ncall.item(i).getFirstChild().getTextContent();
					NodeList figli = ncall.item(i).getChildNodes();

					for(int j=0; j<figli.getLength(); j++){

						if(figli.item(j).getNodeName().equals("argument_list")){

							Node lista =figli.item(j);
							Element li = (Element) lista ;
							NodeList argomenti = li.getElementsByTagName("argument");

							if(argomenti.getLength()>0 && !chiamata.equals("Console.WriteLine")){

								chiamata = chiamata+" "+argomenti.getLength();
								nomim.add(chiamata);
							}

							else if(argomenti.getLength()==0 || chiamata.equals("Console.WriteLine")){
								nomim.add(chiamata);
							}
						}
					}
				}
					
					
				/*
				 * qui sto creadno la mappa che mi serve per conservare valore e costruttore
				 */
				NodeList dec = l.getElementsByTagName("decl_stmt");
				for(int j=0; j<dec.getLength(); j++){
					String chiave =null;
					String valore = null;
					NodeList figli = dec.item(j).getChildNodes().item(0).getChildNodes();

					for(int n=0; n<figli.getLength(); n++){

						if(figli.item(n).getNodeName().equals("init")){
							Element op = (Element) figli.item(n);
							Node oper = (Node) op.getElementsByTagName("operator").item(0);

							if(oper!=null){
								if(oper.getTextContent().equals("new")){
									Element nt = (Element) dec.item(j);
									Node tipo = nt.getElementsByTagName("type").item(0);
									Element nu = (Element) tipo;
									NodeList nunomi = nu.getElementsByTagName("name");
									chiave = nt.getElementsByTagName("name").item(nunomi.getLength()).getTextContent();
									Element posn = (Element) figli.item(n);

									if(posn.getElementsByTagName("name").item(0) != null){
										valore = posn.getElementsByTagName("name").item(0).getTextContent();
										NodeList nargo = posn.getElementsByTagName("argument");

										if(nargo.getLength()>0){
											valore = valore+nargo.getLength();
											mappal.put(chiave, valore);
										}
										else if(nargo.getLength()==0){
											mappal.put(chiave, valore);
										}
									}
								}
							}
						}
					}
				}
				/*
				 * qui crea l'array list contente i nomi delle funzioni che devo eliminare dall'insieme che considero 
				 */
				NodeList nfunc = l.getElementsByTagName("function");

				for(int i=0; i<nfunc.getLength(); i++){
					NodeList figli = nfunc.item(i).getChildNodes();

					for(int j=0; j<figli.getLength(); j++){

						if(figli.item(j).getNodeName().equals("name")){
							funzioni.add(figli.item(j).getTextContent());
						}
					}
				}

				/*
				 * qui elimino dal set i nomi delle funzioni locali della classe 
				 */
				for(String funzione : funzioni){

					for (Iterator<String> iterator = nomim.iterator(); iterator.hasNext();){
						String nome = iterator.next();

						if(nome.equals(funzione)){
							iterator.remove();
						}
					}
				}
				/*
				 * una volta creato la mappa locale vedo chi chiamam un metodo uguale e controllo se hanno lo stesso 
				 * costruttore
				 */
				ArrayList<String> a = new ArrayList<String>();
				a.addAll(nomim);
				Set<String>  doppioni= new HashSet<String>();

				for (int i = 0; i < a.size(); i++) {
					int con=0;

					if(a.get(i).contains(".")){
						String parte = a.get(i).substring(a.get(i).lastIndexOf(".")+1);
						for(int j=0; j< a.size(); j++){
							if(a.get(j).contains(".")){
								String parte2 = a.get(j).substring(a.get(j).lastIndexOf(".")+1);
								if(parte.equals(parte2)){
									con++;

									if(con>1){
										//System.out.println("\nLa prima "+parte+" la seconda "+parte2);
										String chiave1 = a.get(i).substring(0, a.get(i).indexOf('.'));
										//System.out.println("\n"+chiave1);
										Set<String> keys = mappal.keySet();

										for (String k : keys) {
											int po =0;

											if(chiave1.equals(k)){
												String valore = mappal.get(chiave1);

												for(String k2 : keys){

													if(valore.equals(mappal.get(k2))){
														po++;

														if(po>1){
															doppioni.add(a.get(i));
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			
				
				
					
				/*	mappal.forEach((k, v) -> {
					System.out.println("\nChiave "+k+"  Valore "+v);
				});
				*/
				if(doppioni.size()==0){
					conto=conto+nomim.size();
				}
				else if(doppioni.size()!=0){
					conto=conto+nomim.size()-(doppioni.size()-1);
				}
				//System.out.println("\n"+doppioni.toString());
				//System.out.println("\n"+nomim.toString()+" "+conto+" ");
				conto = this.mappa.get(nomeclasse)+conto;
				this.mappa.put(nomeclasse, conto);

				conto=0;
				nomim.clear();
				mappal.clear();
				funzioni.clear();
			}
		}
	}
	private void usoTipo () {
		/*
		 * prima soluzione fare un array contente tutte le classi e capire da li se ce qualche referencs di una classe
		 * esterna il problema che dovrei passare non solo un doc ma un array di doc 
		 * la seconda � controllare se il tipo di un attributo corrisponde a un tipo semplice altrimenti 
		 * corrisponder� al tipo di una classe 
		*/
		Document doc;
		int cbo=0;
		String nomeclasse;

		for(int y=0 ; y<this.ndoc.size(); y++){
			doc=this.ndoc.get(y);
			NodeList nclass = doc.getElementsByTagName("class");

			for(int i=0; i<nclass.getLength(); i++){

				int posizione=0;
				Node classe = nclass.item(i);
				Element yu = (Element) classe;
				NodeList child = classe.getChildNodes();

				for(int n=0; n<child.getLength(); n++){

					if(child.item(n).getNodeName().equals("attribute")){
						Element ni = (Element) child.item(n);
						NodeList nomi = ni.getElementsByTagName("name");
						posizione = posizione+nomi.getLength();
					}
				}
				nomeclasse = yu.getElementsByTagName("name").item(posizione).getTextContent();

				NodeList nattri= yu.getElementsByTagName("decl_stmt");

				for(int t=0; t<nattri.getLength(); t++){
					Node attri = nattri.item(t);

					if(attri.getTextContent().contains("float") || attri.getTextContent().contains("sbyte") || attri.getTextContent().contains("short") ||
							attri.getTextContent().contains("int") || attri.getTextContent().contains("long")
							||  attri.getTextContent().contains("byte") || attri.getTextContent().contains("ushort")|| attri.getTextContent().contains("uint") ||
							attri.getTextContent().contains("ulong") || attri.getTextContent().contains("char") || attri.getTextContent().contains("double")|| attri.getTextContent().contains("bool")||
							attri.getTextContent().contains("string")){
						//System.out.println("\nNon aumento il Cbo della classe "+attri.getTextContent());
					}
					else{//System.out.println("\n"+attri.getTextContent());
						cbo++;}
				}
				//	System.out.println("\n Il cbo della classe � "+name.getTextContent()+"   : "+cbo);
				this.mappa.put(nomeclasse, cbo);
				cbo=0;
			}
		}
		/*
		this.mappa.forEach((k, v) -> {
			System.out.println("Prima Key: " + k + ", Value: " + v);
		});
		*/
	}

	private void argomentiMetodi() {
		Document doc;
		int cbo=0;
		String nomeclasse;

		for(int y=0 ; y<this.ndoc.size(); y++){
			doc=this.ndoc.get(y);

			NodeList nclass = doc.getElementsByTagName("class");

			for(int i=0; i<nclass.getLength(); i++){

				int posizione=0;
				Node classe = nclass.item(i);
				Element yu = (Element) classe;
				NodeList nfunc = yu.getElementsByTagName("function");
				NodeList child = classe.getChildNodes();

				for(int n=0; n<child.getLength(); n++){

					if(child.item(n).getNodeName().equals("attribute")){

						Element ni = (Element) child.item(n);
						NodeList nomi = ni.getElementsByTagName("name");
						posizione = posizione+nomi.getLength();
					}
				}

				nomeclasse = yu.getElementsByTagName("name").item(posizione).getTextContent();

				for(int t=0; t<nfunc.getLength(); t++){

					Node func = nfunc.item(t);
					Element para = (Element) func;
					NodeList npara = para.getElementsByTagName("parameter_list");
					Node attri = npara.item(0);
					Element b = (Element)attri;

					if(b!=null){

						NodeList ntipi = b.getElementsByTagName("type");
						/*
				 		* devo prendere solo il primo tipo della variabile
				 		*/

						for(int z=0; z<ntipi.getLength(); z++){
							Node attri1= ntipi.item(z);
							if(attri1!=null && ntipi.getLength()>0){

								//System.out.println("\nTipi che stampa =="+attri1.getTextContent());

								if(attri1.getTextContent().contains("float") || attri1.getTextContent().contains("sbyte") || attri1.getTextContent().contains("short") ||
										attri1.getTextContent().contains("int") || attri1.getTextContent().contains("long")
										||  attri1.getTextContent().contains("byte") || attri1.getTextContent().contains("ushort")|| attri1.getTextContent().contains("uint") ||
										attri1.getTextContent().contains("ulong") || attri1.getTextContent().contains("char") || attri1.getTextContent().contains("double")|| attri1.getTextContent().contains("bool")||
										attri1.getTextContent().contains("string")){
									//System.out.println("\nNon aumento il Cbo della classe ");
								}
								else{
									cbo++;
									//System.out.println("\nAumento cbo  "+attri1.getTextContent()+cbo);
								}
							}
						}
					}
				}

				//System.out.println("\n Il cbo della classe � "+name.getTextContent()+"   : "+cbo);
				cbo=this.mappa.get(nomeclasse)+cbo;
				this.mappa.put(nomeclasse, cbo);
				cbo=0;
			}
		}
	}
	public HashMap<String, Integer> getMappa() {
		return mappa;
	}
}
