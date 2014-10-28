package org.shine.common.map.shp;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;

public class ZipUtil {
	private static final int BUFFERLEN = 1024;
	/**
	 * 解压缩zip文件，取出其中shp/csv文件
	 * @param srcFile	源zip文件
	 * @param destDir   解压后文件存放地址
	 * @return 	返回解压后的文件绝对路径.	只会解压第一个shp/csv文件.
	 * @throws Exception
	 */
    public static String unzipFile(File srcFile,String srcEncoding, File destDir,String destEncoding) throws Exception {  
    	return unzipFile(new FileInputStream(srcFile), srcEncoding, destDir,destEncoding);
    }
    
    /**
	 * 解压缩zip文件，取出其中shp/csv文件
	 * @param srcIn		源zip文件输入流
	 * @param destDir   解压后文件存放地址
	 * @return 	返回解压后的文件绝对路径.	只会解压第一个shp/csv文件.
	 * @throws Exception
	 */
	public static String unzipFile(InputStream srcIn,String srcEncoding, File destDir,String destEncoding) throws Exception{
		String filePath = null;
		ZipArchiveInputStream is = null;  
        try {
            destDir.mkdirs();
            is = new ZipArchiveInputStream(new BufferedInputStream(srcIn, BUFFERLEN),srcEncoding);  
            ZipArchiveEntry entry;
            while ((entry = is.getNextZipEntry()) != null) {  
                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());  
                    directory.mkdirs();  
                } else {
                	File file = new File(destDir, entry.getName());
                	if (entry.getName().endsWith(".shp") || entry.getName().endsWith(".csv")) {
                		filePath = file.getAbsolutePath();
					}
                    OutputStream os = null;
                    OutputStreamWriter osw = null;
                    try {
                    	if(entry.getName().endsWith(".shp")){			//如果是shp用字节流处理.
                    		os = new BufferedOutputStream(new FileOutputStream(file), BUFFERLEN);  
                    		IOUtils.copy(is, os);
                    	}else if(entry.getName().endsWith(".csv")){		//如果是csv用字符流处理,设置编码.
                    		osw = new OutputStreamWriter(new FileOutputStream(file, true),destEncoding);
                    		IOUtils.copy(is, osw, srcEncoding);
                    	}
                    } finally {  
                        IOUtils.closeQuietly(os);
                        IOUtils.closeQuietly(osw);
                    }  
                }  
            }  
        } finally {  
            IOUtils.closeQuietly(is);
        }  
        return filePath;
	}
	
	/**
	 * 直接解压zip文件流到目的目录
	 * @param srcIn
	 * @param destDir
	 * @throws Exception	//如果有多个csv或者多个shp文件,则抛出异常.
	 */
	public static String unzipFile(InputStream srcIn, File destDir,String charSet) throws Exception{
		String filePath = null;
		ZipArchiveInputStream is = null;  
		destDir.mkdirs();
		int fileCount = 0;
        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(srcIn, BUFFERLEN),charSet);
            
            ZipArchiveEntry entry = null;  
            while ((entry = is.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());  
                    directory.mkdirs();  
                } else {
                	
                	File file = new File(destDir, entry.getName());
                	//如果压缩包内的文件后缀为大写，则转换为小写
                	String filePostfix=StringUtils.substringAfterLast(file.getName(), ".");
                	for(int i=0;i<filePostfix.length();i++){
                		if(filePostfix.charAt(i)>='A'&&filePostfix.charAt(i)<='Z'){
                			filePostfix=filePostfix.toLowerCase();
                			File reName=new File(file.getParent()+File.separator+StringUtils.substringBeforeLast(file.getName(), ".")+System.currentTimeMillis()+"."+filePostfix);
                			file.renameTo(reName);
                			file=reName;
                			break;
                		}
                	}
                	if(file.getName().endsWith("dbf")||file.getName().endsWith("cvs")){
                		//只返回压缩包内第一个文件
                		if(fileCount==0){
                			filePath = file.getAbsolutePath();
                		}
                		fileCount ++;
                	}
                    OutputStream os = null; 
                    try {
                    	os = new BufferedOutputStream(new FileOutputStream(file), BUFFERLEN);
                    	IOUtils.copy(is, os);
                    } finally {  
                        IOUtils.closeQuietly(os);
                    }
                }  
            }  
