package ru.pogo.sbrf.cu.session;

public interface SessionManager extends AutoCloseable {
    void beginSession();
    void commitSession();
    void rollbackSession();
    void close();

    DatabaseSession getCurrentSession();
}
