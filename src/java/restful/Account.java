/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import Exception.DBException;
import SqlConncetion.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javaClass.test;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Danielle
 */
@Path("Account")
public class Account {

    private Gson gson = new Gson();
    private T  t;
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
            servletResponse.sendRedirect("/restapp/insertSchedule2.html");
            //  Navigator.Navigate(servletRequest, servletResponse, servletContext, "/WEB-INF/view/index.html");
        } catch (SQLException | DBException ex) {
            //   Navigator.Navigate(servletRequest, servletResponse, servletContext, "/WEB-INF/view/error_page.html", ex.getMessage());
        }

        //If login was successful, we'll navigate where we wanted to go
        return jsonResponse;

    }

    @POST
    
   // @Consumes({MediaType.APPLICATION_JSON})
   // @Produces({MediaType.TEXT_PLAIN})
    @Path("/login")
    public String loginUser( @FormParam("email") String email, @FormParam("password") String password) throws ServletException, IOException {
        boolean loggedIn = false;
        Integer id = null;
        User user = null;
        String res = "kaki";
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
                // else {
                 /*   RequestDispatcher rd = servletContext.getRequestDispatcher("/index.html");
                    
                    out.println("<font color=red>Either user name or password is wrong.</font>");
                 //   servletResponse.sendRedirect("/restapp/index.html"); 
                 //   return out;
                    rd.include(servletRequest, servletResponse);*/
                //    return "no ok";
                    
               // }
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
          //  servletResponse.sendRedirect("/restapp/pdfStampsVideos.html");
            res = "ok";

        }
        
       // return gson.toJson(res);
       return res;
        
    }
    @GET
    @Path("hello")
    public String getUsername(){
        String name = (String)servletRequest.getSession().getAttribute("username");
        return name;
    }
    @Path("login2")
    @Produces({MediaType.TEXT_PLAIN})
    @POST
    public String get( @FormParam("email") String email, @FormParam("password") String password) throws ServletException, IOException {
        boolean loggedIn = false;
        Integer id = null;
        User user = null;
        String res = "kaki";
                if (servletRequest.getSession(false) != null) {
            //If we already, make sure that the credentials are correct
            if (servletRequest.getSession().getAttribute("username") == email
                    && servletRequest.getSession().getAttribute("password") == password) {
                loggedIn = true;
                id = Integer.parseInt(servletRequest.getSession().getAttribute("userID").toString());
            }
        }
         if (!loggedIn) {
            try {
                //We'll try to login with our details, validating them against the server
                user = User.validateCredentials(email, password);

                if (user != null) {
                    loggedIn = true;
                }
            } catch (SQLException | DBException ex) {
                //Upon connection error, do we go back to the login page or the error page?
                // else {
                 /*   RequestDispatcher rd = servletContext.getRequestDispatcher("/index.html");
                    
                    out.println("<font color=red>Either user name or password is wrong.</font>");
                 //   servletResponse.sendRedirect("/restapp/index.html"); 
                 //   return out;
                    rd.include(servletRequest, servletResponse);*/
                    return "no ok";
                    
               // }
            }
        }
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
          //  servletResponse.sendRedirect("/restapp/pdfStampsVideos.html");
            res = "ok";

        }
        return res;
    }
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("course")
    public String  getCourses(String res) throws ServletException, IOException, SQLException, DBException{
        
        t = gson.fromJson(res, T.class);
        int userid =(int) servletRequest.getSession().getAttribute("userid");
       // if(User.insertCourseToUser(t, userid)){
        User.insertCourseToUser(t, userid);
            return null;
     //   }
     //   return "not ok";
        //String res;
     //   res = "kaki";
        
    }
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("courseInfo")
    public String getCourses(){
        return gson.toJson(t.course);
    }
    
    
    @POST
  //  @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("course2")
    public Response  getCourses2 (String res){
        JsonObject js = new JsonObject();
        int i = js.get(res).getAsInt();
        return null;
    }
    //@JsonDeserialize(using = t.class)
    public static  class T{
        public String[] course;
        public String[] semester;
        public T(){}
    }
    
}
