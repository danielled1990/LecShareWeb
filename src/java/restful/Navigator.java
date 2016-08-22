/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 *
 * @author Danielle
 */
@Path("Navigator")
public class Navigator {
    @Context private HttpServletResponse servletResponse;
    @Context private HttpServletRequest servletRequest;
    @Context private ServletContext servletContext;
    
    public static void Navigate(HttpServletRequest req, HttpServletResponse res, ServletContext cont, String path, String contextData) throws ServletException, IOException{
        Cookie cookie = new Cookie("ContextData", contextData);
        cookie.setPath("/");
        res.addCookie(cookie);
        
        RequestDispatcher dispatcher = cont.getRequestDispatcher(path);
        dispatcher.forward(req, res);
    }
    
    public static void Navigate(HttpServletRequest req, HttpServletResponse res, ServletContext cont, String path) throws ServletException, IOException{
        RequestDispatcher dispatcher = cont.getRequestDispatcher(path);
        dispatcher.forward(req, res);
    }
    
    @POST
    @Path("Navigate")
    public void Navigate(@FormParam("path") String path) throws ServletException, IOException{
        Navigate(servletRequest, servletResponse, servletContext, path);
    }
    

}
