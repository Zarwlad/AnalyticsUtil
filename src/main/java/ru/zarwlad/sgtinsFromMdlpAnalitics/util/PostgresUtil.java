package ru.zarwlad.sgtinsFromMdlpAnalitics.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel.Sgtin;
import ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel.Sscc;

import java.util.HashMap;
import java.util.Map;

public class PostgresUtil {
    private static SessionFactory sessionFactory;
    private static PostgresUtil postgresUtil;

    private PostgresUtil(){}

    public static PostgresUtil getInstance() {
        if (postgresUtil == null){
            postgresUtil = new PostgresUtil();
            postgresUtil.createSessionFactory();
        }
        return postgresUtil;
    }

    private void createSessionFactory(){
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

        Map<String, String> settings = new HashMap<>();

        serviceRegistryBuilder
                .applySettings(settings);

        StandardServiceRegistry registry = serviceRegistryBuilder.build();
        MetadataSources sources = new MetadataSources(registry);
        sources.addAnnotatedClass(Sgtin.class);
        sources.addAnnotatedClass(Sscc.class);

        Metadata metadata = sources.getMetadataBuilder().build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
