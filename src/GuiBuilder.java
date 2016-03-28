import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuiBuilder extends JFrame {
    private Controller controller;
    private JLabel programNameLabel;

    public GuiBuilder(String s) {
        super(s);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[] info = new Controller().getInfo(Data.ip,Data.port,";"); //Information about available Relais
        System.out.println("Buttons detected: "+info.length);

        Container c = getContentPane();
        c.setLayout(new GridLayout(1+info.length,1));
        c.setForeground(Color.black);

        programNameLabel = new JLabel(" Relais Control Panel ",SwingConstants.CENTER);
        programNameLabel.setFont (programNameLabel.getFont ().deriveFont (32.0f));
        c.add(programNameLabel);

        controller = new Controller();

        JButton temp;
        for(int i=0 ; i<info.length ; i++) {
            temp = new JButton(info[i]);
            temp.setFont (temp.getFont ().deriveFont (16.0f));
            temp.addActionListener(e -> {
                String command = ((JButton)e.getSource()).getText();
                controller.sendCommand(command,Data.ip,Data.port);
            });
            c.add(temp);
        }

        setLocation(200,200);
        pack();
        setVisible(true);
    }
}
