package com.newasia.web;

import com.newasia.web.Util.MD5Tool;
import com.newasia.web.Util.RandomGUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.util.Collection;

@WebServlet("/upload")
@MultipartConfig
public class Servlet_UpLoad extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        if(req.getParts()==null || req.getParts().isEmpty())
        {
            System.out.println("don't find part");
        }

        Part[] parts = new Part[req.getParts().size()];
        req.getParts().toArray(parts);
        for (Part part:parts)
        {
            System.out.println(part.getName());
            System.out.println(part.getSize());
            System.out.println(part.getContentType());
            System.out.println(part.getHeader("Content-Disposition"));
            if(part.getName()!=null && part.getContentType() != null && part.getContentType().contains("image") && part.getSize()>0)
            {
                saveImageFile(part);
            }
        }
    }

    private void saveImageFile(Part part)
    {
        String path = mLatestReq.getServletContext().getRealPath("/image");
        File file = new File(path);
        if(!file.exists())
        {
            file.mkdirs();
        }

        String disposition = part.getHeader("Content-Disposition");
        String suffix = disposition.substring(disposition.lastIndexOf("."),disposition.length()-1);
        String md5Name;
        try {
            md5Name = MD5Tool.generateMD5ForStream(part.getInputStream());
        }catch (IOException e){e.printStackTrace(); return;}

        String fullPath = path+"/"+md5Name+suffix;
        System.out.println(fullPath);
        file = new File(fullPath);
        if(file.exists()) return;

        try
        {
            FileOutputStream fos = new FileOutputStream(fullPath);
            InputStream in = part.getInputStream();
            int len = 0;
            byte[] data = new byte[1024];
            while ((len=in.read(data))>0)
            {
                fos.write(data,0,len);
            }

            in.close();
            fos.close();
        }catch (IOException e){e.printStackTrace();}

    }
    //我啊久啊湿地
}
