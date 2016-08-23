/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import Exception.DBException;
import SqlConncetion.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 *
 * @author Danielle
 */
@Path("Account")
public class Account {

    private Gson gson = new Gson();

    @Context
    private HttpServletResponse servletResponse;
    @Context
    private HttpServletRequest servletRequest;
    @Context
    private ServletContext servletContext;

    @POST
    @Path("register")
    public String registerUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("confirmed passwored") String confirmedPassword,
            @FormParam("email") String email, @FormParam("schools") String school)
            throws ServletException, IOException {
        boolean loggedIn = false;
        Integer id = null;
        String jsonResponse = "";
        User user = null;
        //Check if we're already logged in
    /*    if(servletRequest.getSession(false) != null){
         //If we already, make sure that the credentials are correct
         if(servletRequest.getSession().getAttribute("username") == username &&
         servletRequest.getSession().getAttribute("password") == password){
         loggedIn = true;
         id = Integer.parseInt(servletRequest.getSession().getAttribute("userID").toString());
         }
         }*/

        //If we're not logged in yet
        try {
            user = (User.createUser(username, password, email, school));
            servletRequest.getSession().setAttribute("username", user.getUsername());
            servletRequest.getSession().setAttribute("password", password);
            servletRequest.getSession().setAttribute("userid", user.getId());
            servletResponse.sendRedirect("/restapp/newhtml.html");
            //  Navigator.Navigate(servletRequest, servletResponse, servletContext, "/WEB-INF/view/index.html");
        } catch (SQLException | DBException ex) {
            //   Navigator.Navigate(servletRequest, servletResponse, servletContext, "/WEB-INF/view/error_page.html", ex.getMessage());
        }

        //If login was successful, we'll navigate where we wanted to go
        return jsonResponse;

    }

    @POST
    @Path("login")
    public String loginUser(@FormParam("target_page") String targetPage, @FormParam("email") String email, @FormParam("password") String password, @FormParam("fail_redirect_login") boolean failRedirectLogin) throws ServletException, IOException {
        boolean loggedIn = false;
        Integer id = null;
        User user = null;
        PrintWriter out= servletResponse.getWriter();
        //Check if we're already logged in
        if (servletRequest.getSession(false) != null) {
            //If we already, make sure that the credentials are correct
            if (servletRequest.getSession().getAttribute("username") == email
                    && servletRequest.getSession().getAttribute("password") == password) {
                loggedIn = true;
                id = Integer.parseInt(servletRequest.getSession().getAttribute("userID").toString());
            }
        }

        //If we're not logged in yet
        if (!loggedIn) {
            try {
                //We'll try to login with our details, validating them against the server
                user = User.validateCredentials(email, password);

                if (user != null) {
                    loggedIn = true;
                }
            } catch (SQLException | DBException ex) {
                //Upon connection error, do we go back to the login page or the error page?
                if (failRedirectLogin) {
                    Navigator.Navigate(servletRequest, servletResponse, servletContext, "newhtml.html", targetPage);
                } else {
                    RequestDispatcher rd = servletContext.getRequestDispatcher("/index.html");
                    
                    out.println("<font color=red>Either user name or password is wrong.</font>");
                 //   servletResponse.sendRedirect("/restapp/index.html"); 
                 //   return out;
                    rd.include(servletRequest, servletResponse);
                    
                }
            }
        }

        //If login was successful, we'll navigate where we wanted to go
        if (loggedIn && user != null) {
            //Make sure our credentials are saved this time.
            servletRequest.getSession().setAttribute("username", user.getUsername());
            servletRequest.getSession().setAttribute("password", password);
            servletRequest.getSession().setAttribute("userid", user.getId());

  //          Cookie idCookie = new Cookie("id", id.toString());
  //          idCookie.setPath("/");
  //          idCookie.setMaxAge(30 * 24 * 60 * 60);
   //         servletResponse.addCookie(idCookie);

            //  Navigator.Navigate(servletRequest, servletResponse, servletContext, "newhtml.html");
            servletResponse.sendRedirect("/restapp/newhtml.html");
            return "ok";

        }
        

//    return gson.toJson(loggedIn);
        return null;
        
    }
    @GET
    @Path("hello")
    public String getUsername(){
        String name = (String)servletRequest.getSession().getAttribute("username");
        return name;
    }
}
