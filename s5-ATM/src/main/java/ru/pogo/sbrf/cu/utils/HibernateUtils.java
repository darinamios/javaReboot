package ru.pogo.sbrf.cu.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.pogo.sbrf.cu.models.ATMImpl;
import ru.pogo.sbrf.cu.models.CassetteImpl;

public class HibernateUtils {
    private final String URL = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";
    private final SessionFactory sessionFactory;
    public HibernateUtils(){
        Configuration configuration = new Configuration()
                .setProperty( "hibernate.dialect", "org.hibernate.dialect.H2Dialect" )
                .setProperty( "hibernate.connection.driver_class", "org.h2.Driver" )
                .setProperty( "hibernate.connection.url", URL )
                .setProperty( "hibernate.show_sql", "true" )
                .setProperty( "hibernate.hbm2ddl.auto", "create" )
                .setProperty( "hibernate.generate_statistics", "true" );

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings( configuration.getProperties() ).build();

        Metadata metadata = new MetadataSources( serviceRegistry )
                .addAnnotatedClass( ATMImpl.class )
                .addAnnotatedClass( CassetteImpl.class )
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }
}
