package ru.pogo.sbrf.cu.exceptions;

public class DBServiceException extends ATMException {
    @Override
    public String getAtmExceptionMes(){
        return "Exception in db service: " + this.getMessage();
    }
}
