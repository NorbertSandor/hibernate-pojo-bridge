package com.erinors.hpbexample.server.serviceimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.erinors.hpb.server.api.PersistentObjectManager;
import com.erinors.hpbexample.client.entity.Person;
import com.erinors.hpbexample.client.remote.PersonService;

public class PersonServiceImpl implements PersonService
{
    @PersistenceContext
    private EntityManager entityManager;

    private PersistentObjectManager persistentObjectManager;

    public void setPersistentObjectManager(PersistentObjectManager persistentObjectManager)
    {
        this.persistentObjectManager = persistentObjectManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Person> findPersons()
    {
        List<Person> persons = entityManager.createQuery("SELECT p FROM Person p").getResultList();
        return persistentObjectManager.clone(persons);
    }

    @Override
    @Transactional
    public Person savePerson(Person person)
    {
        Person merged = entityManager.merge(persistentObjectManager.merge(person));
        entityManager.flush();
        
        return persistentObjectManager.clone(merged);
    }

    @Override
    @Transactional
    public void deletePerson(Person person)
    {
        entityManager.remove(entityManager.getReference(Person.class, person.getId()));
    }
}
