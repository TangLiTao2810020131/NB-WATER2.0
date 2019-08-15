package com.ets.common;

import com.google.common.collect.Maps;
import com.google.common.hash.*;
import com.google.common.io.Files;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;


/**
 * @author: 姚轶文
 * @date:2018年9月4日 上午10:35:00
 * @version :
 * 
 */
public class Md5Util {

	private static HashFunction hf = Hashing.md5();
    private static Charset defaultCharset = Charset.forName("UTF-8");
 
    private Md5Util() {
        throw new AssertionError("不要实例化工具类哦");
    }
 
    public static String md5(String data) {
        HashCode hash = hf.newHasher().putString(data, defaultCharset).hash();
        return hash.toString();
    }
 
    public static String md5(String data, Charset charset, boolean isUpperCase) {
        HashCode hash = hf.newHasher().putString(data, charset == null ? defaultCharset : charset).hash();
        return isUpperCase ? hash.toString().toUpperCase() : hash.toString();
    }
 
    public static String md5(byte[] bytes, boolean isUpperCase) {
        HashCode hash = hf.newHasher().putBytes(bytes).hash();
        return isUpperCase ? hash.toString().toUpperCase() : hash.toString();
    }
 
    public static String md5(File sourceFile, boolean isUpperCase) {
        HashCode hash = hf.newHasher().putObject(sourceFile, new Funnel<File>() {
 
            private static final long serialVersionUID = 2757585325527511209L;
 
            @Override
            public void funnel(File from, PrimitiveSink into) {
                try {
                    into.putBytes(Files.toByteArray(from));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).hash();
        return isUpperCase ? hash.toString().toUpperCase() : hash.toString();
    }
 
    /**
     * 将其转换为json后，再进行md5
     *
     * @param object 数据源可以是任何类型的对象
     * @param isUpperCase 结果是否大写
     * @param charset 涉及到字符串时的操作编码，默认是utf-8
     * @return
     */
    public static String md5(Object object, boolean isUpperCase, Charset charset) {
        Hasher hasher = hf.newHasher();
        Gson gson = new Gson();
        String json = gson.toJson(object);
 
        HashCode hash = hasher.putString(json, charset == null ? defaultCharset : charset).hash();
        return isUpperCase ? hash.toString().toUpperCase() : hash.toString();
    }
 
    /**
     * @param object 只能是封装了数据的实体类，不可以是map，List等
     * @param fieldNames 需要参与md5计算的字段属性名，如果该属性也是一个封住数据实体类话，.后跟上具体属性名即可。如：role.level.id
     * @param isUpperCase 结果是否大写
     * @param charset 涉及到字符串时的操作编码，默认是utf-8
     * @return
     */
    public static String md5(Object object, List<String> fieldNames, boolean isUpperCase, Charset charset) {
        HashCode hash = hf.newHasher().putObject(object, new Funnel<Object>() {
 
            private static final long serialVersionUID = -5236251432355557848L;
 
            @Override
            public void funnel(Object from, PrimitiveSink into) {
 
                Map<String, Field> allField = getAllField(object);
 
                for (String fieldName : fieldNames) {
 
                    try {
                        if (fieldName.contains(".")) {
                            handleDeepField(object, charset, into, allField, fieldName);
                        } else {
                            handleField(object, charset, into, allField, fieldName);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
 
        }).hash();
        return isUpperCase ? hash.toString().toUpperCase() : hash.toString();
    }
 
    private static void handleDeepField(Object tempValue, Charset charset, PrimitiveSink into, Map<String, Field> tempAllField, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = null;
        String[] names = fieldName.split("\\.");
 
        for (String name : names) {
            field = tempAllField.get(name);
            if (field == null) {
                throw new NoSuchFieldException(fieldName);
            }
            field.setAccessible(true);
            tempValue = field.get(tempValue);
            field.setAccessible(false);
            tempAllField = getAllField(tempValue);
        }
 
        stuffFieldValue(tempValue, charset, into);
    }
 
    private static void handleField(Object object, Charset charset, PrimitiveSink into, Map<String, Field> allField, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = allField.get(fieldName);
        if (field == null) {
            throw new NoSuchFieldException(fieldName);
        }
 
        field.setAccessible(true);
        Object tempValue = field.get(object);
        stuffFieldValue(tempValue, charset, into);
        field.setAccessible(false);
    }
 
    private static void stuffFieldValue(Object value, Charset charset, PrimitiveSink into) throws IllegalAccessException {
 
        if (value instanceof Integer) {
            into.putInt((int) value);
        } else if (value instanceof Long) {
            into.putLong((long) value);
        } else if (value instanceof Float) {
            into.putFloat((float) value);
        } else if (value instanceof Double) {
            into.putDouble((double) value);
        } else if (value instanceof Short) {
            into.putShort((short) value);
        } else if (value instanceof Byte) {
            into.putByte((byte) value);
        } else if (value instanceof Boolean) {
            into.putBoolean((boolean) value);
        } else if (value instanceof Byte) {
            into.putByte((byte) value);
        } else if (value instanceof Character) {
            into.putChar((char) value);
        } else if (value instanceof String) {
            into.putString((String) value, charset == null ? defaultCharset : charset);
        } else {
            throw new IllegalArgumentException(value.getClass() + " is not basic data type");
        }
    }
 
    private static Map<String, Field> getAllField(Object object) {
        Map<String, Field> fieldMap = Maps.newHashMap();
 
        if (object.getClass().getName().equals(Object.class.getName())) {
            return fieldMap;
        }
 
        Class<?> tempClass = object.getClass();
        Field[] declaredFields = null;
        while (true) {
            declaredFields = tempClass.getDeclaredFields();
            for (Field field : declaredFields) {
                fieldMap.put(field.getName(), field);
            }
 
            tempClass = tempClass.getSuperclass();
 
            if (tempClass.getName().equals(Object.class.getName())) {
                break;
            }
 
        }
 
        return fieldMap;
    }

}
