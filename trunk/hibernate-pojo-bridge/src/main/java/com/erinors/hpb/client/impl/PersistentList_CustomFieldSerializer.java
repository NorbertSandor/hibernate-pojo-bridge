package com.erinors.hpb.client.impl;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.core.java.util.ArrayList_CustomFieldSerializer;

/**
 * @author Norbert Sándor
 */
public class PersistentList_CustomFieldSerializer
{
    public static void deserialize(SerializationStreamReader streamReader, PersistentList<?> instance)
            throws SerializationException
    {
        ArrayList_CustomFieldSerializer.deserialize(streamReader, instance);
        instance.setDirty(streamReader.readBoolean());
    }

    public static void serialize(SerializationStreamWriter streamWriter, PersistentList<?> instance)
            throws SerializationException
    {
        ArrayList_CustomFieldSerializer.serialize(streamWriter, instance);
        streamWriter.writeBoolean(instance.isDirty());
    }
}
