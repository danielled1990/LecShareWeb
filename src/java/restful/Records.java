/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import static org.eclipse.persistence.jpa.jpql.parser.Expression.SET;

/**
 * REST Web Service
 *
 * @author Danielle
 */
@Path("records")
public class Records {

    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of ServiceResource
     */
    public Records() {
    }

    /**
     * Retrieves representation of an instance of com.vichargrave.ServiceResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml()
    {
        String s="";
        //TODO return proper representation object
      //  FileInputStream fis = null;
                		//try {
			
			/*PreparedStatement pre;
			//Class.forName("com.mysql.jdbc.Driver");

			
                        java.sql.Date date = new java.sql.Date(0000-00-00);
			File picfile = new File("C:\\Users\\Danielle\\Pictures\\12.m4a");
			FileInputStream fis = new FileInputStream(picfile);

			pre = SqlConncetion.SqlCon.dbConnection.prepareStatement("INSERT INTO audiorecordsdb.records (idRecords,Date,name,Audio) values (?,?,?,?)");
                        pre.setString(1, "7"); 
                        pre.setDate(2,date);
                        pre.setString(3, "susu");
			pre.setBinaryStream(4, fis, (int) picfile.length());
			int count = pre.executeUpdate();

			System.out.println("isUpdated? " + count);

			pre.close();
                        s="working";

		} catch (Exception e) {
                    System.out.println(e.getMessage());
			e.printStackTrace();
                         s="not working";
		}
        return s;
    }*/
        s="records working fine";
        return s;
    }

    /**
     * PUT method for updating or creating an instance of ServiceResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
  
    @POST
    @Consumes("application/json")
    public String postJsonRrcords(String content)
    {
        String s = "lalal";
    //    Gson gson = new Gson();
    //    gson.toJson(s);
        return s;
    }
    @Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request,
            ContainerResponseContext response) throws IOException {
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }


    }
    
}
