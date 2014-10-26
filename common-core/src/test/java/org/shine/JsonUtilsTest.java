package org.shine;

import net.sf.json.JSONObject;
import org.junit.Test;

/**
 * Created by fuzhaohui on 14-10-25.
 */
public class JsonUtilsTest {

    @Test
    public void testJsonAss() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("key", "key1");
        jsonObject.accumulate("key", "key2");
        System.out.println(jsonObject.toString());
    }
}
