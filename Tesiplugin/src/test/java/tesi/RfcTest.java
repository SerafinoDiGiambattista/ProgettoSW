package tesi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RfcTest {
    private  static Rfc rfc;
    private static HashMap<String, Integer> mappa = new HashMap<String,Integer>(); ;

    @BeforeAll
    static void setUpBeforeClass(){


        rfc = new Rfc(new ListDocument().getNdoc());
        rfc.calcolo();


        mappa.put("CollabHistoryPresenter",54);
        mappa.put("MaterialReferenceManager",53);
        mappa.put("TMP_Text",1130);
    }

    @Test
    void TestCalcoloRfc(){
    assertTrue(mappa.equals(rfc.getMap()));
    }
}
