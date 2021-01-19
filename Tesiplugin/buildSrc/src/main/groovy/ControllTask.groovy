import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTable

class ControllTask extends DefaultTask{
@InputFile
final Property<File> file2 = project.objects.property(File)

   static Vector<String> splitString(String valor, char lim){
   /*
   queso metodo mi permette di prendere le varie sottostringhe da una stringa
    */
      String word = ""

      Vector<String> sub_string = new Vector<String>()
      for (int i =0; i<valor.length(); i++){
         if(valor.charAt(i)!=lim){
            word = word +valor.charAt(i)
         }
         else
         {
            if((int)word.length()!=0)
            {
               sub_string.add(word)
            }
            word = ""
         }
      }
      return sub_string

   }



   @TaskAction

def prova() {
   //aggiungere try catch per pulizia e corretezza programma
   InputStream input = new FileInputStream(file2.get())
   Properties prop = new Properties()
   prop.load(input)
   char lim = ','
def key =prop.keySet().iterator().next()
String valor = prop.get(key)+lim
      //con i limitatori prendo i dati che mi interessano
      Vector<String> res = splitString(valor, lim);
      // leggo il file contente la tabella
JTable table

      try {
         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(key)));
         table = (JTable) ois.readObject();
         ois.close();
      }
      catch(Exception e) {
         System.out.println("Problem reading back table from file: " );
         return;
      }

      //trovo il numero della colonna da considerare
      int column =0
      for(int i=0; i<table.getColumnCount(); i++){
         if(res.get(0).equals(table.getColumnName(i).toString())){
            column=i
         }
      }
      //nome della classe e in questo caso considero tutte le classi
      if(res.get(1).equals('*')){



          for(int j=0; j<table.getRowCount(); j++){

         if(Integer.parseInt(res.get(2))<Integer.parseInt(table.getModel().getValueAt(j,column))){
             if(res.get(3).equals('f')){
               throw new ThresholdExceededException("La soglia del parametro "+res.get(0)+" e stata superata");
            }
         }



      }




      }
}
   }

