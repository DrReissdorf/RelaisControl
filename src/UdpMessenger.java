import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class UdpMessenger {

    public boolean sendCommand(String ip, int port, String command) {
        DatagramSocket socket;
        DatagramPacket sendPacket;
        InetAddress IPAddress;
        byte[] sendData;

        try{
            socket = new DatagramSocket();
            IPAddress = InetAddress.getByName(ip);
            sendData = command.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            socket.send(sendPacket);
            socket.close();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Warnung");
        }

        return true;
    }

    public String[] getInfo(String ip, int port, String splitRegex) {
        DatagramSocket socket;
        DatagramPacket sendPacket;
        DatagramPacket receivePacket;
        InetAddress IPAddress;
        byte[] sendData;
        byte[] recvData;

        try{
            socket = new DatagramSocket();
            IPAddress = InetAddress.getByName(ip);
            sendData = Data.getInfo_message.getBytes();
            recvData = new byte[1024];
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            socket.send(sendPacket);
            receivePacket = new DatagramPacket(recvData, recvData.length);
            socket.receive(receivePacket);
            socket.close();
            return new String(receivePacket.getData()).split(splitRegex);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Warnung");
        }

        return null;
    }
}
