package exceptions;

public class SystemExceptions extends Exception{
    public SystemExceptions(String message){
        super(message);
    }
    public SystemExceptions(Throwable throwable){
        super(throwable);
    }
}
