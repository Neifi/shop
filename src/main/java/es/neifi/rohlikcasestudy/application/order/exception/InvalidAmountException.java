package es.neifi.rohlikcasestudy.application.order.exception;

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException(double amount) {
        super("The given amount is invalid: "+amount);
    }
}
