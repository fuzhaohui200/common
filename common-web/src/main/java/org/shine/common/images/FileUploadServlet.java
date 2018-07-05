/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.shine.common.images;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;

/**
 *
 * @author zkpursuit
 */
public class FileUploadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("GB2312");
        //获取我项目中存储图片的文件夹地址 你要换成你自己的目录
        String imageFold = request.getSession().getServletContext().getRealPath("/images");
        File fold = new File(imageFold);
        if (!fold.exists()) {
            fold.mkdir();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (item.isFormField()) {
                    processFormField(item);
                } else {
                    processUploadedFile(fold, item);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processFormField(FileItem item) {
        String name = item.getFieldName();
        String value = item.getString();
    }

    private void processUploadedFile(File parent, FileItem item) throws Exception {
        long time = System.currentTimeMillis();
        File savedFile = new File(parent, item.getName());
        item.write(savedFile);

        GMOperation op = new GMOperation();
        //待处理图片的绝对路径  
        op.addImage(savedFile.getAbsolutePath());
        //图片压缩比，有效值范围是0.0-100.0，数值越大，缩略图越清晰  s
        op.quality(20.0);
        //width 和height可以是原图的尺寸，也可以是按比例处理后的尺寸
        op.addRawArgs("-resize", "100");
        //宽高都为100
        //op.addRawArgs("-resize", "100x100");
        op.addRawArgs("-gravity", "center");
        //op.resize(100, null);  
        //处理后图片的绝对路径  
        File smallFile = new File(parent.getAbsolutePath() + "/small");
        if(!smallFile.exists()) {
            smallFile.mkdir();
        }
        op.addImage(smallFile.getAbsolutePath() + "/" + item.getName());

        // 如果使用ImageMagick，设为false,使用GraphicsMagick，就设为true，默认为false  
        ConvertCmd convert = new ConvertCmd(true);
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            //linux下不要设置此值，不然会报错
            convert.setSearchPath("D://Program Files//GraphicsMagick-1.3.19-Q8");
        }
        convert.run(op);
        System.out.println((System.currentTimeMillis() - time));
        //压缩图片保存
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}