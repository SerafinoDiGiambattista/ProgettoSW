package tesi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentiTest {
    private  static ArrayList<String> listf = new ArrayList<>();
    @BeforeAll
    public static void setUpBeforeClass(){

        Documenti doc = new Documenti();
        InputStream in = new ByteArrayInputStream("C#".getBytes());
        Scanner sc = new Scanner(in);
        listf=doc.listf("C#",listf, sc);


    }

@Test
void TestListf(){
        ListDocument list= new ListDocument();

        assertTrue(list.getListClass().equals(listf));
}

}
