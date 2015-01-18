package chococraft.common.utils;

import com.google.gson.Gson;
import cpw.mods.fml.common.FMLLog;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

public class UpdateChecker implements Runnable {

	private final static String updateURL = "http://haxyshideout.co.uk/mods/chococraft/update.json";
	private final static Gson gson = new Gson();
	private static UpdateResponse response;

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

	public static boolean isVersionHigher(String latestVersion, String currentVersion) {
		int result = versionCompare(latestVersion, currentVersion);
		if(result == 1)
			return true;
		return false;
	}

	/**
	 * Compares two version strings.
	 *
	 * Use this instead of String.compareTo() for a non-lexicographical
	 * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
	 *
	 * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
	 *
	 * @param str1 a string of ordinal numbers separated by decimal points.
	 * @param str2 a string of ordinal numbers separated by decimal points.
	 * @return The result is a negative integer if str1 is _numerically_ less than str2.
	 *         The result is a positive integer if str1 is _numerically_ greater than str2.
	 *         The result is zero if the strings are _numerically_ equal.
	 */
	public static Integer versionCompare(String str1, String str2)
	{
		String[] vals1 = str1.split("\\.");
		String[] vals2 = str2.split("\\.");
		int i = 0;
		// set index to first non-equal ordinal or length of shortest version string
		while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i]))
		{
			i++;
		}
		// compare first non-equal ordinal number
		if (i < vals1.length && i < vals2.length)
		{
			int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
			return Integer.signum(diff);
		}
		// the strings are equal or one string is a substring of the other
		// e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
		else
		{
			return Integer.signum(vals1.length - vals2.length);
		}
	}



}
