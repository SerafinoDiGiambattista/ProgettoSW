package tesi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DitTest {
        private static Dit dit ;
        private static HashMap<String,Integer> mappa= new HashMap<String,Integer>(); ;

        @BeforeAll
        static void setUpBeforeClass() {
                dit = new Dit(new ListDocument().getNdocNOC());
                dit.calcolo();

                mappa.put("B",1);
                mappa.put("C",2);
        }

        @Test
        void testListaNomi(){
                assertTrue(mappa.equals(dit.getMap()));
        }
}
