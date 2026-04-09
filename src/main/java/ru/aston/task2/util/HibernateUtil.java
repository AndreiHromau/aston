package ru.aston.task2.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY = build();

    private static SessionFactory build() {
        try {
            String configFile = System.getProperty("test.config.file", "hibernate.cfg.xml");
            return new Configuration().configure(configFile).buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Критическая ошибка инициализации БД: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}