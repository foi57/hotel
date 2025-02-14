package org.example.springboot.redis;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.StringJoiner;

@Component("customKeyGenerator")
public class CustomGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringJoiner joiner = new StringJoiner("_");
        joiner.add(target.getClass().getSimpleName());
        joiner.add(method.getName());
        for (Object param : params) {
            if (param instanceof Timestamp) {
                joiner.add(String.valueOf(((Timestamp) param).getTime()));
            } else {
                joiner.add(String.valueOf(param));
            }
        }
        return joiner.toString();
    }
}
