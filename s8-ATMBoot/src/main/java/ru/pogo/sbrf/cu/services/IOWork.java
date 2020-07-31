package ru.pogo.sbrf.cu.services;

import ru.pogo.sbrf.cu.models.ATM;

public interface IOWork {
    void writeToFile(ATM atm, String filename);
    ATM readFromFile(String filename);
}
