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

    static String[] splitString(String valor, String lim) {
        String[]  subString

        if (valor.contains(lim)) {
            subString = valor.split(lim)
        }

        else {
            throw new IllegalArgumentException ('Non è presente il separatore')
        }

        if (subString.size() < 3 || subString.size() > 4) {
            throw new ThresholdExceededException ('Numero valori di config.properties non consentito')
        }

        subString
    }

    static int contaColonna(Vector<String> res, JTable table) {
        boolean pass = false
        int column = 0

        for (int i = 0; i < table.getColumnCount(); i++) {
            if (res.get(0) == table.getColumnName(i).toString()) {
                column = i
                pass = true
            }
        }

        if (!pass) {
            throw new ParameterInvalidException('Nome parametro inserito non valido')
        }
        column
    }

    static void  controlloTabella(int column, Vector<String> res, JTable table) {
        if (res.get(3) == 'f' || res.get(3) == 'w')  { }
        else {
            throw new IllegalArgumentException ('Inserire il carattere f oppure w')
        }

        String ricerca = null
        if (res.get(1).contains('*')) {
            ricerca = res.get(1).replace('*', '')
        }
        boolean pass = false

        for (int j = 0; j < table.getRowCount(); j++) {
            String nomeClasse = table.getModel().getValueAt(j, 0).toString()
            if (res.get(1) == '*' || res.get(1) == nomeClasse || (ricerca != null && nomeClasse.contains(ricerca)) ) {
                pass = true

                if ((Integer.parseInt(res.get(2)) < Integer.parseInt(table.getModel().getValueAt(j, column) as String))) {
                    if (res.get(3) == 'f') {
                        throw new ThresholdExceededException('La soglia del parametro ' + res.get(0) + ' è superata')
                    }

                    else if (res.get(3) == ('w')) {
                        WarningMessage war = new WarningMessage(j, 'WARNING:' + res.get(0) + ' Soglia classe superata: ' +
                                table.getModel().getValueAt(j, 0).toString(), null, null)
                        println war.getMessage()
                    }
                }
            }
        }

        if (!pass) {
            throw new InvalidClassException('Nome classe non valido')
        }
    }

    static void controlloTabellaNoc(Vector<String> res, JTable table) {
        if (res.get(2) == 'f' || res.get(2) == 'w')  { }
        else {
            throw new IllegalArgumentException('Inserire il carattere f oppure w')
        }

        String ricerca = null
        if (res.get(0).contains('*')) {
            ricerca = res.get(0).replace('*', '')
        }
        boolean pass = false

        for (int j = 0; j < table.getRowCount(); j++) {
            String nomeClasse = table.getModel().getValueAt(j, 0).toString()
            if (res.get(0) == '*' || res.get(0) == nomeClasse || (ricerca != null && nomeClasse.contains(ricerca))) {
                pass = true
                if (res.get(1).toInteger() < Integer.parseInt(table.getModel().getValueAt(j, 1) as String)) {
                    if (res.get(2) == 'f') {
                        throw new ThresholdExceededException ('Soglia parametro ' + res.get(0) + ' superata')
                    } else if (res.get(2) == 'w') {
                        WarningMessage war = new WarningMessage(j, 'WARNING:' + res.get(0) + ' Soglia classe superata: '
                                + table.getModel().getValueAt(j, 0).toString(), null, null)
                        println war.getMessage()
                    }
                }
            }
        }

        if (!pass) {
            throw new InvalidClassException('Nome classe non valido')
        }
    }

    @TaskAction
    void prova() {
        InputStream input = new FileInputStream(file2.get())
        Properties prop = new Properties()
        prop.load(input)
        String lim = ','
        String key = prop.keySet().iterator().next()
        String valor = prop.get(key).toString()
        Vector<String> res = splitString(valor, lim)
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
        int column = 0

        if (key.endsWith('Risultati.txt')) {
            column = contaColonna(res, table)
        }

        if (column != 0) {
            controlloTabella(column, res, table)
        }

        if (column == 0) {
            controlloTabellaNoc(res, table)
        }
    }

}
