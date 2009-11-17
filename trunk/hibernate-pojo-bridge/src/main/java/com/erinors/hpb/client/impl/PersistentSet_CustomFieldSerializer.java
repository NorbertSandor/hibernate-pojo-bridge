package com.erinors.hpb.client.impl;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.core.java.util.HashSet_CustomFieldSerializer;

/**
 * @author Norbert Sándor
 */
public class PersistentSet_CustomFieldSerializer
{
    public static void deserialize(SerializationStreamReader streamReader, PersistentSet<?> instance)
            throws SerializationException
    {
        HashSet_CustomFieldSerializer.deserialize(streamReader, instance);
        instance.setDirty(streamReader.readBoolean());
    }

    public static void serialize(SerializationStreamWriter streamWriter, PersistentSet<?> instance)
            throws SerializationException
    {
        HashSet_CustomFieldSerializer.serialize(streamWriter, instance);
        streamWriter.writeBoolean(instance.isDirty());
    }
}
