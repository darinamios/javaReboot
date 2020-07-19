package ru.pogo.sbrf.cu.session;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DatabaseSessionImpl implements DatabaseSession {
    private final Session session;
    private final Transaction transaction;

    DatabaseSessionImpl(Session session) {
        this.session = session;
        this.transaction = session.beginTransaction();
    }

    public Session getHibernateSession() {
        return session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void close() {
        if (transaction.isActive()) {
            transaction.commit();
        }
        session.close();
    }
}
