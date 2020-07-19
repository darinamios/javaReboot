package ru.pogo.sbrf.cu.service;

import ru.pogo.sbrf.cu.models.ATM;

import java.util.Optional;

public interface DBServiceATM {
    long saveATM(ATM atm);
    Optional<ATM> getATM(long id);
}
