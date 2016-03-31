import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ControlSocket {
    private JFrame optionPaneFrame; //fuer popups
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public ControlSocket() {
        optionPaneFrame = new JFrame();
        connectSocket();
    }

    private boolean connectSocket() {
        try {
            socket = new Socket(Data.ip, Data.tcp_control_port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
            System.out.println("\nConnected to "+socket.getInetAddress().getHostAddress());
            return true;
        } catch (IOException e) {
            try {
                Data.ip = UdpDiscover.findIP(Data.discover_message,Data.udp_discover_port);
                socket = new Socket(Data.ip, Data.tcp_control_port);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter = new PrintWriter(socket.getOutputStream());
                System.out.println("\nConnected to "+socket.getInetAddress().getHostAddress());
                return true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(optionPaneFrame, "No answer from server.\nClosing program");
                System.exit(-1);
                e.printStackTrace();
                return false;
            }
        }
    }

    public void sendCommand(String command) {
        printWriter.println(command);
        printWriter.flush();
    }

    public String[] getInfo() {
        String[] ret;

        try {
            printWriter.println(Data.getInfo_message);
            printWriter.flush();

            ret = bufferedReader.readLine().split(";");

            return ret;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(optionPaneFrame, "No answer from server.\nClosing program");
            System.exit(-1);
            e.printStackTrace();
        }

        return null;
    }
}