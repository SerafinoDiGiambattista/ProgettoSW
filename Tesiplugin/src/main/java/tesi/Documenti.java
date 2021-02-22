package tesi;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Documenti {
private String os = System.getProperty("os.name");
	public Documenti() {

	}

	public  ArrayList<String> listf(String directoryName, ArrayList<String> files, Scanner scan) {
		ProcessBuilder processBuilder= new ProcessBuilder();

		File directory = new File(directoryName);

		while(!directory.isDirectory()){
			System.out.println("\nPercorso File non valido"+"\nInserisci una directory valida ");
			directoryName= scan.nextLine();
			directory = new File(directoryName);
		}

		File[] fList = directory.listFiles();

		for (File file : fList) {
			if (file.isFile()) {
				if(  file.getName().endsWith(".cs")) {
					String name = file.getName().replace("cs", "xml");

					if(os.equals("Linux")){
					processBuilder.command( "srcml_1.0.0-1_ubuntu20.04/bin/srcml", file.getAbsolutePath(), "-o", file.getParent()+"/"+name);
		          }
		          if(os.equals("Windows")){
		          processBuilder.command( "srcML-Win-64\\srcML-Win\\bin\\srcml", file.getAbsolutePath(), "-o", file.getParent()+"\\"+name);
		          
		          }
					try {
						java.lang.Process process =	processBuilder.start();
						InputStream inputStream = process.getInputStream();
						int i;
						StringBuilder xmlData = new StringBuilder();

						while ((i = inputStream.read()) != -1){
							xmlData.append((char) i);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
						
					if(os.equals("Linux")){
					files.add( file.getParent()+"/"+name);
					}
					if(os.equals("Windows")){
					files.add( file.getParent()+"\\"+name);
					}
				}

			}else if (file.isDirectory()) {
				listf(file.getAbsolutePath(), files,scan);
			}
		}

		return files;
	}
}
