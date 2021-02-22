package pluginTesi

import groovy.transform.CompileStatic

@CompileStatic
class ExceedingLenghtValues  extends Exception {

    ExceedingLenghtValues(String message) {
        super(message)
    }

}
