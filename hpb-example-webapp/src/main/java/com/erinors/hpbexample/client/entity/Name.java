package com.erinors.hpbexample.client.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Name implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String firstName;

    private String lastName;

    public Name()
    {
    }

    public Name(String firstName, String lastName)
    {
        setFirstName(firstName);
        setLastName(lastName);
    }

    @Column(nullable = false)
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Column(nullable = false)
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Transient
    public String getFullName()
    {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString()
    {
        return getFullName();
    }
}
