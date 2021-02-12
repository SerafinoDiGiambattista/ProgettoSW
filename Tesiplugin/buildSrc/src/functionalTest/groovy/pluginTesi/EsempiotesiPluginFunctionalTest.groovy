/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package pluginTesi

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import org.gradle.testkit.runner.GradleRunner

import static org.gradle.testkit.runner.TaskOutcome.FAILED
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS


class EsempiotesiPluginFunctionalTest extends Specification {

    @Rule
    TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
        buildFile << """
            plugins {
              id('controllPlugin.prova')
            }
        """
    }
    
    

    def "testing configuration"() {
        given:
             File testFile = testProjectDir.newFile('config.properties') <<"""PRJs_RecallRisultatiNOC.txt = Assertion*,1000,f"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """
        
        
        
        
        
        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('prova')
                .withPluginClasspath()
                .build()


        then:
        result.task(":prova").outcome == SUCCESS
    }
}
