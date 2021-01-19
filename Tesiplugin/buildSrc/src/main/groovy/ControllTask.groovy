import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction


import javax.swing.JTable

class ControllTask extends DefaultTask{
@InputFile
final Property<File> file2 = project.objects.property(File)

@TaskAction

def prova() {

}
   }

