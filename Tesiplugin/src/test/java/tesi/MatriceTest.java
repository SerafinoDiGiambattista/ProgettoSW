package tesi;


import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MatriceTest {

    private ArrayList<Document> ndoc = new ListDocument().getNdoc();

    @Test
    public void TestCreazione(){
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


        Matrice ce = new Matrice("SistemaTest",ndoc,wmc, rfc, noc, loc, lcom, dit, cbo);
        ce.creazione();
        InputStream in = new ByteArrayInputStream("Chiudi".getBytes());
        Scanner sc = new Scanner(in);
        ce.lettura("SistemaTest",sc);
    }

}
