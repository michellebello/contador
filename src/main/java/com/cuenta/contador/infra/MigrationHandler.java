package com.cuenta.contador.infra;

import org.flywaydb.core.Flyway;

public class MigrationHandler {
    public void migrate() {
        System.out.println("Migrating...");
        DBCredentials credentials = DBCredentials.fromDefaultFile();
        Flyway flyway = Flyway.configure().dataSource(credentials.getUrl(), credentials.getUser(), credentials.getPassword()).load();
        flyway.migrate();
    }
}
