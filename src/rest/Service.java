package rest;

import java.util.ArrayList;

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
import bean.Landmark;
import bean.Macroarea;
import bean.Result;
import bean.Visits;
import storage.JsonStorage;

@ApplicationPath("/") @Path("/")
public class Service extends Application {
	
	
	//url for json DB : https://jsonblob.com/40440c40-ff09-11e8-8917-215984a6b448
	
	//To init the json File on the server
	@POST @Path("/init") @Consumes(MediaType.APPLICATION_JSON) @Produces(MediaType.APPLICATION_JSON)
	public Result init(DB db) {
		JsonStorage jsonStor = JsonStorage.getJsonStorage();
		jsonStor.putDB(db);
		Result res = new Result();
		res.success = true;
		return res;
	}

	@GET @Path("/hi") @Produces(MediaType.TEXT_HTML)
	public String sayHello() {
		return "<html> " + "<title>" + "Hello World RESTful Jersey" + "</title>" + "<body><h1>"
				+ "Hello World RESTful Jersey" + "</h1></body>" + "</html> ";
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
	
	@GET @Path("/statistics") @Produces(MediaType.TEXT_HTML) @Consumes(MediaType.APPLICATION_JSON)
	public String showStatistics() {
		DB db = JsonStorage.getJsonStorage().getDB();
		ArrayList<Macroarea> macroareas = db.macroareas;
		String s = "<html> " + "<title>" + "Statistiche" + "</title>" + "<head>" +
		"<link href='http://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>" +
		"</head>" + "<body style=\"background-color: #EC1B28; font-family: Lato; color: white;\">" + 
		"<h1>" + "Numero di visite ai luoghi GalileoPisaTour" + "</h1>";
		
		for (Macroarea m : macroareas) {
			for (Landmark l : m.landmarks) {
				s += "<p>"+ l.name + ": " + Integer.toString(l.visits) + " volte</p>";
			}
		}
		s += "</body></html>";
	
		return s;
	}
}
