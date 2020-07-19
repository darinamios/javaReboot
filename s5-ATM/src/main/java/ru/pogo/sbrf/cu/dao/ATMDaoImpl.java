package ru.pogo.sbrf.cu.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pogo.sbrf.cu.exceptions.ATMDaoException;
import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.models.ATMImpl;
import ru.pogo.sbrf.cu.session.DatabaseSessionImpl;
import ru.pogo.sbrf.cu.session.SessionManager;
import ru.pogo.sbrf.cu.session.SessionManagerImpl;

import java.util.Optional;

public class ATMDaoImpl implements ATMDao {
    private static Logger logger = LoggerFactory.getLogger(ATMDaoImpl.class);
    private final SessionManagerImpl sessionManager;

    public ATMDaoImpl(SessionManagerImpl sessionManager){
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<ATM> findById(long id) {
        DatabaseSessionImpl currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().load(ATMImpl.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveATM(ATM atm) {
        DatabaseSessionImpl currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (atm.getId() > 0) {
                logger.info("call merge");
                hibernateSession.merge(atm);
            } else {
                logger.info("call persist");
                hibernateSession.persist(atm);
            }
            return atm.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ATMDaoException();
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
