import groovy.transform.CompileStatic

 
class ThresholdExceededException extends Exception{

    ThresholdExceededException(String message) {
        super(message)
    }

}
