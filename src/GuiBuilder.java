import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

class GuiBuilder extends JFrame {
    private HashMap<JButton,String> jButtonStringHashMap;
    private int cnt;
    private Controller controller;
    private JLabel programNameLabel;

    // Konstruktor zur Erzeugung der Grafik-Oberfläche:

    public GuiBuilder(String s) {
        super(s);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[] info = new Controller().getInfo(Data.ip,Data.port,";"); //Information about available Relais

        Container c = getContentPane();
        c.setLayout(new GridLayout(1+info.length/2,1));
        c.setForeground(Color.black);

        programNameLabel = new JLabel(" Relais Control Panel ",SwingConstants.CENTER);
        programNameLabel.setFont (programNameLabel.getFont ().deriveFont (32.0f));
        c.add(programNameLabel);
        controller = new Controller();

        jButtonStringHashMap = new HashMap<>();

        JButton temp;
        for(cnt=1 ; cnt<info.length ; cnt+=2) {
            temp = new JButton(info[cnt-1]);
            temp.setFont (temp.getFont ().deriveFont (16.0f));
            temp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String command = jButtonStringHashMap.get(e.getSource());
                    controller.sendCommand(command,Data.ip,Data.port);
                    System.out.println(command);
                }
            });
            jButtonStringHashMap.put(temp,info[cnt]);
            c.add(temp);
            if(cnt == info.length-1) break;
        }

        setLocation(200,200);
        pack();
        setVisible(true);

    }
}
