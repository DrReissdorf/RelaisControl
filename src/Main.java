import javax.swing.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //GUI gui = new GUI(400,200);
        //gui.setVisible(true);
        new UdpDiscover().find();
        new GuiBuilder("Relais Control Panel").setVisible(true);
    }
}
