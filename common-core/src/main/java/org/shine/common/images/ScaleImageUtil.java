package org.shine.common.images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 相关描述：缩放图片
 *
 * 文件名：ScaleImageUtil.java
 * 作者： AutoNavi 
 * 完成时间：2014年7月30日 上午11:25:20 
 * 维护人员：AutoNavi  
 * 维护时间：2014年7月30日 上午11:25:20 
 * 维护原因：  
 * 当前版本： v1.0 
 *
 */
public class ScaleImageUtil {

	/**
	 * 方法描述：按最大尺寸等比例缩放图片成字节
	 *
	 * 作者： AutoNavi
	 * 完成时间： 2014年7月30日 上午11:25:32
	 * 维护人员： AutoNavi
	 * 维护时间： 2014年7月30日 上午11:25:32
	 * 维护原因: 
	 * 当前版本： v1.0 
	 * @param data
	 * @param maxSize
	 * @return
	 * @throws IOException
	 */
	public static byte[] scaleMaxImageToByte(byte[] data , int maxSize) throws IOException {
		BufferedImage buffered_oldImage = ImageIO.read(new ByteArrayInputStream(data));
		int imageOldWidth = buffered_oldImage.getWidth();
		int imageOldHeight = buffered_oldImage.getHeight();
		double scale_x = (double) maxSize / imageOldWidth;
		double scale_y = (double) maxSize / imageOldHeight;
		double scale_xy = Math.min(scale_x, scale_y);
		int imageNewWidth = (int) (imageOldWidth * scale_xy);
		int imageNewHeight = (int) (imageOldHeight * scale_xy);
		BufferedImage buffered_newImage = new BufferedImage(imageNewWidth, imageNewHeight, BufferedImage.TYPE_INT_RGB);
		buffered_newImage.getGraphics().drawImage(buffered_oldImage.getScaledInstance(imageNewWidth, imageNewHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
		buffered_newImage.getGraphics().dispose();
		ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
		ImageIO.write(buffered_newImage, "jpeg", outPutStream);
		return outPutStream.toByteArray();
	}

	/**
	 * 方法描述：按最大尺寸等比例缩放图片成图片文件
	 *
	 * 作者： AutoNavi
	 * 完成时间： 2014年7月30日 上午11:27:33
	 * 维护人员： AutoNavi
	 * 维护时间： 2014年7月30日 上午11:27:33
	 * 维护原因: 
	 * 当前版本： v1.0 
	 * @param src
	 * @param imgDist
	 * @param maxSize
	 * @throws IOException
	 */
	public static void scaleMaxImageToFile(String src, String imgDist, int maxSize) throws IOException {
		File srcfile = new File(src);
		if (!srcfile.exists()) {
			return;
		}
		File destFile = new File(imgDist);
		if(!destFile.getParentFile().exists()){
			destFile.getParentFile().mkdirs();
		}
		
		BufferedImage buffered_oldImage = ImageIO.read(srcfile);
		int imageOldWidth = buffered_oldImage.getWidth();
		int imageOldHeight = buffered_oldImage.getHeight();
		double scale_x = (double) maxSize / imageOldWidth;
		double scale_y = (double) maxSize / imageOldHeight;
		double scale_xy = Math.min(scale_x, scale_y);
		int imageNewWidth = (int) (imageOldWidth * scale_xy);
		int imageNewHeight = (int) (imageOldHeight * scale_xy);
		BufferedImage buffered_newImage = new BufferedImage(imageNewWidth, imageNewHeight, BufferedImage.TYPE_INT_RGB);
		buffered_newImage.getGraphics().drawImage(buffered_oldImage.getScaledInstance(imageNewWidth, imageNewHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
		buffered_newImage.getGraphics().dispose();
		FileOutputStream outPutStream = new FileOutputStream(imgDist);
		ImageIO.write(buffered_newImage, "jpeg", outPutStream);
		outPutStream.close();
	}
	
	/**
	 * 方法描述：按指定大小缩放图片成字节
	 *
	 * 作者： AutoNavi
	 * 完成时间： 2014年7月30日 上午11:28:16
	 * 维护人员： AutoNavi
	 * 维护时间： 2014年7月30日 上午11:28:16
	 * 维护原因: 
	 * 当前版本： v1.0 
	 * @param src
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static byte[] scaleImageToByte(String src , int width, int height) throws IOException {
		File srcfile = new File(src);
		if (!srcfile.exists()) {
			return null;
		}
		BufferedImage buffered_oldImage = ImageIO.read(srcfile);
		BufferedImage buffered_newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		buffered_newImage.getGraphics().drawImage(buffered_oldImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, null);
		buffered_newImage.getGraphics().dispose();
		ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
		ImageIO.write(buffered_newImage, "jpeg", outPutStream);
		return outPutStream.toByteArray();
	}

	/**
	 * 方法描述：按指定大小缩放图片成文件
	 *
	 * 作者： AutoNavi
	 * 完成时间： 2014年7月30日 上午11:28:36
	 * 维护人员： AutoNavi
	 * 维护时间： 2014年7月30日 上午11:28:36
	 * 维护原因: 
	 * 当前版本： v1.0 
	 * @param src
	 * @param imgDist
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void scaleImageToFile(String src, String imgDist, int width, int height) throws IOException {
		File srcfile = new File(src);
		if (!srcfile.exists()) {
			return;
		}
		File destFile = new File(imgDist);
		if(!destFile.getParentFile().exists()){
			destFile.getParentFile().mkdirs();
		}
		BufferedImage buffered_oldImage = ImageIO.read(srcfile);
		BufferedImage buffered_newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		buffered_newImage.getGraphics().drawImage(buffered_oldImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, null);
		buffered_newImage.getGraphics().dispose();
		FileOutputStream outPutStream = new FileOutputStream(imgDist);
		ImageIO.write(buffered_newImage, "jpeg", outPutStream);
		outPutStream.close();
	}
	
	public static void reduceImg(String imgsrc, int targetLength,boolean isWidth) {   
	    try {   
	        File srcfile = new File(imgsrc);   
	        if (!srcfile.exists()) {   
	            return;   
	        }
	        Image src = ImageIO.read(srcfile);   
	        
	        //原始图像高和宽
	        int width  = src.getWidth(null);
	        int height  = src.getHeight(null);
	        
	        int widthdist = 0;
	        int heightdist = 0;
	        
	        //确定图像的缩放后的高和宽
	        if(isWidth){
	        	if(targetLength >= width) return;
	        	double scale = targetLength * 1.0/ width;
	        	widthdist = targetLength;
	        	heightdist = (int) (height*scale);
	        }else{
	        	if(targetLength >= height) return;
	        	double scale = targetLength * 1.0/ height;
	        	widthdist = (int) (width*scale);
	        	heightdist = targetLength;
	        }
	        BufferedImage tag= new BufferedImage((int) widthdist, (int) heightdist,   
	                BufferedImage.TYPE_INT_RGB);   
	  
	        tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,  Image.SCALE_FAST), 0, 0,  null);  //根据缩略图要求品质可以选择 Image.SCALE_SMOOTH
	        String formatName = getFormatName(srcfile);//此句必须在new FileOutputStream之前，因为是替换原图，FileOutputStream对象未关闭之前，ImageInputStream无法获得文件格式。
	        FileOutputStream out = new FileOutputStream(srcfile);
	        ImageIO.write(tag, formatName, out);
	        out.flush();
	        out.close();   
	  
	    } catch (IOException ex) {   
	        ex.printStackTrace();   
	    }   
	}  
	
	/**
	 * 获取图片的格式
	 * @param o
	 * @return
	 */
	public static String getFormatName(File o) {
	    try {
	        // Create an image input stream on the image
	        ImageInputStream iis = ImageIO.createImageInputStream(o);

	        // Find all image readers that recognize the image format
	        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
	        if (!iter.hasNext()) {
	            // No readers found
	            return null;
	        }
	        // Use the first reader
	        ImageReader reader = (ImageReader)iter.next();
	        // Close stream
	        iis.close();
	        // Return the format name
	        return reader.getFormatName();
	    } catch (IOException e) {
	    }
	    // The image could not be read
	    return null;
	}
	
	public static void main(String[] args) {
		//System.out.println(getFormatName(new File("F:/images/test.jpg")));
		String imageSrc = ScaleImageUtil.class.getResource("/").getPath()+"/psb.jpeg";
		String imgDist = ScaleImageUtil.class.getResource("/").getPath()+"/psb2.jpeg";
		try {
			scaleMaxImageToFile(imageSrc, imgDist, 500);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

