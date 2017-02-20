import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by jakob on 18/02/2017.
 */

public class UDPEchoServer {

    public static final int PORT = 1234;
    private static DatagramSocket datagramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;

    public static void main(String[] args) {

        System.out.println("Opening port... \n");
        try {
            datagramSocket = new DatagramSocket(PORT);
        }
        catch (SocketException sockEx){
            System.out.println("Unable to open port!");
            System.exit(1);
        }
         handleClient();

    }

    private static void handleClient(){

        try{
            String messageIn, messageOut;
            int numMessages = 0;
            InetAddress clientAddress = null;
            int clientPort;

            do {
                 buffer = new byte[256];
                 inPacket = new DatagramPacket(buffer, buffer.length);

                 datagramSocket.receive(inPacket);

                 clientAddress = inPacket.getAddress();

                 clientPort = inPacket.getPort();

                 messageIn = new String(inPacket.getData(), 0, inPacket.getLength());

                System.out.println("Message received.");
                numMessages++;
                messageOut = "Message " + numMessages + ": " + messageIn;

                outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);

                datagramSocket.send(outPacket);

            }
            while(true);

            }
            catch (IOException ioEx)
            {
                ioEx.printStackTrace();
        }
        finally {
            System.out.println("\n* Closing Connection... *");
            datagramSocket.close();
        }

    }

}
