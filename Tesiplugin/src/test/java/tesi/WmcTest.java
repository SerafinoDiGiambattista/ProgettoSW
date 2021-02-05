package tesi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WmcTest {
private static Wmc wmc;
private static HashMap<String,Integer> mappa = new HashMap<String,Integer>();

    @BeforeAll
    static void setUpBeforeClass() {

        wmc=new Wmc(new ListDocument().getNdoc());
        wmc.calcolo();

        mappa.put("CollabHistoryPresenter",22);
        mappa.put("MaterialReferenceManager",13);
        mappa.put("TMP_Text",1078);

    }
    @Test
    void TestCalcoloWmc(){
        assertTrue(mappa.equals(wmc.getMappa()));
    }




}
