package rest;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import bean.Result;
import bean.Visits;

@ApplicationPath("/")
public class Service extends Application {

	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(Arrays.asList(Service.class));
	}

	@GET @Path("/hi") @Produces(MediaType.TEXT_HTML)
	public String sayHello() {
		return "<html> " + "<title>" + "Hello World RESTful Jersey" + "</title>" + "<body><h1>"
				+ "Hello World RESTful Jersey" + "</body></h1>" + "</html> ";
	}
	
	@POST @Path("/putStatistics") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
	public Result putStatistics(Visits visits) {
		Result res = new Result();
		res.success = true;
		return res;
	}
}
