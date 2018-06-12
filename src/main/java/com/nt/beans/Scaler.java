package com.nt.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rabbitmq.client.*;

public class Scaler {
	private static String userName = "docshifter";
	private static String pwd = "docshifter";
	private static String virtualHost = "/";
	private static String queueName = "docshifter";
	static int queuemessageSize;
	static int queueConsumerSize;
	static int millis = 300000;
	static int receiverCount;
	static int minReceivers = 1;
	static int maxReceivers = 10;

	// local change to localhost later
	/*
	 * private static String hostName = "192.168.99.100"; private static Integer
	 * portNumber = 5672; private static String IP = "http://192.168.99.100:2376";
	 */

	// K8S
	// private static String hostName ="35.233.33.42");
	// private static Integer portNumber =30021;

	// DockerCloud

	private static String hostName = "35.233.56.122";
	private static Integer portNumber = 4243;
	private static String IP = "http://" + hostName + ":" + portNumber;
	static ArrayList<Integer> rabbitList = new ArrayList<Integer>();

	public static void main(String[] args) {
		init();
	}

	public static void init() {

		// read from file
		// make log file
		// get db info from file made from env
		// check db for MQ info
		// log4j
		// check if all nodes are still operational and scale down if needed for
		// performance?????
		try {
			// checkHardware("p46jf5s3mkslpdpfp8s0h1g9w");
			while (true) {
				scaler();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void scaler() throws Exception {
		// main loop
		// compare file env and other stuff
		// unix socket

		JSONObject node;
		int maxReceivers = 5;

		// check rabbitmq
		rabbitList.add(checkRabbitMQ());
		if (rabbitList.size() > 6) {
			rabbitList.remove(0);
		}
		System.out.println(rabbitList);
		int sum = 0;
		for (Integer i : rabbitList) {
			sum += i;
		}
		double average = (double) sum / rabbitList.size();
		System.out.println(average);

		JSONObject serviceReceiver = checkServicesReceiver();
		String serviceID = serviceReceiver.getString("ID");

		// scale down faster
		if (rabbitList.get(rabbitList.size() - 1) == 0
				|| average < DockerConnection.getServiceReplicas(IP, serviceID)) {
			if (DockerConnection.getServiceReplicas(IP, serviceID) != 1) {
				System.out.println("SCALING DOWN");
				if (minReceivers < DockerConnection.getServiceReplicas(IP, serviceID)) {
					if (DockerConnection.getServiceScale(IP, serviceID, false)) {
						System.out.println("Scaled down");
					} else {
						System.out.println("Scaling down failed");
					}
				} else {
					System.out.println("Minimum receivers reached! Not scaling down further");
				}
			} else {
				System.out.println("Only 1 receiver running! Not scaling down further");
			}
		}

		// change scale up numbersv/ algoritm!
		else if (rabbitList.get(rabbitList.size() - 1) > average) {
			System.out.println("SCALING UP");
			// check nodes
			JSONArray nodes = new JSONArray(DockerConnection.getNodes(IP));
			if (DockerConnection.getServiceReplicas(IP, serviceID) < maxReceivers) {
				System.out.println("SCALING UP RECEIVER ALLOWED");
				if (nodes.length() > DockerConnection.getServiceReplicas(IP, serviceID)) {
					DockerConnection.getServiceScale(IP, serviceID, true);
					System.out.println("SCALING ACHIEVED");
				}
				// nodes zijn gelijke grote
				else {
					System.out.println("NEED TO ADD MORE RECEIVERS BASED ON CPU AND MEM");
					// scaleCheckingHardware();
				}
			} else {
				System.out.println("Reached max receivers!");
			}

		}
		// sleep
		System.out.println("Cycle completed");
		Thread.sleep(millis);
	}

	private static void scaleCheckingHardware() throws Exception {
		Map<String, Integer> nodeReceiver = new HashMap<String, Integer>();
		JSONArray tasks = new JSONArray(DockerConnection.getTasks(IP));

		for (int i = 0; i < tasks.length(); i++) {

		}
		/*
		 * JSONObject node; for (int i = 0; i < nodes.length(); i++) { node =
		 * nodes.getJSONObject(i);
		 * 
		 * float[] h = checkHardware(node.getString("ID")); check for mem and CPU on
		 * each node check if place for extra receiver
		 */
	}

	// check favor cpus and bytes
	public static float[] checkHardware(String nodeid) throws JSONException, Exception {
		System.out.println("HARDWARE");
		JSONArray nodes = new JSONArray(DockerConnection.getNodes(IP));
		JSONObject node;
		float[] hardware = new float[2];
		hardware[0] = 0;
		hardware[1] = 0;
		for (int i = 0; i < nodes.length(); i++) {
			node = nodes.getJSONObject(i);
			if (node.getString("ID").contains(nodeid)) {
				hardware[0] = node.getJSONObject("Description").getJSONObject("Resources").getInt("NanoCPUs")
						/ 1000000000;
				hardware[1] = node.getJSONObject("Description").getJSONObject("Resources")
						.getLong("MemoryBytes") / 1024 / 1024 / 1024;
			}
		}
		System.out.println(hardware[0] + ":" + hardware[1]);
		return hardware;
	}

	// check service for receiver
	public static JSONObject checkServicesReceiver() throws Exception {
		System.out.println("CheckReceivers");
		JSONArray services = new JSONArray(DockerConnection.getServices(IP));
		JSONObject service = null;
		for (int i = 0; i < services.length(); i++) {
			service = new JSONObject(DockerConnection.getServiceInspect(IP, services.getJSONObject(i).getString("ID")));
			// System.out.println(service);
			if (service.getJSONObject("Spec").getJSONObject("TaskTemplate").getJSONObject("ContainerSpec").has("Env")) {
				JSONArray env = service.getJSONObject("Spec").getJSONObject("TaskTemplate")
						.getJSONObject("ContainerSpec").getJSONArray("Env");
				if ((env.toString(0).toLowerCase().contains("ds_component=docshifterreceiver"))) {
					System.out.println("found receiver");
					return service;
				}
			}
		}
		return null;
	}

	// checkRabbitMQ queue
	public static int checkRabbitMQ() {
		System.out.println("Checking RMQ");
		ConnectionFactory factory = new ConnectionFactory();
		Connection conn;
		Channel channel;
		factory.setUsername(userName);
		factory.setPassword(pwd);
		factory.setVirtualHost(virtualHost);
		factory.setHost(hostName);
		factory.setPort(5672);

		try {
			conn = factory.newConnection();
			channel = conn.createChannel();
			queuemessageSize = channel.queueDeclarePassive(queueName).getMessageCount();
			System.out.println("queue size: " + queuemessageSize);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queuemessageSize;
	}
}