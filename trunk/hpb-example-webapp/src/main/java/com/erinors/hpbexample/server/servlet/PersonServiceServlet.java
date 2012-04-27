package com.erinors.hpbexample.server.servlet;

import java.util.List;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.erinors.hpbexample.client.entity.Person;
import com.erinors.hpbexample.client.remote.PersonService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PersonServiceServlet extends RemoteServiceServlet implements PersonService
{
    private static final long serialVersionUID = 1L;

    private PersonService getPersonService()
    {
        return ((PersonService) WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext())
                .getBean("personService"));
    }

    @Override
    public List<Person> findPersons()
    {
        return getPersonService().findPersons();
    }

    @Override
    public Person savePerson(Person person)
    {
        return getPersonService().savePerson(person);
    }

    @Override
    public void deletePerson(Person person)
    {
        getPersonService().deletePerson(person);
    }
}
