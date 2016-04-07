package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketComm {
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader bufferedReader;

    public SocketComm(Socket socket) {
        this.socket = socket;
        try {
            pw = new PrintWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String text) {
        pw.println(text);
        pw.flush();
    }

    public boolean sendCommand(String text) {
        send(text);
        String temp = null;

        temp = receive();

        if(temp == null) return false;

        if(temp.equals("command received")) {
            return true;
        } return false;
    }

    public String receive() {
        try {
            return bufferedReader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getIP() {
        return socket.getInetAddress().getHostAddress();
    }

    public String getHostname() {
        return socket.getInetAddress().getHostName();
    }
}
