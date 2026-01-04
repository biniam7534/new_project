import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ServerGUI extends JFrame implements ActionListener {

    JTextArea chatArea;
    JTextField inputField;
    JButton sendButton;

    ServerSocket serverSocket;
    Socket socket;
    PrintStream out;
    BufferedReader in;

    public ServerGUI() throws IOException {

        // GUI setup
        setTitle("Server");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        inputField = new JTextField();
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);

        // SERVER SOCKET
        serverSocket = new ServerSocket(5000);
        chatArea.append("Server waiting for client...\n");

        socket = serverSocket.accept();
        chatArea.append("Client connected!\n");

        // IMPORTANT PART (FIX)
        out = new PrintStream(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // READ CLIENT MESSAGES
        new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    chatArea.append("Client: " + msg + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // SEND MESSAGE TO CLIENT
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
