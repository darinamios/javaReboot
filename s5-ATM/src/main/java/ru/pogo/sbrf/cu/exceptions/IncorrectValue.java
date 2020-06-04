package ru.pogo.sbrf.cu.exceptions;

public class IncorrectValue extends ATMException {
    @Override
    public String getAtmExceptionMes(){
        return "You try to set incorrect value";
    }
}
