package com.erinors.hpbexample.client.ui;

import com.erinors.hpbexample.client.entity.Person;
import com.erinors.hpbexample.client.entity.PhoneNumber;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;

public class PhoneNumberList extends FlexTable
{
    public PhoneNumberList(Person person)
    {
        int row = 0;
        for (final PhoneNumber phoneNumber : person.getPhoneNumbers())
        {
            final TextBox field = new TextBox();
            field.setText(phoneNumber.getPhoneNumber());

            field.addBlurHandler(new BlurHandler()
            {
                @Override
                public void onBlur(BlurEvent event)
                {
                    phoneNumber.setPhoneNumber(field.getText());
                }
            });

            setWidget(row++, 0, field);
        }
    }
}
