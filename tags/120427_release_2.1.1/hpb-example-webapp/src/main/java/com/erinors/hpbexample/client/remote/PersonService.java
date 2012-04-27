package com.erinors.hpbexample.client.remote;

import java.util.List;

import com.erinors.hpbexample.client.entity.Person;
import com.google.gwt.user.client.rpc.RemoteService;

public interface PersonService extends RemoteService
{
    List<Person> findPersons();

    Person savePerson(Person person);

    void deletePerson(Person person);
}
