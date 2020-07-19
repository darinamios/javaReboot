package ru.pogo.sbrf.cu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pogo.sbrf.cu.dao.ATMDao;
import ru.pogo.sbrf.cu.exceptions.DBServiceException;
import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.session.SessionManager;

import java.util.Optional;

public class DBServiceATMImpl implements DBServiceATM {
    private static Logger logger = LoggerFactory.getLogger(DBServiceATMImpl.class);

    private final ATMDao atmDao;

    public DBServiceATMImpl(ATMDao atmDao) {
        this.atmDao = atmDao;
    }

    @Override
    public long saveATM(ATM atm) {
        try (SessionManager sessionManager = atmDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long atmId = atmDao.saveATM(atm);
                sessionManager.commitSession();
                logger.info("created atm: {}", atmId);
                return atmId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DBServiceException();
            }
        }
    }

    @Override
    public Optional<ATM> getATM(long id) {
        try (SessionManager sessionManager = atmDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<ATM> atmOptional = atmDao.findById(id);
                logger.info("atm: {}", atmOptional.orElse(null));
                logger.info("atm services: {}", atmOptional.get().getCassettes().size());
                return atmOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
