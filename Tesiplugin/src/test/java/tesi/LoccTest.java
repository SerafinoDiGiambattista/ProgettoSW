package tesi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoccTest {

    private static Locc locc ;
    private static HashMap<String,Integer> mappa = new HashMap<String,Integer>();

    @BeforeAll
    static void setUpBeforeClass() throws Exception{


        locc= new Locc(new ListDocument().getNdoc());
        locc.calcolo();

        mappa.put("CollabHistoryPresenter",150);
        mappa.put("MaterialReferenceManager",105);
        mappa.put("TMP_Text",4008);


    }


    @Test
    void TestCalcoloLocc(){
        assertTrue(mappa.equals(locc.getMappa()));
    }


}
