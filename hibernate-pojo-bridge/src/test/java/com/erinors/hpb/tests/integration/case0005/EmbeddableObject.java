package com.erinors.hpb.tests.integration.case0005;

import javax.persistence.Embeddable;

import org.springframework.util.ObjectUtils;

@Embeddable
public class EmbeddableObject {
    private String value;

    public EmbeddableObject()
    {
    }

    public EmbeddableObject(String value)
    {
        setValue(value);
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EmbeddableObject))
        {
            return false;
        }

        EmbeddableObject other = (EmbeddableObject) obj;
        return ObjectUtils.nullSafeEquals(other.getValue(), getValue());
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(getValue());
    }
}
