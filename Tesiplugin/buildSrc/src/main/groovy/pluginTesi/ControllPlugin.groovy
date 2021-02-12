package pluginTesi
import org.gradle.api.Plugin
import org.gradle.api.Project




class ControllPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('prova', ControllExtension)

        project.tasks.create('prova', ControllTask) {

            file2 = project.prova.file2
        }

    }
}