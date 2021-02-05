package tesi;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.*;

public class NocTest {

    private static Noc nx ;
    private static HashMap<String,Integer> mappa= new HashMap<String,Integer>(); ;

    @BeforeAll
    static void setUpBeforeClass() {

        nx = new Noc(new ListDocument().getNdocNOC());
        mappa.put("A",1);
        mappa.put("B",1);

        nx.listaNomi();



    }


@Test
    void TestListaNomi(){
      assertTrue(mappa.equals(nx.getMappa()));
}




}

