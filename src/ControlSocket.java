import socket.SocketComm;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ControlSocket {
    private SocketComm socketComm;

    public ControlSocket() {
        try {
            socketComm = new SocketComm(new Socket(Data.ip, Data.TCP_CONTROL_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(String command) {
        socketComm.send(command);
    }
}
