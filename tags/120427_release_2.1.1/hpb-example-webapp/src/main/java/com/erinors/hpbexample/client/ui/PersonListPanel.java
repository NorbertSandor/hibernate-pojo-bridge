package com.erinors.hpbexample.client.ui;

import java.util.List;

import com.erinors.hpbexample.client.ExampleWebapp;
import com.erinors.hpbexample.client.entity.Person;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PersonListPanel extends VerticalPanel
{
    public PersonListPanel(List<Person> persons)
    {
        FlexTable personListTable = new FlexTable();

        personListTable.setCellPadding(5);
        personListTable.setBorderWidth(1);

        int row = 0;
        for (final Person person : persons)
        {
            // Name

            personListTable.setText(row, 0, person.getName().toString());

            // Edit link

            Anchor editAnchor = new Anchor("edit");
            editAnchor.addClickHandler(new ClickHandler()
            {
                @Override
                public void onClick(ClickEvent event)
                {
                    ExampleWebapp.show(new PersonEditPanel(person));
                }
            });
            personListTable.setWidget(row, 1, editAnchor);

            // Delete link

            Anchor deleteAnchor = new Anchor("delete");
            deleteAnchor.addClickHandler(new ClickHandler()
            {
                @Override
                public void onClick(ClickEvent event)
                {
                    if (Window.confirm("Really delete " + person.getName() + "?"))
                    {
                        ExampleWebapp.personService.deletePerson(person, new AsyncCallback<Void>()
                        {
                            @Override
                            public void onFailure(Throwable caught)
                            {
                                Window.alert("Cannot delete: " + person.getName());
                            }

                            @Override
                            public void onSuccess(Void result)
                            {
                                ExampleWebapp.showPersonList();
                            }
                        });
                    }
                }
            });
            personListTable.setWidget(row, 2, deleteAnchor);

            row++;
        }

        add(personListTable);

        // New person

        Anchor newAnchor = new Anchor("create new");
        newAnchor.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                ExampleWebapp.show(new PersonEditPanel(new Person()));
            }
        });
        add(newAnchor);
    }
}
