package pluginTesi;
import groovy.transform.CompileStatic


class ParameterInvalidException extends Exception{
    ParameterInvalidException(String message){
        super(message)
    }

}
