package tesi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CboTest {
    private static Cbo cbo;
    private static HashMap<String,Integer> mappa = new HashMap<String,Integer>();

    @BeforeAll
    public static void setUpBeforeClass(){
        cbo= new Cbo(new ListDocument().getNdoc());
        cbo.calcolo();

        mappa.put("CollabHistoryPresenter",32);
        mappa.put("MaterialReferenceManager",46);
        mappa.put("TMP_Text",325);

    }

    @Test
    void TestCalcoloWmc(){
        assertTrue(mappa.equals(cbo.getMappa()));
    }

}
