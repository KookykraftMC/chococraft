package chococraft.common.utils;

import com.google.gson.Gson;
import cpw.mods.fml.common.FMLLog;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.util.List;

public class UpdateChecker implements Runnable {

	private final static String updateURL = "http://haxyshideout.co.uk/mods/chococraft/update.json";
	private final static Gson gson = new Gson();
	private static UpdateResponse response;

	public static void main(String[] args) throws Exception {
		UpdateChecker updateChecker = new UpdateChecker();
		new Thread(updateChecker).start();
	}

	@Override
	public void run() {
		try {
			String jsonString = IOUtils.toString(new URL(updateURL));
			response = gson.fromJson(jsonString, UpdateResponse.class);
		} catch (Exception e) {
			FMLLog.warning("If you see a error just below this line, its prob fine to ignore <3 chococraft");
			e.printStackTrace();
		}
	}

	public static UpdateResponse getResponse() {
		return response;
	}

	public class UpdateResponse {
		public List<VersionInfo> versions;
	}

	public class VersionInfo {
		public String modversion;
		public List<String> changes;
	}

}
