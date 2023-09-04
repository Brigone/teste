package br.com.stoom.store.exceptions;

public class ProductDoesNotExitException extends Throwable {

    public ProductDoesNotExitException(){
        super("This Product is empty or does not exist!");
    }

    public ProductDoesNotExitException(String message){
        super(message);
    }
}
