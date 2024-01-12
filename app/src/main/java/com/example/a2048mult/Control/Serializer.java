package com.example.a2048mult.Control;

import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Objects;

public class Serializer {
    public static void serializePDU(PDU pdu, OutputStream os) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);

        oos.writeUTF(PDU.MAGIC_NUMBER);
        oos.writeUTF(PDU.VERSION);
        oos.writeObject(pdu);
        oos.close();
    }

    public static PDU deserializePDU(InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(is);
        if(!Objects.equals(ois.readUTF(), PDU.MAGIC_NUMBER) || !Objects.equals(ois.readUTF(), PDU.VERSION)){
            throw new NotSerializableException("Wrong Protocol or Version");
        }
        return (PDU)ois.readObject();
    }
}
