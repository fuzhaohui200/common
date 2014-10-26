package org.shine.common.utils;

import net.sf.json.JsonConfig;

import java.util.Date;

/**
 * Created by fuzhaohui on 14-10-25.
 */
public class JsonUtils {

    private static JsonUtils instance;
    private JsonConfig jsonConfig;

    private JsonUtils() {

        jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
    }

    public static JsonUtils getInstance() {
        if(instance == null) {
            synchronized (JsonUtils.class) {
                if(instance == null) {
                    instance = new JsonUtils();
                }
            }
        }
        return instance;
    }



}
