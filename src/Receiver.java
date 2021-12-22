import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;

public class Receiver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите номер порта для приёма сообщений");
        int port = in.nextInt();
        in.close();
        System.out.println("Receiver is running");
        try {
            DatagramSocket datagramSocket = new DatagramSocket(port);
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                datagramSocket.receive(packet);
                String message = new String (packet.getData());
                Scanner scanner = new Scanner(message);
                int messageType = scanner.nextInt();
                String textMessage = scanner.nextLine();
                scanner.close();
                if (messageType == Sender.TEXT_MESSAGE) {
                    System.out.println("Получено сообщение: " + textMessage);
                } else if (messageType == Sender.DIAGRAM){
                    System.out.println(textMessage);
                    Diagram diagram = new Diagram(textMessage);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
