package com.erinors.hpbexample.client.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Person extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Name name;

    private Set<PhoneNumber> phoneNumbers;

    @Embedded
    public Name getName()
    {
        if (name == null)
        {
            name = new Name();
        }

        return name;
    }

    public void setName(Name name)
    {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "person")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    public Set<PhoneNumber> getPhoneNumbers()
    {
        if (phoneNumbers == null)
        {
            phoneNumbers = new HashSet<PhoneNumber>();
        }

        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers)
    {
        this.phoneNumbers = phoneNumbers;
    }

    public void addPhoneNumber(PhoneNumber phoneNumber)
    {
        phoneNumber.setPerson(this);
        getPhoneNumbers().add(phoneNumber);
    }
}
