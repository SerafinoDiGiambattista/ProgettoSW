package pluginTesi

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import org.gradle.testkit.runner.GradleRunner
import java.nio.file.Files
import java.nio.file.Paths

import static org.gradle.testkit.runner.TaskOutcome.FAILED
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS


class EsempiotesiPluginFunctionalTest extends Specification {



    @Rule
    TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile
    private GradleRunner gradleRunner

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
        buildFile << """
            plugins {
              id('controllPlugin.prova')
            }
        """
        gradleRunner = GradleRunner.create()
                .withPluginClasspath()
                .withProjectDir(testProjectDir.root)
                .withTestKitDir(testProjectDir.newFolder())

        Files.copy(
                Paths.get("./build/testkit/testkit-gradle.properties"),
                new File(gradleRunner.projectDir, "gradle.properties").toPath()
        )

    }



    def "testing configuration NUMBER ONE "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""PRJs_RecallRisultatiNOC.txt=Assertion*,1000,f"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
        result.task(":prova").outcome == SUCCESS
    }



 }
