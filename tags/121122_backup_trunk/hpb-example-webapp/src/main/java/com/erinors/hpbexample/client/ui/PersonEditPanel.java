package com.erinors.hpbexample.client.ui;

import com.erinors.hpbexample.client.ExampleWebapp;
import com.erinors.hpbexample.client.entity.Person;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class PersonEditPanel extends FlexTable
{
    public PersonEditPanel(final Person person)
    {
        // First name

        setWidget(0, 0, new Label("First name"));

        final TextBox firstNameField = new TextBox();
        firstNameField.setText(person.getName().getFirstName());
        setWidget(0, 1, firstNameField);

        // Last name

        setWidget(1, 0, new Label("Last name"));

        final TextBox lastNameField = new TextBox();
        lastNameField.setText(person.getName().getLastName());
        setWidget(1, 1, lastNameField);

        // Phone numbers

        setWidget(2, 0, new Label("Phone numbers"));

        setWidget(2, 1, new PhoneNumberList(person));

        // Save and cancel buttons

        setWidget(3, 0, new Button("Save", new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                person.getName().setFirstName(firstNameField.getText());
                person.getName().setLastName(lastNameField.getText());

                ExampleWebapp.personService.savePerson(person, new AsyncCallback<Person>()
                {
                    @Override
                    public void onFailure(Throwable caught)
                    {
                        Window.alert("Cannot save person!");
                    }

                    @Override
                    public void onSuccess(Person result)
                    {
                        ExampleWebapp.showPersonList();
                    }
                });
            }
        }));

        setWidget(3, 1, new Button("Cancel", new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                ExampleWebapp.showPersonList();
            }
        }));
    }
}
