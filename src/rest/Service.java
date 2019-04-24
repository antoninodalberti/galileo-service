package rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import bean.DB;
import bean.DBVersion;
import bean.Result;
import bean.Visits;
import storage.JsonStorage;

@ApplicationPath("/") @Path("/")
public class Service extends Application {
	
	/*
	url for json DB : https://jsonblob.com/40440c40-ff09-11e8-8917-215984a6b448
	put content in the string
	String db = "";
	
	To init the json File on the server
	@GET @Path("/init") @Produces(MediaType.APPLICATION_JSON)
	public Result init() {
		JsonStorage jsonStor = JsonStorage.getJsonStorage();
		jsonStor.saveDB(new Gson().fromJson(db, DB.class));
		Result res = new Result();
		res.success = true;
		return res;
	}*/


	@GET @Path("/hi") @Produces(MediaType.TEXT_HTML)
	public String sayHello() {
		return "<html> " + "<title>" + "Hello World RESTful Jersey" + "</title>" + "<body><h1>"
				+ "Hello World RESTful Jersey" + "</body></h1>" + "</html> ";
	}
	
	@GET @Path("/getDBVersion") @Produces(MediaType.APPLICATION_JSON)
	public DBVersion getDBVersion() {
		DBVersion dbv = new DBVersion();
		dbv.version = JsonStorage.getJsonStorage().getDB().version;
		return dbv;
	}
	
	@GET @Path("/getMacroareas") @Produces(MediaType.APPLICATION_JSON)
	public DB getMacroareas() {
		return JsonStorage.getJsonStorage().getDB();
	}
	
	@POST @Path("/updateStatistics") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
	public Result updateStatistics(Visits visits) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				JsonStorage.getJsonStorage().incrementVisits(visits.labels);
			}
		}).start();
		Result res = new Result();
		res.success = true;
		return res;
	}
}
