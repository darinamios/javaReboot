package ru.pogo.sbrf.cu.exceptions;

public class NotAvailableRequestCount extends ATMException {
    @Override
    public String getAtmExceptionMes(){
        return "No enough money in ATM";
    }
}
