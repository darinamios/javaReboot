package ru.pogo.sbrf.cu.session;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.pogo.sbrf.cu.exceptions.DatabaseSessionException;

public class SessionManagerImpl  implements  SessionManager{
    private DatabaseSessionImpl databaseSession;
    private final SessionFactory sessionFactory;

    public SessionManagerImpl(SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            throw new DatabaseSessionException();
        }
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void beginSession() {
        try {
            databaseSession = new DatabaseSessionImpl(sessionFactory.openSession());
        } catch (Exception e) {
            throw new DatabaseSessionException();
        }
    }

    @Override
    public void commitSession() {
        checkSessionAndTransaction();
        try {
            databaseSession.getTransaction().commit();
            databaseSession.getHibernateSession().close();
        } catch (Exception e) {
            throw new DatabaseSessionException();
        }
    }

    @Override
    public void rollbackSession() {
        checkSessionAndTransaction();
        try {
            databaseSession.getTransaction().rollback();
            databaseSession.getHibernateSession().close();
        } catch (Exception e) {
            throw new DatabaseSessionException();
        }
    }

    @Override
    public void close() {
        if (databaseSession == null) {
            return;
        }
        Session session = databaseSession.getHibernateSession();
        if (session == null || !session.isConnected()) {
            return;
        }

        Transaction transaction = databaseSession.getTransaction();
        if (transaction == null || !transaction.isActive()) {
            return;
        }

        try {
            databaseSession.close();
            databaseSession = null;
        } catch (Exception e) {
            throw new DatabaseSessionException();
        }
    }

    @Override
    public DatabaseSessionImpl getCurrentSession() {
        checkSessionAndTransaction();
        return databaseSession;
    }

    private void checkSessionAndTransaction() {
        if (databaseSession == null) {
            throw new DatabaseSessionException();
        }
        Session session = databaseSession.getHibernateSession();
        if (session == null || !session.isConnected()) {
            throw new DatabaseSessionException();
        }

        Transaction transaction = databaseSession.getTransaction();
        if (transaction == null || !transaction.isActive()) {
            throw new DatabaseSessionException();
        }
    }
}
