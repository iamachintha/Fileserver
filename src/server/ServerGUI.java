package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerGUI {
    private JFrame frame;
    private JTextArea textArea;
    private JButton startButton;
    private JButton stopButton;
    private JButton clearLog;
    private JLabel connectedClientsLabel;
    private JServer server;

    public ServerGUI() {
        frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocation(1420, 55);
        frame.setAlwaysOnTop(true);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GRAY);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        buttonPanel.add(startButton);
        stopButton = new JButton("Stop");
        buttonPanel.add(stopButton);
        clearLog = new JButton("Clear Log");
        buttonPanel.add(clearLog);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        connectedClientsLabel = new JLabel("Connected Clients: 0");
        frame.add(connectedClientsLabel, BorderLayout.NORTH);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        clearLog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });

        frame.setVisible(true);
    }

    private void startServer() {
        server = new JServer(5002, this);
        server.start();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    private void stopServer() {
        if (server != null) {
            server.stopServer();
            server = null;
        }
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    public void appendText(String text) {
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        textArea.append("[" + timeStamp + "] " + text + "\n");
    }

    public void setConnectedClients(int count) {
        connectedClientsLabel.setText("Connected Clients: " + count);
    }

    public static void main(String[] args) {
        ServerGUI servergui = new ServerGUI();
        servergui.startServer();
    }
}
