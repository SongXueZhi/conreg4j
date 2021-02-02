package com.alibaba.json.bvt;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class ArrayRefTest2 extends TestCase {

    public void test_0() throws Exception {
        String text;
        {
            List<Group> groups = new ArrayList<Group>();
            
            Group g0 = new Group(0);
            Group g1 = new Group(1);
            Group g2 = new Group(2);
            
            groups.add(g0);
            groups.add(g1);
            groups.add(g2);
            groups.add(g0);
            groups.add(g1);
            groups.add(g2);
            
            text = JSON.toJSONString(groups);
        }
        
        System.out.println(text);
        
        Group[] groups = JSON.parseObject(text, new TypeReference<Group[]>() {});
        Assert.assertEquals(6, groups.length);
    }

    public static class Group {

        private int id;

        public Group(){

        }

        public Group(int id){
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
        
        public String toString() {
            return "{id:" + id + "}";
        }

    }
}
