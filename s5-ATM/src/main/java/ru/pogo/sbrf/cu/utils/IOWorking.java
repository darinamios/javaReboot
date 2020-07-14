package ru.pogo.sbrf.cu.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.pogo.sbrf.cu.atm.ATMImpl;

import java.io.File;
import java.io.IOException;

public class IOWorking {
    ObjectMapper mapper;

    public IOWorking(){
        mapper = new ObjectMapper();
    }

    public void writeToFile(ATMImpl atm, String filename){
        try{
            mapper.writeValue(new File(filename), atm);}
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public ATMImpl readFromFile(String filename) {
        try{
            return mapper.readValue(new File(filename), ATMImpl.class);
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            return new ATMImpl();
        }
    }
}
