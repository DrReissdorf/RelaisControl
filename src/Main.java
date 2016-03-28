import javax.swing.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        Data.ip = UdpDiscover.findIP("raspi",Data.udpPort); //request message raspi checks for
        new GuiBuilder("-> "+Data.ip).setVisible(true);
    }
}
