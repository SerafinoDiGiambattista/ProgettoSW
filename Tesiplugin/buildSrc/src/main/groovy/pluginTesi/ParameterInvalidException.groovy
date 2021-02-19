package pluginTesi

import groovy.transform.CompileStatic

@CompileStatic
class ParameterInvalidException extends Exception {

    ParameterInvalidException(String message) {
        super(message)
    }

}
