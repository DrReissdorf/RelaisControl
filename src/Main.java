import javax.swing.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        Data.discover_message = "jinhi-pc";

        if(args.length == 0) Data.ip = UdpDiscover.findIP(Data.discover_message,Data.udp_discover_port); //request message raspi checks for
        else Data.ip = args[0];

        new GuiBuilder("Relais Control Panel").setVisible(true);
    }
}
