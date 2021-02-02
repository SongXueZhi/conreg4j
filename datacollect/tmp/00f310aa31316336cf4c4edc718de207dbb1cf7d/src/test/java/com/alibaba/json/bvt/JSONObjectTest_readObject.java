package com.alibaba.json.bvt;

import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JSONObjectTest_readObject extends TestCase {
    public void test_0() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 123);

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(bytesOut);
        objOut.writeObject(jsonObject);
        objOut.flush();

        byte[] bytes = bytesOut.toByteArray();

        ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
        ObjectInputStream objIn = new ObjectInputStream(bytesIn);

        Object obj = objIn.readObject();

        assertEquals(JSONObject.class, obj.getClass());
        assertEquals(jsonObject, obj);
    }
}
