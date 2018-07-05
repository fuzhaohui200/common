package org.shine;

import net.sf.json.JSONObject;
import org.junit.Test;

/**
 * Created by fuzhaohui on 14-10-25.
 */
public class JsonUtilsTest {

    public void testJsonAss() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("key", "key1");
        jsonObject.accumulate("key", "key2");
        System.out.println(jsonObject.toString());
    }

    public static void main(String[] args) {
        float a = 5.4f;
        Float b = new Float(5.4f);
        double c = 5.4d;
        Double d = new Double(5.4d);

        float e = -1;
        double f = 5.4d;


        System.out.println("value=" + f);
    }
}
