package com.erinors.hpbexample.client.remote;

import java.util.List;

import com.erinors.hpbexample.client.entity.Person;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface PersonServiceAsync extends RemoteService
{
    void findPersons(AsyncCallback<List<Person>> callback);

    void savePerson(Person person, AsyncCallback<Person> callback);

    void deletePerson(Person person, AsyncCallback<Void> asyncCallback);
}
