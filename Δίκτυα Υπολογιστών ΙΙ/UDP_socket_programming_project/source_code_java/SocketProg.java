package userApplication;


import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketProg {
	public static void main(String[] args) throws Exception {
        int serverPort = 38036;
        int clientPort = 48036;
        String clientIP = "192.168.1.81";
        String serverIP = "155.207.18.208";
        String echoCode = "E6810";
        String echoCode_T = "E6810T00";
        String imageCode = "M6608";
        String imageCodeCam = "M6608CAM=PTZ";
        String audioCode = "A4398";
        
        Scanner scanner = new Scanner(System.in);

        
        int userChoice = 0;
        // printing choices
        System.out.println("1. Create Echo with delay");
        System.out.println("2. Create Echo without delay");
        System.out.println("3. Create Temperatures");
        System.out.println("4. Create Image E1");
        System.out.println("5. Create Image E2");
        System.out.println("Choose an operation: ");
        
        // get user choice
        userChoice = Integer.parseInt(scanner.nextLine());
        
        
        if (userChoice == 1 || userChoice == 2) {
        	echo(clientIP, clientPort, serverIP, serverPort, echoCode, userChoice);
        	
        }
        else if(userChoice == 3){
        	temperature(clientIP, clientPort, serverIP, serverPort, echoCode_T);
        }
        else if(userChoice == 4 || userChoice == 5) {
        	image(clientIP, serverPort, serverIP, serverPort, imageCode, imageCodeCam, userChoice);
        }
        
        
    
        scanner.close();
	}


	public static void temperature(String clientIP, int clientPort, String serverIP, int serverPort, String echoCode_T) throws Exception {

	    File statsFile = new File("src/statistics_" + echoCode_T + ".txt");
	    DatagramSocket clientSocket = new DatagramSocket(clientPort);
        InetAddress serverAddress = InetAddress.getByName(serverIP);
	    try (PrintWriter writer = new PrintWriter(statsFile)) {
	            byte[] sendData = echoCode_T.getBytes();
	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
	            byte[] receiveData = new byte[1024];
	            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

	            clientSocket.send(sendPacket);
	            clientSocket.receive(receivePacket);

	            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
	            String output = String.format("Packet: %s%n", response);
	            System.out.print(output);
	            writer.print(output);
	    }
	    
	    clientSocket.close();
	}



	public static void echo(String clientIP, int clientPort, String serverIP, int serverPort, String echoCode, int userChoice) throws Exception {
	    
		String message = "";
		if(userChoice == 1) {
			message = echoCode;
		}
		else if(userChoice == 2) {
			message = "E0000";
		}
		
		DatagramSocket clientSocket = new DatagramSocket(clientPort);
		

	    InetAddress serverAddress = InetAddress.getByName(serverIP);
	    byte[] sendData = message.getBytes();
	    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

	    byte[] receiveData = new byte[1024];
	    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

	    int packetCount = 0;
	    long startTime = System.currentTimeMillis();
	    long endTime = startTime + 4 * 60 * 1000;
	    long totalDelayTime = 0;

	    File statsFile = new File("src/statistics_" + message + ".txt");
	    File throughputFile = new File("src/throughput" + message + ".txt");
	    try (PrintWriter writer = new PrintWriter(statsFile);
	         PrintWriter throughputWriter = new PrintWriter(new FileOutputStream(throughputFile, true))) {

	        long lastThroughputTime = System.currentTimeMillis();
	        int packetsSinceLastThroughput = 0;

	        while (System.currentTimeMillis() < endTime) {
	            long sendTime = System.currentTimeMillis();
	            clientSocket.send(sendPacket);
	            packetCount++;

	            receivePacket.setData(new byte[1024]);
	            clientSocket.receive(receivePacket);

	            long receiveTime = System.currentTimeMillis();
	            long delayTime = receiveTime - sendTime;
	            totalDelayTime += delayTime;

	            packetsSinceLastThroughput++;

	            if (System.currentTimeMillis() - lastThroughputTime >= 8000) {
	                double throughput8 = (double) packetsSinceLastThroughput / 8;
	                String throughputOutput = String.format("Packet count: %d, Throughput: %.2f pps%n", packetCount, throughput8);
	                System.out.print(throughputOutput);
	                throughputWriter.print(throughputOutput);
	                lastThroughputTime = System.currentTimeMillis();
	                packetsSinceLastThroughput = 0;
	            }

	            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
	            String output = String.format("Packet %d: delay_time=%dms, response=%s%n", packetCount, delayTime, response);
	            System.out.print(output);
	            writer.print(output);

	        }

	        long duration = System.currentTimeMillis() - startTime;
	        double avgDelayTime = (double) totalDelayTime / packetCount;

	        writer.printf("Packets sent: %d%n", packetCount);
	        writer.printf("Total duration: %dms%n", duration);
	        writer.printf("Average delay time: %.2fms%n", avgDelayTime);
	    }

	    clientSocket.close();
	}
	





		public static void image(String clientIP, int clientPort, String serverIP, int serverPort,String imageCode,String imageCodeCam, int userChoice) throws UnknownHostException, SocketException {
        byte[] sendData;
        byte[] image = new byte[70000];
        int bytesRead;
        bytesRead=0;
        String message = "";
        
        InetAddress clientAddress = InetAddress.getByName(clientIP);
        InetAddress serverAddress = InetAddress.getByName(serverIP);
        
        if (userChoice == 4) {
        	message = imageCode;
        }
        else if(userChoice == 5) {
        	message = imageCodeCam;
        }
        
        sendData = message.getBytes();

        byte[] receiveData = new byte[128];

        DatagramSocket clientSock = new DatagramSocket(clientPort, clientAddress);
        clientSock.setSoTimeout(3600);

        DatagramPacket request = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        DatagramPacket response = new DatagramPacket(receiveData, receiveData.length);


        String requestmsg = new String (sendData, 0, request.getLength());
        System.out.println("Request for: " + requestmsg);

        boolean packet_start = false;
        boolean packet_finished = false;

        for (int j=0; j < 2000; j++) {
            //System.out.print("Number of packets requested: ");
            //System.out.println(j+1);
            try {
                clientSock.send(request);
                clientSock.receive(response);
            }catch (Exception x) {
                System.out.println(x);
            }
            for (int i=0; i < receiveData.length; i ++) {
                if (packet_start) {
                    if (i<127) {
                        if (receiveData[i] == -1 && receiveData[i + 1] == -39) {
                            image[bytesRead] = -1;
                            image[bytesRead+1] = -39;
                            System.out.println("Found end of image delimiter");
                            System.out.print("Total bytes read: ");
                            System.out.println(bytesRead+1);
                            packet_finished = true;
                            break;
                        }
                        else {
                            image[bytesRead] = receiveData[i];
                            bytesRead ++;
                        }
                    }
                    else {
                        image[bytesRead] = receiveData[i];
                        bytesRead ++;
                    }
                }
                else { //this else statement is only executed before we find the start of image delimiter
                    if (i<127) {
                        if (receiveData[i] == -1 && receiveData[i + 1] == -40) {
                            System.out.println("Found start of image delimiter");
                            image[0] = -1;// storing the 2 bytes of the start delimiter in the
                            image[1] = -40;
                            bytesRead = 2;
                            i ++; // in order to avoid the "-40"
                            packet_start = true;  // this is set to true in order to not get in this "else" statement
                            continue;
                        }
                    }
                }
            }
            if (packet_finished) {
            	break;
            }
        }
        clientSock.close();
        File f = new File("src/image_" + message + ".jpeg");
        try(FileOutputStream fos = new FileOutputStream(f)){
            fos.write(image);
            fos.close();
            System.out.println("Image response saved to file: image_" + message + ".jpeg ");
        }catch (Exception x) {
            System.out.println(x);
        }
        
    }
}