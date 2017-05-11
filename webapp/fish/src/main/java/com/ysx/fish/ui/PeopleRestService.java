package com.ysx.fish.ui;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path( "/people" ) 
public class PeopleRestService {
	
	@Path( "/json" ) 
	@GET
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String userNameForJson(@FormParam("name") String name) {
        return "[{name:avalue:1},{name:bvalue:2},{name:cvalue:3},{name:dvalue:4},{name:evalue:5}]";  
    }
	
	@Path( "/xml" ) 
	@GET
    @Produces( MediaType.TEXT_XML )
    public String userNameForXML(@FormParam("name") String name) {
        return "<User>" + "<Name>" + name + "</Name>" + "</User>";
    }
	
	@Path( "/html" ) 
	@GET
    @Produces( MediaType.TEXT_HTML )
    public String userNameForHTML(@FormParam("name") String name) {  
		 return "<html><title>" + name + "</title><body><h1>" + name + "</h1></body>" + "</html> "; 
    }

}