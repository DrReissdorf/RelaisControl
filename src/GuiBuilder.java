import javax.swing.*;
import java.awt.*;

class GuiBuilder extends JFrame {
    private Controller controller;
    private JLabel programNameLabel;

    public GuiBuilder(String s) {
        super(s);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        controller = new Controller();
        String[] info = controller.getInfo(Data.ip,Data.tcpPort,";"); //Information about available Relais

        Container c = getContentPane();
        c.setLayout(new GridLayout(1+info.length,1));
        c.setForeground(Color.black);

        programNameLabel = new JLabel(" Relais Control Panel ",SwingConstants.CENTER);
        programNameLabel.setFont (programNameLabel.getFont ().deriveFont (32.0f));
        c.add(programNameLabel);

        JButton temp;
        for(int i=0 ; i<info.length ; i++) {
            temp = new JButton(info[i]);
            temp.setFont (temp.getFont ().deriveFont (16.0f));
            temp.addActionListener(e -> {
                String command = ((JButton)e.getSource()).getText();
                controller.sendCommand(command,Data.ip,Data.tcpPort);
            });
            c.add(temp);
        }

        setLocation(200,200);
        pack();
        setVisible(true);
    }
}
