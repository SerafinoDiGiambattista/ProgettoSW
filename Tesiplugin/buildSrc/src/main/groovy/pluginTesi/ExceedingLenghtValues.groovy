package pluginTesi;
import groovy.transform.CompileStatic

class ExceedingLenghtValues  extends Exception{
    ExceedingLenghtValues(String message){
        super(message)
    }
}
