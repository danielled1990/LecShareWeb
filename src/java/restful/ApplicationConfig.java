/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Danielle
 */
@javax.ws.rs.ApplicationPath("Actions")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(restful.Account.class);
        resources.add(restful.Navigator.class);
        resources.add(restful.Records.class);
        resources.add(restful.Records.CORSFilter.class);
        resources.add(restful.ServiceResource.class);
        resources.add(restful.pdfstamps.class);
    }
    
}
