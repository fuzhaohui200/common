/**
 * Copyright (c) 2012-2013 AutoNavi. All rights reserved.  
 * common-core  2014年8月7日 
 * 
 * 相关描述： 
 * 
 */
package org.shine.image;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 相关描述：
 *
 * 文件名：OCRTest.java
 * 作者： AutoNavi 
 * 完成时间：2014年8月7日 下午1:42:21 
 * 维护人员：AutoNavi  
 * 维护时间：2014年8月7日 下午1:42:21 
 * 维护原因：  
 * 当前版本： v1.0 
 *
 */
public class OCRTest {
	
	public static void main(String[] args) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(
                    "http://query.5184.com/query/image.jsp");
 
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            System.out.println("获取验证码: " + response.getStatusLine());
            /* System.out.println(EntityUtils.toString(entity)); */
            InputStream is = entity.getContent();
            BufferedImage image = ImageIO.read(is);
            String imgcode = new OCR().recognizeEverything(image);
 
            System.out.println("\n---- 验证码是: ------- \n" + imgcode);
        } catch (Exception e) {
        	
        }
	}

}
