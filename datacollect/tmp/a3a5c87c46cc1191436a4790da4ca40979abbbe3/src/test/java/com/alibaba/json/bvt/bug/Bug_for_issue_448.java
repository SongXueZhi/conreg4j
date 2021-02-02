package com.alibaba.json.bvt.bug;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;

import junit.framework.TestCase;

public class Bug_for_issue_448 extends TestCase {

    public void test_for_issue() throws Exception {
        List<Model> list = new ArrayList<Model>();
        for (int i = 0; i < 10; ++i) {
            Model model = new Model();
            char[] buf = new char[1024 * 16];
            for (int j = 0; j < buf.length; ++j) {
                buf[j] = (char) ('a' + j);
            }
            model.value = new String(buf);
            list.add(model);
        }
        
        String text = JSON.toJSONString(list);
        JSONReader reader = new JSONReader(new StringReader(text));
        reader.startArray();
        while (reader.hasNext()) {
            Model model = reader.readObject(Model.class);
            String value = model.value;
            for (int i = 0; i < value.length(); ++i) {
                char ch = value.charAt(i);
                Assert.assertEquals((char) ('a' + i), ch);
            }
        }
        reader.endArray();
        reader.close();
    }


    public static class Model {
        public String value;
    }
}
