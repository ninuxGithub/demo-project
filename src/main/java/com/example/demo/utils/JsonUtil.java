package com.example.demo.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, TreeMap<String, BigDecimal>> readStringToMap(String content) {
        try {
            Map<String, TreeMap<String, BigDecimal>> result = mapper.readValue(content, new TypeReference<Map<String, TreeMap<String, BigDecimal>>>() {
            });
            return result;
        } catch (IOException e) {
            log.error("JsonUtil readString  to  Map<String, TreeMap<String, BigDecimal>> failure.");
            throw new RuntimeException("数据转换格式失败");
        }
    }

    public static <S,T> Map<S, T> readStringToMap(String content,Class<S> key ,Class<T> value) {
        try {
            Map<S, T> result = mapper.readValue(content,mapper.getTypeFactory().constructMapLikeType(HashMap.class,key,value));
            return result;
        } catch (IOException e) {
            log.error("JsonUtil readString  to  Map failure.");
            throw new RuntimeException( "数据转换格式失败");
        }
    }
    public static <S,T> TreeMap<S, T> readStringToTreeMap(String content,Class<S> key ,Class<T> value) {
        try {
            TreeMap<S, T> result = mapper.readValue(content,mapper.getTypeFactory().constructMapLikeType(TreeMap.class,key,value));
            return result;
        } catch (IOException e) {
            log.error("JsonUtil readString  to  Map failure.");
            throw new RuntimeException("数据转换格式失败");
        }
    }
    public static <T> List<T> readStringToList(String content, Class<T> clazz) {
        try {
            List<T> result = mapper.readValue(content, mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz));
            return result;
        } catch (IOException e) {
            log.error("JsonUtil readString  to  List failure.");
            throw new RuntimeException("数据转换格式失败");
        }
    }

    public static String writeValueAsString(Object content) {
        try {
            return mapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            log.error("JsonUtil writeValueAsString  to  String failure.");
            throw new RuntimeException("数据转换格式失败");
        }
    }

    public static Map<String,Double[]> readStringToMapVArray(String content) {
        try {
            Map<String,Double[]> result = mapper.readValue(content,new TypeReference<Map<String,Double[]>>() {
            });
            return result;
        } catch (IOException e) {
            log.error("JsonUtil readString  to  Map<Integer,List<String>> failure.");
            throw new RuntimeException("数据转换格式失败");
        }
    }

    public static <T> T readStringToJavaBean(String content, Class<T> clazz) {
        try {
            return mapper.readValue(content,clazz);
        } catch (IOException e) {
            log.error("JsonUtil readString  to  Map<Integer,List<String>> failure.");
            throw new RuntimeException("数据转换格式失败");
        }
    }
}
