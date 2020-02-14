package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FindConfigServerProps {
	private final static String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {
		Process process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
		process.waitFor();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String branch = reader.readLine();
		System.out.println(branch);
		FindConfigServerProps.sendGet(String.join("/", "http://localhost:8080", "api-client-" + branch + ".yml"));
	}

	public static void sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// 默认值我GET
		con.setRequestMethod("GET");

		// 添加请求头
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// 打印结果
		System.out.println(response.toString());
	}
}
