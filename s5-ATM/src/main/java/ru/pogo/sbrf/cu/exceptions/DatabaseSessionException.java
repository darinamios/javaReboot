package ru.pogo.sbrf.cu.exceptions;

public class DatabaseSessionException extends ATMException {
  @Override
  public String getAtmExceptionMes(){
      return "DatabaseSessionException" + this.getMessage();
  }
}
