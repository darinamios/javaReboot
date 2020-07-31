package ru.pogo.sbrf.cu.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.models.ATMImpl;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class IOWorkImpl implements IOWork {

    private final ObjectMapper mapper;

    public void writeToFile(ATM atm, String filename){
        try{
            mapper.writeValue(new File(filename), atm);}
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public ATM readFromFile(String filename) {
        try{
            return mapper.readValue(new File(filename), ATMImpl.class);
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
