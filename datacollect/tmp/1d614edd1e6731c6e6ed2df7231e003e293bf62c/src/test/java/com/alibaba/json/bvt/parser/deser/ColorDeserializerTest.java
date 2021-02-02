package com.alibaba.json.bvt.parser.deser;

import java.awt.Color;

import org.junit.Assert;
import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.deserializer.ColorDeserializer;


public class ColorDeserializerTest extends TestCase {
    public void test_0 () throws Exception {
        new ColorDeserializer().getFastMatchToken();
    }
    
    public void test_error() throws Exception {
        Exception error = null;
        try {
            JSON.parseObject("[]", Color.class);
        } catch (JSONException ex) {
            error = ex;
        }
        Assert.assertNotNull(error);
    }
    
    public void test_error_1() throws Exception {
        Exception error = null;
        try {
            JSON.parseObject("{33:44}", Color.class);
        } catch (JSONException ex) {
            error = ex;
        }
        Assert.assertNotNull(error);
    }
    
    public void test_error_2() throws Exception {
        Exception error = null;
        try {
            JSON.parseObject("{\"r\":44.}", Color.class);
        } catch (JSONException ex) {
            error = ex;
        }
        Assert.assertNotNull(error);
    }
    
    public void test_error_3() throws Exception {
        Exception error = null;
        try {
            JSON.parseObject("{\"x\":44}", Color.class);
        } catch (JSONException ex) {
            error = ex;
        }
        Assert.assertNotNull(error);
    }
}
