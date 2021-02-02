package com.alibaba.json.bvt.parser.deser;

import org.junit.Assert;
import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultExtJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.CharacterDeserializer;
import com.alibaba.fastjson.parser.deserializer.NumberDeserializer;

public class TestNull extends TestCase {

    public void test_byte() throws Exception {
        DefaultExtJSONParser parser = new DefaultExtJSONParser("null", ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);

        Assert.assertNull(new NumberDeserializer().deserialze(parser, null, null));
    }
    
    public void test_char() throws Exception {
        DefaultExtJSONParser parser = new DefaultExtJSONParser("null", ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
        
        Assert.assertNull(new CharacterDeserializer().deserialze(parser, null, null));
    }
    
    public void test_short() throws Exception {
        DefaultExtJSONParser parser = new DefaultExtJSONParser("null", ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
        
        Assert.assertNull(new NumberDeserializer().deserialze(parser, null, null));
    }
    
    public void test_null() throws Exception {
        DefaultExtJSONParser parser = new DefaultExtJSONParser("null", ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
        
        Assert.assertNull(new NumberDeserializer().deserialze(parser, null, null));
    }
}
