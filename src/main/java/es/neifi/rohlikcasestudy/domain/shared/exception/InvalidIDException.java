package es.neifi.rohlikcasestudy.domain.shared.exception;

public class InvalidIDException extends IllegalArgumentException{
    public InvalidIDException(String id) {
        super("The given id is not valid uuid: "+id);
    }
}
