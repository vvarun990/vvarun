/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APP;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Sangram
 */
@ApplicationPath("/api")
public class AppConfig extends ResourceConfig {
    
    public AppConfig(){
        packages("main.java");
        
        
    }
}
