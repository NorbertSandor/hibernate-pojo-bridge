package com.erinors.hpbexample.client;

import java.util.List;

import com.erinors.hpbexample.client.entity.Person;
import com.erinors.hpbexample.client.remote.PersonService;
import com.erinors.hpbexample.client.remote.PersonServiceAsync;
import com.erinors.hpbexample.client.ui.PersonListPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ExampleWebapp implements EntryPoint
{
    public static PersonServiceAsync personService;

    @Override
    public void onModuleLoad()
    {
        personService = GWT.create(PersonService.class);
        ((ServiceDefTarget) personService).setServiceEntryPoint(GWT.getHostPageBaseURL() + "/servlets/PersonService");
        
        showPersonList();
    }

    public static void show(Widget widget)
    {
        RootPanel.get().clear();
        RootPanel.get().add(widget);
    }

    public static void showPersonList()
    {
        personService.findPersons(new AsyncCallback<List<Person>>()
        {
            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert("Cannot load persons!");
            }

            @Override
            public void onSuccess(List<Person> result)
            {
                show(new PersonListPanel(result));
            }
        });
    }
}
