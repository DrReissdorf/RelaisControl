import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Controller {
    private JFrame optionPaneFrame; //fuer popups
    private Socket socket;

    public Controller() {
        optionPaneFrame = new JFrame();
        socket = connectSocket();
    }

    private Socket connectSocket() {
        try {
            return new Socket(Data.ip, Data.tcpPort);
        } catch (IOException e) {
            try {
                Data.ip = UdpDiscover.findIP(Data.discover_message,Data.udpPort);
                return new Socket(Data.ip, Data.tcpPort);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(optionPaneFrame, "No answer from server.\nClosing program");
                System.exit(-1);
                e.printStackTrace();
            }
        }

        return null;
    }

    public boolean sendCommand(String command, String ip, int port) {
        PrintWriter outToServer = null;
        try {
            outToServer = new PrintWriter(socket.getOutputStream());
            outToServer.println(command);
            outToServer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String[] getInfo(String ip, int port, String splitRegex) {
        PrintWriter outToServer = null;
        BufferedReader inFromServer = null;
        String[] ret;

        try {
            outToServer = new PrintWriter(socket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            outToServer.println("info");
            outToServer.flush();

            ret = inFromServer.readLine().split(splitRegex);

            System.out.println("Activated relais detected: "+ret.length);

            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
