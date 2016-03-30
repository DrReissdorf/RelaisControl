import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

class GuiBuilder extends JFrame {
    private Communication comm;
    private JLabel programNameLabel;
    private ArrayList<JButton> buttons;
    HashMap<String, JLabel> labelHashMap;
    private Container c;

    public GuiBuilder(String s) {
        super(s);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buttons = new ArrayList<>();
        labelHashMap = new HashMap<>();
        comm = new Communication();
        String[] info = comm.getInfo(); //Information about available Relais

        c = getContentPane();
        c.setLayout(new GridLayout(0,2));
        c.setForeground(Color.black);

        initButtonsAndStatusLabels(info);

        setLocation(200,200);
        pack();
        setVisible(true);

        new StatusThread(Data.refreshRatePerSecond).run();
    }

    private void initButtonsAndStatusLabels(String[] info) {
        JButton tempButton;
        JLabel tempLabel;
        String[] tempString;
        for(int i=0 ; i<info.length ; i++) {
            tempString = info[i].split(",");

            if(!isButtonAlreadyInList(tempString[0],buttons)) {
                tempButton = new JButton(tempString[0]);
                tempButton.setFont (tempButton.getFont ().deriveFont (16.0f));
                tempButton.addActionListener(e -> {
                    String command = ((JButton)e.getSource()).getText();
                    comm.sendCommand(command);
                    actualizeLabels(comm.getInfo());
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

    private void actualizeLabels(String[] info) {
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

    private class StatusThread extends Thread {
        private int sleepTime;

        public StatusThread(int refreshRatePerSecond) {
            this.sleepTime = (int)(1000/refreshRatePerSecond);
        }

        public void run() {
            while(true) {
                actualizeLabels(comm.getInfo());
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
