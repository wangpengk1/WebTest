package com.newasia.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet("/image")
public class Image  extends BaseServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        if(isParamersEmpty())
        {
            //mLatestResp.sendRedirect("/index.jsp");
            getServletContext().getRequestDispatcher("/image?vercode=请指定参数vercode=").forward(req,resp);
        }

        if(req.getParameterMap().containsKey("vercode"))
        {
            System.out.println("found");
            verificationCode();
        }


    }


    private void verificationCode() throws IOException
    {
        String strCode = mLatestReq.getParameter("vercode");
        BufferedImage image = new BufferedImage(200,100,BufferedImage.TYPE_INT_RGB);
        Graphics2D graph2D = (Graphics2D)image.getGraphics();
        graph2D.setColor(Color.red);
        graph2D.fillRect(0,0,image.getWidth(),image.getHeight());
        graph2D.setFont(new Font(null,Font.BOLD,20));
        graph2D.setColor(Color.BLACK);
        graph2D.drawString(strCode,0,60);
        mLatestResp.setContentType("image/jpeg");
//        mLatestResp.setDateHeader("expries", -1);
//        mLatestResp.setHeader("Cache-Control", "no-cache");
//        mLatestResp.setHeader("Pragma", "no-cache");
        //6.将图片写给浏览器
        ImageIO.write(image, "jpg", mLatestResp.getOutputStream());
    }
}
