package pluginTesi


import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.FAILED

class FileDiffPluginFunctionalTest  extends Specification{
    @Rule
    TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildfile

    def setup() {
        buildfile = testProjectDir.newFile('build.gradle')
        buildfile << """
            plugin{id ControllPlugin}
                        """

    }

    def "proviamo cosa  succede"() {
        given:
        File testFile1 = testProjectDir.newFile('configpro/conftest.properties')

        buildfile << """
         
    estensione {
        file1 = file('${testFile1.getName()}')
    }

        """
        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('prova')
                .withPluginClasspath()
                .build()
        then:
        result.tasks(":prova").outcome == FAILED

    }

}