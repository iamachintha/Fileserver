import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
    private Socket clientSocket;
    private JServer server;

    public ClientHandler(Socket socket, JServer server) {
        this.clientSocket = socket;
        this.server = server;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                server.serverGUI.appendText("Received from " + clientSocket.getInetAddress().getHostAddress() + ": " + inputLine);
                out.println("Server received: " + inputLine);
                if(inputLine == "FETCH_FILES") { //inputLine.startsWith("FETCH_FILES")
                    JServer.handleClientFetchFiles(out);
                }
            }

            in.close();
            out.close();
            clientSocket.close();
            server.removeClient(this);
            server.serverGUI.appendText("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        clientSocket.close();
    }
}