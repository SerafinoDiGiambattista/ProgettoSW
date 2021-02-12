package pluginTesi
import groovy.transform.CompileStatic
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

@CompileStatic
class ControllExtension {

    final Property<File> file2

    @Inject
    ControllExtension(ObjectFactory objects) {
        file2 = objects.property(File)
    }

}
