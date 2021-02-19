package pluginTesi
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import javax.swing.JTable
import org.codehaus.groovy.control.messages.WarningMessage

class ControllTask extends DefaultTask {
@InputFile
final Property<File> file2 = project.objects.property(File)



    /*
     COSA DA MIGLIORARE
     mettere le varie eccezioni quando il formato del file non è rispettato
	 COSA SUCCEDE SE LA TABELLA VUOTA???
     ipotetico vedere se il formato file di configurazione accetta spazi vuoti
     */


    // queso metodo mi permette di prendere le varie sottostringhe da una stringa
    static String[] splitString(String valor, String lim){

       String[]  sub_string

       if(valor.contains(lim)) {
         sub_string = valor.split(lim)
        }
       else{
        throw new IllegalArgumentException("Non è presente il separatore")
    }

    if(sub_string.size()<3 || sub_string.size()>4) throw new ThresholdExceededException("Numero valori di config.properties non consentito")

       sub_string

   }

    static int ContaColonna(Vector<String> res, JTable table){
    boolean pass = false
    int column =0
    for (int i = 0; i < table.getColumnCount(); i++) {
        if (res.get(0) == table.getColumnName(i).toString()) {
            column = i
            pass = true

        }
    }
    if(!pass) throw new ParameterInvalidException("Nome parametro inserito non valido")

    column
}

    static void  ControlloTabella(int column, Vector<String> res, JTable table){

      if(res.get(3)=='f' || res.get(3)=='w')  {}
        else{throw new IllegalArgumentException("Inserire il carattere f oppure w")}



        String ricerca=null
        if(res.get(1).contains('*'))  ricerca=res.get(1).replace('*','')

        boolean pass = false

      for(int j=0; j<table.getRowCount(); j++) {
          String nome_classe = table.getModel().getValueAt(j, 0).toString()
          if (res.get(1) == '*' || res.get(1) == nome_classe || (ricerca!=null && nome_classe.contains(ricerca)) ){
              pass=true
              if ((Integer.parseInt(res.get(2)) < Integer.parseInt(table.getModel().getValueAt(j, column) as String))) {
                  if (res.get(3) == 'f') {
                      throw new ThresholdExceededException("La soglia del parametro " + res.get(0) + " e stata superata")
                  }
                 else if (res.get(3)==('w')) {
                      //idea priorittizare rispetto in base alla differenza tra la soglia impostato e della classe considerata ma il problema è che si deve fare per ogni parametro ;-(
                      WarningMessage war = new WarningMessage(j, 'WARNING:' + res.get(0) + ' Soglia superata della classe : ' + table.getModel().getValueAt(j, 0).toString(), null, null)
                      println war.getMessage()
                  }

              }

          }

      }
        if(!pass) throw new InvalidClassException("Nome classe non valido")
  }

    static void ControlloTabellaNoc(Vector<String> res, JTable table){

        if(res.get(2)=='f' || res.get(2)=='w')  {}
        else{throw new IllegalArgumentException("Inserire il carattere f oppure w")}

        String ricerca=null
        if(res.get(0).contains("*"))  ricerca=res.get(0).replace('*','')

        boolean pass=false

        for(int j=0; j<table.getRowCount(); j++) {
            String nome_classe = table.getModel().getValueAt(j, 0).toString()
            if(res.get(0) == '*' || res.get(0) == nome_classe || (ricerca!=null && nome_classe.contains(ricerca))){
                pass=true
                if(res.get(1).toInteger()<Integer.parseInt(table.getModel().getValueAt(j,1) as String)){
                    if (res.get(2) == 'f') {
                        throw new ThresholdExceededException("La soglia del parametro " + res.get(0) + " e stata superata");
                    } else if (res.get(2) == 'w') {
                        //idea priorittizare rispetto in base alla differenza tra la soglia impostato e della classe considerata ma il problema è che si deve fare per ogni parametro ;-(
                        WarningMessage war = new WarningMessage(j, 'WARNING:' + res.get(0) + ' Soglia superata della classe : ' + table.getModel().getValueAt(j, 0).toString(), null, null)
                        println war.getMessage()
                    }
                }
            }
        }

        if(!pass) throw new InvalidClassException("Nome classe non valido")
    }

   @TaskAction
/*
la lettura del file di configurazione non accetta spazi vuoi.
Dobbiamo rivedere la sua lettura oppure no?
 */
void prova() {

   //aggiungere try catch per pulizia e corretezza programma
   InputStream input = new FileInputStream(file2.get())
   Properties prop = new Properties()
   prop.load(input)
   String lim = ","
String key =prop.keySet().iterator().next()
String valor = prop.get(key).toString()
      //con i limitatori prendo i dati che mi interessano
       Vector<String> res = splitString(valor, lim);
      // leggo il file contente la tabella
       JTable table

   //   try {
         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(key)))
         table = (JTable) ois.readObject()

         ois.close()
     // }
      //catch(Exception e) {
       // e.print("Nome file non valido o inesistente")

      //}

      //trovo il numero della colonna da considerare
      int column =0

       if(key.endsWith("Risultati.txt")) {
           column = ContaColonna(res, table)

       }



    if (column != 0) ControlloTabella(column, res, table)
    if (column == 0) ControlloTabellaNoc(res, table)











}
   }
