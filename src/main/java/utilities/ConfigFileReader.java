package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

	private Properties prop;
	private final String propertyFilePath = "src/test/resources/testdata.properties";

	public ConfigFileReader() throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propertyFilePath);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
	}

	public String getURL() {
		String url = prop.getProperty("end_point_url");
		return url;
	}

	public String getURLforUserDetails() {
		String url = prop.getProperty("userDetailsURL");
		return url;
	}

	public String getURLfortoDo() {
		String url = prop.getProperty("toDoURL");
		return url;
	}


}
