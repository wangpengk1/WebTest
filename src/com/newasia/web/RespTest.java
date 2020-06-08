package com.newasia.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RespTest extends  BaseServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        super.service(req, resp);
        if(req.getParameterMap().containsKey("id"))
        {
            resp.getWriter().println("dahdiwhahodw");
            System.out.println("found");
        }
    }
}
