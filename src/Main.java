import socket.SocketComm;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        if(args.length == 0) Data.ip = UdpDiscover.findIP(Data.DISCOVER_MSG,Data.UDP_DISCOVER_PORT); //request message raspi checks for
        else Data.ip = args[0];

        SocketComm controlConnection = null;
        SocketComm statusConnection = null;

        try {
            controlConnection = new SocketComm(new Socket(Data.ip, Data.TCP_CONTROL_PORT));
            statusConnection = new SocketComm(new Socket(Data.ip, Data.TCP_STATUS_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new GuiBuilder("Relais Control Panel",controlConnection,statusConnection).setVisible(true);
    }
}
