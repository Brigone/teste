package br.com.stoom.store.exceptions;

public class BrandDoesNotExitException extends Throwable {

    public BrandDoesNotExitException(){
        super("This brand is empty or does not exist!");
    }
    public BrandDoesNotExitException(String message){
        super(message);
    }

}
