package com.erinors.hpbexample.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class PhoneNumber extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Person person;

    private String phoneNumber;

    public PhoneNumber()
    {
    }

    public PhoneNumber(String phoneNumber)
    {
        setPhoneNumber(phoneNumber);
    }

    @ManyToOne(optional = false)
    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    @Column(nullable = false)
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof PhoneNumber))
        {
            return false;
        }

        PhoneNumber other = (PhoneNumber) obj;
        return getPhoneNumber() == null ? other.getPhoneNumber() == null : getPhoneNumber().equals(
                other.getPhoneNumber());
    }

    @Override
    public int hashCode()
    {
        return getPhoneNumber() == null ? 0 : getPhoneNumber().hashCode();
    }
}
