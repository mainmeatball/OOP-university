package Lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide4 {

    public static void main(String[] args) {
        startSocket(4445);
    }

    public static void startSocket(int portNumber) {
        TApplication app = new TApplication();
        while (true) {
            try (
                    ServerSocket serverSocket = new ServerSocket(portNumber);
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter out =
                            new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
            ) {
                String inputLine, outputLine;

                while ((inputLine = in.readLine()) != null) {
                    // add here processing input string
                    System.out.println("Got the message from client");
                    String argA = inputLine;
                    String argB = in.readLine();
                    String argC = in.readLine();
                    outputLine = app.exec(argA, argB, argC);

                    System.out.println("Counting polinom roots");
                    System.out.println(outputLine);

                    System.out.println("Sending result back to client");
                    System.out.println(outputLine);

                    out.println(outputLine);

                    System.out.println("Result was sent to client");

                    if (outputLine.equals("Bye."))
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
