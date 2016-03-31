import javax.swing.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        if(args.length == 0) Data.ip = UdpDiscover.findIP(Data.DISCOVER_MSG,Data.UDP_DISCOVER_PORT); //request message raspi checks for
        else Data.ip = args[0];

        new GuiBuilder("Relais Control Panel").setVisible(true);
    }
}
