import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import javax.swing.JTable
import org.codehaus.groovy.control.messages.WarningMessage

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

      //trovo il numero della colonna da considerare metterlo in metodo
      int column =0
       boolean pass = false
      for(int i=0; i<table.getColumnCount(); i++){
         if(res.get(0).equals(table.getColumnName(i).toString())){
            column=i
             pass=true

         }
      }
       //tutto il for metterlo in un metodo?
       //if(!pass) ricorda significa che pass è false
       //era possibile fare in unico for mettendo un altro if
       //NOTA possiamo mettere una failure nel caso in cui il nome della classe\package non è valido (forse basta mettere un altro if contente la failure???)
          for(int j=0; j<table.getRowCount(); j++) {
              if (res.get(1).equals('*') || res.get(1).equals( table.getModel().getValueAt(j, 0).toString())){
                  if (Integer.parseInt(res.get(2)) < Integer.parseInt(table.getModel().getValueAt(j, column))) {
                      if (res.get(3).equals('f')) {
                          throw new ThresholdExceededException("La soglia del parametro " + res.get(0) + " e stata superata");
                      } else if (res.get(3).equals('w')) {
                          //idea priorittizare rispetto in base alla differenza tra la soglia impostato e della classe considerata ma il problema è che si deve fare per ogni parametro ;-(
                          def war = new WarningMessage(j, 'Warning:' + res.get(0) + ' Soglia superata della classe : ' + table.getModel().getValueAt(j, 0).toString(), null, null)
                          println war.getMessage()
                      }
                  }

          }

      }





}
   }

