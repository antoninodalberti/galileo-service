package storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import com.google.gson.Gson;

import bean.DB;
import bean.Landmark;
import bean.Macroarea;

public class JsonStorage {
	final private static String path = "/Users/nino/Workspace/GalileoServer/DB/db.json";

	private static JsonStorage currentStorage;
	private DB db = null;

	/**
	 * @return the currentStorage
	 */
	public static JsonStorage getJsonStorage() {
		if (currentStorage == null)
			currentStorage = new JsonStorage();
		return currentStorage;
	}

	public DB getDB() {
		if (db == null) {
			try {
				String contents = new String(Files.readAllBytes(Paths.get(path)));
				db = new Gson().fromJson(contents, DB.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return db;
	}

	public void saveDB() {
		String json = new Gson().toJson(db);
		OpenOption[] options = new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.WRITE };
		try {
			Files.write(Paths.get(path), json.getBytes(), options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	synchronized public void incrementVisits(ArrayList<String> labels) {
		DB db = getDB();
		ArrayList<Macroarea> macroareas = db.macroareas;
		for (Macroarea m : macroareas) {
			for (Landmark l : m.landmarks) {
				if (labels.contains(l.beacon.label)) {
					l.visits++;
				}
			}
		}
		saveDB();
	}
}
