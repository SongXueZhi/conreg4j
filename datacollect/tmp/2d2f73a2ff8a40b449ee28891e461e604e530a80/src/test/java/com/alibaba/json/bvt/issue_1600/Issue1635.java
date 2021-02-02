package com.alibaba.json.bvt.issue_1600;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import junit.framework.TestCase;

import java.util.List;

public class Issue1635 extends TestCase {
    public static class Foo {
        public String name;
        public Integer BarCount;
        public Boolean flag;
        public List list;

        public Foo(String name, Integer barCount) {
            this.name = name;
            BarCount = barCount;
        }
    }

    public void test_issue() throws Exception {
        SerializeConfig config = new SerializeConfig();
        Foo foo = new Foo(null, null);
        String json = JSON.toJSONString(foo
                , config, new PascalNameFilter()
                , SerializerFeature.WriteNullBooleanAsFalse
                , SerializerFeature.WriteNullNumberAsZero
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.WriteNullListAsEmpty
        );
        assertEquals("{\"BarCount\":0,\"Flag\":false,\"List\":[],\"Name\":\"\"}", json);
    }

    public void test_issue_1() throws Exception {
        SerializeConfig config = new SerializeConfig();
        Foo foo = new Foo(null, null);
        String json = JSON.toJSONString(foo
                , config, new PascalNameFilter()
                , SerializerFeature.WriteNullBooleanAsFalse
                , SerializerFeature.WriteNullNumberAsZero
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.WriteNullListAsEmpty
                , SerializerFeature.BeanToArray
        );
        assertEquals("[0,false,[],\"\"]", json);
    }

    public class PascalNameFilter implements NameFilter {

        public String process(Object source, String name, Object value) {
            if (name == null || name.length() == 0) {
                return name;
            }

            char[] chars = name.toCharArray();
            chars[0]= Character.toUpperCase(chars[0]);

            String pascalName = new String(chars);
            return pascalName;
        }
    }
}
