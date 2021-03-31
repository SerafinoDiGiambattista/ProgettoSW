package pluginTesi


import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import org.gradle.testkit.runner.GradleRunner
import java.nio.file.Files
import java.nio.file.Paths

import static org.gradle.testkit.runner.TaskOutcome.FAILED
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS


class FunctionalTest1 extends Specification {



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

    def "testing configuration NUMBER 1 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultati.txt=LOCC,ControlMapper,0,f"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
         thrown org.gradle.testkit.runner.UnexpectedBuildFailure
    }

       def "testing configuration NUMBER 2 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultatiNOC.txt=UnityEvent<string>,0,f"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
         thrown org.gradle.testkit.runner.UnexpectedBuildFailure
    }
    
   //questo metodo prende l'eccezione nessuna classe trovata nella Tabella normale
        def "testing configuration NUMBER 3 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultati.txt=NOC,EEE,0,f"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
    
        thrown org.gradle.testkit.runner.UnexpectedBuildFailure
    }
         def "testing configuration NUMBER 4 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultatiNOC.txt=EEE,0,f"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
    
        thrown org.gradle.testkit.runner.UnexpectedBuildFailure
    }
        def "testing configuration NUMBER 5 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultati.txt=CBO,ControlMapper,0,w"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
         result.output.contains("WARNING:Valore del parametroCBO superato dalla classe : ControlMapper")
    }
    
    def "testing configuration NUMBER 6 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultatiNOC.txt=UnityEvent<string>,0,w"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
        result.output.contains("WARNING: Valore del parametro NOC superato dalla classe: UnityEvent<string>")
    }
    
        def "testing configuration NUMBER 7 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultati.txt=LOCCUnityEvent<string>0w"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
       thrown org.gradle.testkit.runner.UnexpectedBuildFailure
    }
    
        
  def "testing configuration NUMBER 8 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultati.txt=CBO,ControlMapper,0,fdsd"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
         thrown org.gradle.testkit.runner.UnexpectedBuildFailure
    }
    def "testing configuration NUMBER 9 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultati.txt=sadasd,ControlMapper,0,f"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
         thrown org.gradle.testkit.runner.UnexpectedBuildFailure
    }
    
        def "testing configuration NUMBER 10 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultati.txt=WMC,ControlMapper,0,f,ll,ll"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
         thrown org.gradle.testkit.runner.UnexpectedBuildFailure
    }
        
        def "testing configuration NUMBER 13 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultatiNOC.txt=UnityEvent<string>,0,fdsd"""

        buildFile  <<"""
        prova{
            file2 = file('${testFile.getName()}')
      
        }
        """

        when:

        def result = gradleRunner.withArguments('prova').build()
        then:
         thrown org.gradle.testkit.runner.UnexpectedBuildFailure
    }
        def "testing configuration NUMBER 11 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultati.txt=RFC,*,0,w"""

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
            def "testing configuration NUMBER 12 "() {
        given:
        File testFile = testProjectDir.newFile('config.properties')<<"""TabellaRisultatiNOC.txt=*,0,w"""

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
