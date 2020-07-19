package ru.pogo.sbrf.cu.dao;

import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.session.SessionManager;


import java.util.Optional;

public interface ATMDao {
    Optional<ATM> findById(long id);

    long saveATM(ATM atm);

    SessionManager getSessionManager();
}
