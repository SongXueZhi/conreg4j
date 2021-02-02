package com.alibaba.json.bvt.serializer;

import java.util.LinkedHashMap;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class SerializeConfigTest extends TestCase {

    public void test_1() throws Exception {
        SerializeConfig config = new SerializeConfig();

        Assert.assertEquals("{\"@type\":\"java.util.LinkedHashMap\"}",
                            JSON.toJSONString(new LinkedHashMap(), config, SerializerFeature.WriteClassName));
    }
}