//            if(fileCount != 1){
//            	Exception exception = new Exception("zip包内文件csv/shp个数不正确!");
//            	GLog.error(exception.getMessage(), true);
//            	throw exception;
//            }
        } finally {  
            IOUtils.closeQuietly(is);
        } 
        return filePath;
	}
	/**
	 * 解压文件到目录中
	 * @param srcFile
	 * @param destDir
	 * @return
	 * @throws Exception
	 */
	public static String unzipFile(File srcFile, File destDir,String charSet) throws Exception{
		if(null == srcFile || destDir == null){
			return null;
		}
		FileInputStream fis = new FileInputStream(srcFile);
		return unzipFile(fis,destDir,charSet);
	}
	/**
	 * 打包单个csv文件.
	 * @param srcFile
	 * @param destFile
	 * @throws java.io.IOException
	 */
	public static void zipSingleFile(File srcFile, File destFile) throws IOException {
        ZipArchiveOutputStream out = null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(srcFile), BUFFERLEN);
            out = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), BUFFERLEN));
            ZipArchiveEntry entry = new ZipArchiveEntry(srcFile.getName());
            entry.setSize(srcFile.length());
            out.putArchiveEntry(entry);
            IOUtils.copy(is, out);
            out.closeArchiveEntry();
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(out);
        }
    }
	/**
	 * 把
	 * @param srcFolder		/opt/aa/
	 * @param destFile		XXX.zip
	 * @throws java.io.IOException
	 */
	public static void zipFolder(File srcFolder, File destFile) throws Exception {
		if(null == srcFolder || destFile == null){
			return ;
		}
		File[] files = srcFolder.listFiles();
		if(null == files || files.length <= 0){
            throw new Exception("目录为空!");
		}
        ZipArchiveOutputStream out = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), BUFFERLEN));
        InputStream is = null;
        try {
            for (File file : files) {
                try {
                    is = new BufferedInputStream(new FileInputStream(file), BUFFERLEN);
                    ZipArchiveEntry entry = new ZipArchiveEntry(file.getName());
                    entry.setSize(file.length());
                    out.putArchiveEntry(entry);
                    IOUtils.copy(is, out);
                    out.closeArchiveEntry();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    IOUtils.closeQuietly(is);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(out);
        }
    }
	/**
	 * 解压zip包
	 * @param srcIn  
	 * @param srcEncoding
	 * @param destDir  解压路径
	 * @param destEncoding
	 * @return
	 * @throws Exception
	 */
	public static String decZipFile(InputStream srcIn,String srcEncoding, File destDir,String destEncoding) throws Exception{
		ZipArchiveInputStream is = null;
        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(srcIn, BUFFERLEN),srcEncoding);
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null) {  
                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());
                    directory.mkdirs();
                } else {
                	File file = new File(destDir, entry.getName());
                    OutputStream os = null;
                    OutputStreamWriter osw = null;
                    try {
                		//如果是shp用字节流处理.
                		os = new BufferedOutputStream(new FileOutputStream(file), BUFFERLEN);  
                		IOUtils.copy(is, os);
                    } finally {  
                        IOUtils.closeQuietly(os);
                        IOUtils.closeQuietly(osw);
                    }  
                }  
            }  
        } finally {  
            IOUtils.closeQuietly(is);
        }  
        return destDir.getAbsolutePath();
	}

    public static void main(String[] args) throws Exception {
//    	System.out.println(ZipUtil.unzipShp(new File("D:\\zipTest\\aaa.zip"), new File("D:\\zipTest")));
//    	ZipUtil.zipCSVFile(new File("D:\\shp.csv"), new File("D:\\zipTest\\bb.zip"));
//    	ZipUtil.zipFolder(new File("e:\\8wan"), new File("E:\\8wan\\a.zip"));
    	File destDir=new File("E:\\gds_home\\temp");
    	String path=decZipFile(new FileInputStream(new File("E:\\gds_home\\test.zip")),"UTF-8", destDir,"UTF-8");
    	System.out.println(path);
	}
}
