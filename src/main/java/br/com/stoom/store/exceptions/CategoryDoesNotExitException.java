package br.com.stoom.store.exceptions;

public class CategoryDoesNotExitException extends Throwable {

    public CategoryDoesNotExitException(){
        super("This category is empty or does not exist!");
    }
    public CategoryDoesNotExitException(String message){
        super(message);
    }

}
