package tesi;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainTest{

@Test
public void testMain() throws IOException {
System.out.println("main");
String[] args = null;
final InputStream original = System.in;
final FileInputStream fips = new FileInputStream(new File("src/test/java/tesi/Input.txt"));
System.setIn(fips);


Process.main(args);
System.setIn(original);

}

}
