package com.nt.beans;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author rapha
 * @version 0.1
 * @
 */

public class DockerConnection {
	// add file to output to ?
	// log 4j import

	// transforms reader to string
	private static String transformIntoString(Reader in) {

		StringBuilder sb = new StringBuilder();

		try {
			for (int c; (c = in.read()) >= 0;)
				sb.append((char) c);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String response = sb.toString();
		return response;
	}

	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @param ID
	 *            ID of the service needed to scale up
	 * @return String containing JSON Object, or errorcode Needs to be changed
	 * @throws Exception
	 *             General Exception needs to be changed
	 */
	public static String getContainers(String IP) throws Exception {
		try {
			String fullLink = IP + "/containers/json";
			URL url = new URL(fullLink);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			// System.out.print(conn.getResponseMessage());
			return transformIntoString(in);

		} finally {
		}
	}

	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @param ID
	 *            ID of the service needed to scale up
	 * @return String containing JSON Object, or errorcode Needs to be changed
	 * @throws Exception
	 *             General Exception needs to be changed
	 */
	public static String getContainersByID(String IP, String ID) throws Exception {
		try {
			String fullLink = IP + "/containers/" + ID + "/json";
			URL url = new URL(fullLink);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			return transformIntoString(in);

		} catch (FileNotFoundException E) {
			System.out.println("Container ID not found on server");
			return "File not found";
		}
	}

	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @param ID
	 *            ID of the service needed to scale up
	 * @return String containing JSON Object, or errorcode Needs to be changed
	 * @throws Exception
	 *             General Exception needs to be changed
	 */
	public static String getContainerStats(String IP, String ID) throws Exception {
		try {
			String fullLink = IP + "/containers/" + ID + "/stats?stream=false";
			URL url = new URL(fullLink);
			System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			System.out.println("Requesting");
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			return transformIntoString(in);

		} catch (FileNotFoundException E) {
			System.out.println("Container ID not found on server");
			return "File not found";
		}
	}

	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @return String containing JSON Object, or errorcode Needs to be changed
	 * @throws Exception
	 *             General Exception needs to be changed
	 */
	public static String getImages(String IP) throws Exception {
		try {
			String fullLink = IP + "/images/json";
			URL url = new URL(fullLink);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			return transformIntoString(in);

		} catch (FileNotFoundException E) {
			System.out.println("Container ID not found on server");
			return "File not found";
		}
	}

	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @return String containing JSON Object, or errorcode Needs to be changed
	 * @throws Exception
	 *             General Exception needs to be changed
	 */
	public static String getSwarm(String IP) throws Exception {
		try {
			String fullLink = IP + "/swarm";
			URL url = new URL(fullLink);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			return transformIntoString(in);

		} catch (FileNotFoundException E) {
			System.out.println("Container ID not found on server");
			return "File not found";
		}
	}

	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @return String containing JSON Object, or errorcode Needs to be changed
	 * @throws Exception
	 *             General Exception needs to be changed
	 */
	public static String getNodes(String IP) throws Exception {
		try {
			String fullLink = IP + "/nodes";
			//System.out.println(fullLink);
			URL url = new URL(fullLink);
			//System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			return transformIntoString(in);

		} catch (FileNotFoundException E) {
			System.out.println("Container ID not found on server");
			return "File not found";
		}
	}
	
	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @return String containing JSON Object, or errorcode Needs to be changed
	 * @throws Exception
	 *             General Exception needs to be changed
	 */
	public static String getTasks(String IP) throws Exception {
		try {
			String fullLink = IP + "/tasks";
			System.out.println(fullLink);
			URL url = new URL(fullLink);
			System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			return transformIntoString(in);

		} catch (FileNotFoundException E) {
			System.out.println("Container ID not found on server");
			return "File not found";
		}
	}

	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @param ID
	 *            ID of the service needed to scale up
	 * @return String containing JSON Object, or errorcode Needs to be changed
	 * @throws Exception
	 *             General Exception needs to be changed
	 */
	public static String getInspectNode(String IP, String ID) throws Exception {
		try {
			String fullLink = IP + "/nodes/" + ID;
			URL url = new URL(fullLink);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			return transformIntoString(in);

		} catch (FileNotFoundException E) {
			System.out.println("Container ID not found on server");
			return "File not found";
		}
	}

	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @return String containing JSON Object, or errorcode Needs to be changed
	 * @throws Exception
	 *             General Exception needs to be changed
	 */
	public static String getServices(String IP) throws Exception {
		try {
			String fullLink = IP + "/services";
			URL url = new URL(fullLink);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			return transformIntoString(in);

		} finally {
		}
	}

	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @param ID
	 *            ID of the service needed to scale up
	 * @return String containing JSON Object, or errorcode Needs to be changed
	 * @throws Exception
	 *             General Exception needs to be changed
	 */
	public static String getServiceInspect(String IP, String ID) throws Exception {
		if (getPing(IP) == 200) {
			try {
				String fullLink = IP + "/services/" + ID;
				URL url = new URL(fullLink);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				return transformIntoString(in);

			} catch (FileNotFoundException E) {
				System.out.println("Container ID not found on server");
				return "File not found";
			}
		} else {
			// write to log file
			return "can't access API";
		}
	}

	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @param ID
	 *            ID of the service needed to scale up
	 * @param up
	 *            Scale the service up (true) or down (false)
	 * @return returns true if succesfully scaled, false if unsuccesful needs to be
	 *         changed
	 * @throws Exception
	 *             needs to be changed
	 */
	public static boolean getServiceScale(String IP, String ID, boolean up) throws Exception {
		if (getPing(IP) == 200) {
			// get service
			JSONObject service;
			try {
				service = new JSONObject(getServiceInspect(IP, ID));
			} catch (Exception E) {
				// write to log file
				return false;
			}

			// make link to connect to
			URL url;
			try {
				String fullLink = IP + "/v1.37/services/" + ID + "/update?version="
						+ service.getJSONObject("Version").get("Index");
				url = new URL(fullLink);
			} catch (Exception E) {
				// write to log
				return false;
			}

			// create JSONObjects to work with
			JSONObject json = new JSONObject();
			JSONObject replicas = new JSONObject();

			// check if scaling down or up
			try {
				if (up) {
					replicas = service.getJSONObject("Spec").getJSONObject("Mode").getJSONObject("Replicated")
							.increment("Replicas");
				} else {
					int scaledown;
					scaledown = (Integer) service.getJSONObject("Spec").getJSONObject("Mode")
							.getJSONObject("Replicated").getInt("Replicas")-1;
					if (scaledown != 0)
					replicas.put("Replicas", scaledown);
					else {return false;}
				}
			} catch (Exception E) {
				// write to log file
				return false;
			}

			// create JSONObject to send to DockerDaemon to scale up/down
			JSONObject spec = service.getJSONObject("Spec");
			JSONObject replicated = new JSONObject();
			json.put("TaskTemplate", spec.getJSONObject("TaskTemplate"));
			replicated.put("Replicated", replicas);
			json.put("Mode", replicated);
			json.put("Name", service.getJSONObject("Spec").get("Name"));
			json.put("EndpointSpec", service.getJSONObject("Spec").get("EndpointSpec"));
			json.put("Labels", spec.get("Labels"));

			// create conn
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			// write to httpconnection
			System.out.println(json);
			OutputStream outStream = conn.getOutputStream();
			outStream.write(json.toString().getBytes("UTF-8"));
			outStream.close();

			//System.out.println(				conn.getResponseCode());
			
			// check conn if succesfully scaled
			if (conn.getResponseCode() == 200) {
				// write to log file
				conn.disconnect();
				return true;
			} else {
				// write to log file

				Reader in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
				System.out.println(transformIntoString(in));
				conn.disconnect();
				return false;
			}
		} else {
			// write to log file
			return false;
		}
	}
	
	/**
	 * @param IP
	 *            Ip adress to send the API call to, including port
	 * @param ID
	 *            ID of the service needed to scale up
	 * @param up
	 *            Scale the service up (true) or down (false)
	 * @return returns true if succesfully scaled, false if unsuccesful needs to be
	 *         changed
	 * @throws Exception
	 *             needs to be changed
	 */
	public static int getServiceReplicas(String IP, String ID) throws Exception {
		if (getPing(IP) == 200) {
			// get service
			JSONObject service;
			try {
				service = new JSONObject(getServiceInspect(IP, ID));
			} catch (Exception E) {
				// write to log file
				return 0;
			}

			// make link to connect to
			URL url;
			try {
				String fullLink = IP + "/v1.37/services/" + ID + "/update?version="
						+ service.getJSONObject("Version").get("Index");
				url = new URL(fullLink);
			} catch (Exception E) {
				// write to log
				return 0;
			}

			// create JSONObjects to work with
			JSONObject json = new JSONObject();
			JSONObject replicas = new JSONObject();
			try {
				
				return (Integer) service.getJSONObject("Spec").getJSONObject("Mode")
						.getJSONObject("Replicated").get("Replicas");
				
			} catch (Exception E) {
				// write to log file
				return 0;
			}
	}
		else {return 0;}
}

	/**
	 * @param IP
	 *            Ip to ping to and test docker daemon, including port
	 * @return int basic http code or 0 if something went wrong
	 * @throws Exception
	 *             I/O Exception or general Exception needs to be changed
	 */
	public static int getPing(String IP) throws Exception {
		// create ip
		String fullLink = IP + "/_ping";
		URL url = new URL(fullLink);
		// make connection
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		try {
			if (conn.getResponseCode() == 200) {
				// write to log file
				return 200;
			} else if (conn.getResponseCode() == 401) {
				// write to log file
				return 401;
			} else if (conn.getResponseCode() == 404) {
				// write to log file
				return 404;
			} else if (conn.getResponseCode() == 500) {
				// write to log file
				return 500;
			} else {
				// write to log file
				Reader in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
				System.out.println(transformIntoString(in));
				return 0;
			}
		} finally {
			conn.disconnect();
		}
	}
		
	public static String rmq() throws Exception {
		
			try {
				String fullLink = "http://192.168.99.100/api/consumers";
				URL url = new URL(fullLink);
				String userpass = "docshifter:docshifter";
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				String basicAuth = "Basic " + userpass.getBytes();
				conn.setRequestProperty("Authorization", basicAuth);
				conn.setRequestMethod("GET");
				Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				return transformIntoString(in);

			} catch (FileNotFoundException E) {
				System.out.println("Container ID not found on server");
				return "File not found";
			}
		} 
	}