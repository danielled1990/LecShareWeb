/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import SqlConncetion.LessonGet;
import com.google.gson.Gson;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 *
 * @author Danielle
 */
@Path("pdfstamps")
public class pdfstamps {
    private Gson gson = new Gson();

    @Context
    private HttpServletResponse servletResponse;
    @Context
    private HttpServletRequest servletRequest;
    @Context
    private ServletContext servletContext;
    
    @GET
    @Path("hello")
    public String getLesson() throws SQLException{
        
        LessonGet lesson = LessonGet.getLessonData(1);
        String res = gson.toJson(lesson);
        return res;
    }
}
