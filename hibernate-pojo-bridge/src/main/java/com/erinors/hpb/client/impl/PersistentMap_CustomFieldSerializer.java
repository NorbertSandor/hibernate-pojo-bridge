package com.erinors.hpb.client.impl;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.core.java.util.HashMap_CustomFieldSerializer;

/**
 * @author Norbert Sándor
 */
public class PersistentMap_CustomFieldSerializer
{
    public static void deserialize(SerializationStreamReader streamReader, PersistentMap<?, ?> instance)
            throws SerializationException
    {
        HashMap_CustomFieldSerializer.deserialize(streamReader, instance);
        instance.setDirty(streamReader.readBoolean());
    }

    public static void serialize(SerializationStreamWriter streamWriter, PersistentMap<?, ?> instance)
            throws SerializationException
    {
        HashMap_CustomFieldSerializer.serialize(streamWriter, instance);
        streamWriter.writeBoolean(instance.isDirty());
    }
}
