package com.alibaba.json.bvt;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class TypeReerenceTest4 extends TestCase {

    public void test_typeRef() throws Exception {
        TypeReference<VO<List<A>>> typeRef = new TypeReference<VO<List<A>>>() {
        };

        VO<List<A>> vo = JSON.parseObject("{\"list\":[{\"id\":123}]}", typeRef);

        Assert.assertEquals(123, vo.getList().get(0).getId());
    }

    public static class VO<T> {

        private T list;

        public T getList() {
            return list;
        }

        public void setList(T list) {
            this.list = list;
        }
    }

    public static class A {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }
}
