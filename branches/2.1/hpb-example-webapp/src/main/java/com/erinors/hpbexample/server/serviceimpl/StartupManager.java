package com.erinors.hpbexample.server.serviceimpl;

public class StartupManager
{
    private DatabasePopulator databasePopulator;

    public void setDatabasePopulator(DatabasePopulator databasePopulator)
    {
        this.databasePopulator = databasePopulator;
    }

    public void initialize()
    {
        databasePopulator.populateDatabase();
    }
}
