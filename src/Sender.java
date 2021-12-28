import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Sender {
    //IP-адрес получателя сообщения
    private String host;
    //порт, на который нужно отправлять сообщения
    private int port;

    //число, соответствующее простому текстовому сообщению
    public static final int TEXT_MESSAGE = 1;
    //число, соответствующее диаграмме
    public static final int DIAGRAM = 2;

    public Sender(String host, int port) {
        this.host = host;
        this.port = port;
    }

    //отправить текстовое сообщение
    public void sendMessage(String message) {
        try {
            //конвертируем текстовую строку в массив байтов
            byte[] data = message.getBytes();
            //получение IP-адреса из строки
            InetAddress address = InetAddress.getByName(host);
            //создание датаграммного пакета для отправки
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            //создание датаграммного сокета
            DatagramSocket datagramSocket = new DatagramSocket();
            //отправка датаграммного пакета
            datagramSocket.send(packet);
            //закрытие датаграммного сокета
            datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Sender is running");
        System.out.println("Введите IP-адрес приёмника");
        //создание сканера для считывания данных с клавиатуры
        Scanner scanner = new Scanner(System.in);
        //считывание IP-адреса
        String host = scanner.nextLine();
        System.out.println("Введите номер порта");
        //считывание номера порта
        int port = scanner.nextInt();
        scanner.nextLine();
        //создание отправителя
        Sender sender = new Sender(host, port);
        System.out.println("Введите сообщение для передачи");
        //считывание текстового сообщения для отправки
        String textMessage = scanner.nextLine();
        //отправка текстового сообщения
        sender.sendMessage(TEXT_MESSAGE + " " + textMessage + "\n");
        System.out.println("Введите название файла с данными для диаграммы");
        //считывание имени файла
        String filePath = scanner.nextLine();
        //закрытие сканера
        scanner.close();
        //получение пути к файлу по его имени
        Path path = Paths.get(filePath);
        //считывание строки с величинами углов из файла
        String source = Files.readString(path);
        //отправка текстового сообщения
        sender.sendMessage(DIAGRAM + " " + source);
    }
}
