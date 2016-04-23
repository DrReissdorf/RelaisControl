import socket.SocketComm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

class GuiBuilder extends JFrame {
    private ArrayList<JButton> buttons;
    private HashMap<String, JLabel> labelHashMap;
    private Container c;
    private String[] info;

    private SocketComm controlConnection;

    public GuiBuilder(String s, SocketComm controlConnection) {
        super(s);
        System.out.println("GuiBuilder()");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        buttons = new ArrayList<>();
        labelHashMap = new HashMap<>();

        c = getContentPane();
        c.setLayout(new GridLayout(0,2));
        c.setForeground(Color.black);

        this.controlConnection = controlConnection;

        initButtonsAndStatusLabels();

        new StatusThread().start();

        setLocation(200,200);
        pack();
        setVisible(true);
    }

    private void initButtonsAndStatusLabels() {
        System.out.println("initButtonsAndStatusLabels()");
        JButton tempButton;
        JLabel tempLabel;
        String[] tempString;
        String[] info;
        String[] message;

        message = controlConnection.receive().split("%");

        if (message[0].equals("relay")) {
            info = message[1].split(";");

            for(int i=0 ; i<info.length ; i++) {
                tempString = info[i].split(",");

                if(!isButtonAlreadyInList(tempString[0],buttons)) {
                    tempButton = new JButton(tempString[0]);
                    tempButton.setFont (tempButton.getFont ().deriveFont (16.0f));
                    tempButton.addActionListener(e -> {
                        String command = ((JButton)e.getSource()).getText();
                        System.out.println("Sent command: "+command);
                        controlConnection.send("relay%"+command);
                    });
                    buttons.add(tempButton);

                    tempLabel = new JLabel();

                    if(Boolean.parseBoolean(tempString[1])) tempLabel.setBackground(Color.GREEN);
                    else tempLabel.setBackground(Color.RED);
                    tempLabel.setOpaque(true);
                    tempLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                    labelHashMap.put(tempString[0],tempLabel);
                    c.add(tempButton);
                    c.add(tempLabel);
                }
            }
        }
    }

    private void updateLabels(String[] info) {
        System.out.println("updateLabels()");
        JLabel tempLabel;
        String[] tempString;
        for(int i=0 ; i<info.length ; i++) {
            tempString = info[i].split(",");
            tempLabel = labelHashMap.get(tempString[0]);
            if(Boolean.parseBoolean(tempString[1])) tempLabel.setBackground(Color.GREEN);
            else tempLabel.setBackground(Color.RED);
        }
    }

    private boolean isButtonAlreadyInList(String buttonText, ArrayList<JButton> buttons) {
        for(JButton b : buttons) {
            if(b.getText().equals(buttonText)) return true;
        }
        return false;
    }

    public class StatusThread extends Thread {
        public void run() {
            try {
                while(true) {
                    String[] message = controlConnection.receive().split("%");

                    System.out.println("received from server: "+message[0]+"%"+message[1]);
                    if(message[0].equals("relay")) {
                        info = message[1].split(";");
                        updateLabels(info);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), "No answer from server.\nClosing program");
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

}
