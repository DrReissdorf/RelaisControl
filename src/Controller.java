import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Controller {
    private JFrame optionPaneFrame; //fuer popups

    public Controller() {
        optionPaneFrame = new JFrame();
    }

    private Socket connectSocket(String ip, int port) {
        try {
            return new Socket(Data.ip, 18745);
        } catch (IOException e) {
            try {
                Data.ip = UdpDiscover.findIP(Data.discover_message,Data.udpPort);
                return new Socket(Data.ip, 18745);
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
        Socket socket = null;
        try {
            socket = connectSocket(ip, port);
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
        Socket socket = null;
        String[] ret;

        try {
            socket = connectSocket(ip, port);
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
