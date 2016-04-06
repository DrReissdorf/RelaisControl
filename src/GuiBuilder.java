import socket.SocketComm;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

class GuiBuilder extends JFrame {
    private ArrayList<JButton> buttons;
    private HashMap<String, JLabel> labelHashMap;
    private Container c;

    private SocketComm controlConnection;
    private SocketComm statusConnection;

    public GuiBuilder(String s, SocketComm controlConnection, SocketComm statusConnection) {
        super(s);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        buttons = new ArrayList<>();
        labelHashMap = new HashMap<>();

        c = getContentPane();
        c.setLayout(new GridLayout(0,2));
        c.setForeground(Color.black);

        this.controlConnection = controlConnection;
        this.statusConnection = statusConnection;
        new StatusThread().start();

        initButtonsAndStatusLabels();

        setLocation(200,200);
        pack();
        setVisible(true);
    }

    private void initButtonsAndStatusLabels() {
        JButton tempButton;
        JLabel tempLabel;
        String[] tempString;
        String[] info;

        info = statusConnection.receive().split(";");

        for(int i=0 ; i<info.length ; i++) {
            tempString = info[i].split(",");

            if(!isButtonAlreadyInList(tempString[0],buttons)) {
                tempButton = new JButton(tempString[0]);
                tempButton.setFont (tempButton.getFont ().deriveFont (16.0f));
                tempButton.addActionListener(e -> {
                    String command = ((JButton)e.getSource()).getText();
                    controlConnection.send(command);
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

    private void updateLabels(String[] info) {
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
                    String[] info = statusConnection.receive().split(";");
                    updateLabels(info);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), "No answer from server.\nClosing program");
                System.exit(-1);
                e.printStackTrace();
            }
        }
    }

}
