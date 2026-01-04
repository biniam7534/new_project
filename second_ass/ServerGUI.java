// GUI Chat Server
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ServerGUI extends JFrame implements ActionListener {

    JTextArea chatArea;
    JTextField inputField;
    JButton sendButton;

    ServerSocket serverSocket;
    Socket socket;
    PrintStream out;
    BufferedReader in;

    ServerGUI() throws IOException {
        // GUI setup
        setTitle("Chat Server");
        setSize(400, 500);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane sp = new JScrollPane(chatArea);

        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(sp, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(this);
        inputField.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Server setup
        serverSocket = new ServerSocket(9000);
        chatArea.append("Waiting for client...\n");

        socket = serverSocket.accept();
        chatArea.append("Client connected!\n");

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Receive messages thread
        new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    chatArea.append("Client: " + msg + "\n");
                }
            } catch (IOException e) {
                chatArea.append("Connection closed.\n");
            }
        }).start();
    }

    public void actionPerformed(ActionEvent e) {
        String msg = inputField.getText();
        if (!msg.isEmpty()) {
            out.println(msg);
            chatArea.append("Me: " + msg + "\n");
            inputField.setText("");
        }
    }

    public static void main(String[] args) throws IOException {
        new ServerGUI();
    }
}
