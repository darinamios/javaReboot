package ru.pogo.sbrf.cu.exceptions;

public class NoSuchNominal extends ATMException {
    @Override
    public String getAtmExceptionMes(){
        return "ATM hasn't such nominal";
    }
}
