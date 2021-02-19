package tesi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LcomTest {
        private static Lcom lcom;
        private static HashMap<String,Float> mappa = new HashMap<String,Float>();

        @BeforeAll
        public static void setUpBeforeClass(){
                lcom= new Lcom(new ListDocument().getNdoc());
                lcom.calcolo();

                mappa.put("CollabHistoryPresenter", (float) 0.8472222);
                mappa.put("MaterialReferenceManager",(float)0.9);
                mappa.put("TMP_Text",(float)0.99272424);
        }

        @Test
        void testCalcoloLcom(){
                assertTrue(mappa.equals(lcom.getMappa()));
        }

}
