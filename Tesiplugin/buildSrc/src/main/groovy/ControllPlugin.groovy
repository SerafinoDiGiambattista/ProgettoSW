import org.gradle.api.Plugin
import org.gradle.api.Project




class ControllPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def ext = project.extensions.create('estensione', ControllExtension)

        project.tasks.create('prova', ControllTask) {
            group = 'Publishing'
            file2 = ext.file1
        }

    }
}