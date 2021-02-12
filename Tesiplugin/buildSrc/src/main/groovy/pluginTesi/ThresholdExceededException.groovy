package pluginTesi

import groovy.transform.CompileStatic

@CompileStatic
class ThresholdExceededException extends Exception{

    ThresholdExceededException(String message) {
        super(message)
    }

}
