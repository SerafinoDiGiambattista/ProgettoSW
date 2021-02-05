package tesi;

import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListDocument {
    private ArrayList<Document> ndoc= new ArrayList<Document>();



    ListDocument(){

    }

   public ArrayList<Document> getNdoc (){
       ArrayList<String> files= new ArrayList<String>();
       files.add("xml/CollabHistoryPresenter.xml");
       files.add("xml/MaterialReferenceManager.xml");
       files.add("xml/TMP_Text.xml");

       for(String file  : files) {
           File inputFile = new File(file);
           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder;


           try {
               dBuilder = dbFactory.newDocumentBuilder();
               Document doc =  dBuilder.parse(inputFile);
               doc.getDocumentElement().normalize();
               ndoc.add(doc);

           } catch (ParserConfigurationException | IOException | SAXException ebs) {
               ebs.printStackTrace();
           }

       }

       return ndoc;
    }
    public ArrayList<Document> getNdocNOC (){
        ArrayList<String> files= new ArrayList<String>();
        files.add("xml/ClasseB.xml");
        files.add("xml/ClasseC.xml");

        for(String file  : files) {
            File inputFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;


            try {
                dBuilder = dbFactory.newDocumentBuilder();
                Document doc =  dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();
                ndoc.add(doc);

            } catch (ParserConfigurationException | IOException | SAXException ebs) {
                ebs.printStackTrace();
            }

        }

        return ndoc;
    }


public ArrayList<String> getListClass(){
    ArrayList<String> files= new ArrayList<String>();

    files.add("C#\\ClasseA.xml");
    files.add("C#\\ClasseB.xml");
    files.add("C#\\ClasseC.xml");
    File file = new File("C#/Person\\Person.xml");
    files.add(file.getAbsolutePath());
    return files;
    }

}
