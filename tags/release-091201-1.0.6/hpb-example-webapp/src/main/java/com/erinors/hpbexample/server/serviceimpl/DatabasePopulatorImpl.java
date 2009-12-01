package com.erinors.hpbexample.server.serviceimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.erinors.hpbexample.client.entity.Name;
import com.erinors.hpbexample.client.entity.Person;
import com.erinors.hpbexample.client.entity.PhoneNumber;

public class DatabasePopulatorImpl implements DatabasePopulator
{
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void populateDatabase()
    {
        Person johnSmith = new Person();
        johnSmith.setName(new Name("John", "Smith"));
        johnSmith.addPhoneNumber(new PhoneNumber("+36306274499"));
        entityManager.persist(johnSmith);

        Person eveJackson = new Person();
        eveJackson.setName(new Name("Eve", "Jackson"));
        eveJackson.addPhoneNumber(new PhoneNumber("+358485721956"));
        eveJackson.addPhoneNumber(new PhoneNumber("+36204811579"));
        entityManager.persist(eveJackson);
    }
}
