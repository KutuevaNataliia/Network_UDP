import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Sender {
    private String host;
    private int port;

    public static final int TEXT_MESSAGE = 1;
    public static final int DIAGRAM = 2;

    public Sender(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendMessage(String message) {
        try {
            byte[] data = message.getBytes();
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            DatagramSocket datagramSocket = new DatagramSocket();
            datagramSocket.send(packet);
            datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Sender is running");
        System.out.println("Введите IP-адрес приёмника");
        Scanner scanner = new Scanner(System.in);
        String host = scanner.nextLine();
        System.out.println("Введите номер порта");
        int port = scanner.nextInt();
        scanner.nextLine();
        Sender sender = new Sender(host, port);
        System.out.println("Введите сообщение для передачи");
        String textMessage = scanner.nextLine();
        sender.sendMessage("1 " + textMessage + "\n");
        System.out.println("Введите название файла с данными для диаграммы");
        String filePath = scanner.nextLine();
        scanner.close();
        Path path = Paths.get(filePath);
        String source = Files.readString(path);
        sender.sendMessage("2 " + source);
    }
}
