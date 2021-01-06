import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.Scanner;
import org.gradle.internal.impldep.org.eclipse.jgit.storage.pack.PackStatistics

import javax.swing.*

class ControllPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def ext = project.extensions.create('estensione', ControllExtension)

        project.task('prova') {
            group= 'Prova'
            /*
            ext.file1 = new File('C:\\Users\\Serafino\\Documents\\Esercizio\\progetto\\Tesiplugin\\C#Risultati.txt')
            ext.file2 = new File('C:\\Users\\Serafino\\Documents\\Esercizio\\progetto\\Tesiplugin\\C#RisultatiNOC.txt')
*/
            JTable table1

            println 'inserisci il percorso file da leggere '
      String name = System.in.newReader().readLine()

            try{
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name))
                table1= (JTable) ois.readObject()
                ois.close()
            }
            catch(Exception e){
                println "Problem reading back table from file: "
            }
/*
        JFrame frame1 = new JFrame()

        frame1.add(new JScrollPane(table1))
        frame1.setSize(300,400)
            frame1.setVisible(true);
            */

def data = new ArrayList<String>()
            for (int row = 0; row < table1.getRowCount(); row++){
                for (int column = 0; column < table1.getColumnCount(); column++){
                    data.add(table1.getValueAt(row, column).toString());
                }
                // Probably add new line to 'data'
            }
print data.toString()
        }


    }
}