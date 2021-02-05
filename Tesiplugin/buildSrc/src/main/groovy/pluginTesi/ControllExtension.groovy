package pluginTesi;
import groovy.transform.CompileStatic
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject


class ControllExtension {

    final Property<File> file1

    @Inject
    ControllExtension(ObjectFactory objects) {
        file1 = objects.property(File)
    }

}
