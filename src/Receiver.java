import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class Receiver {
    public static void main(String[] args) {
        //создание сканера для считывания данных с клавиатуры
        Scanner in = new Scanner(System.in);
        System.out.println("Введите номер порта для приёма сообщений");
        //считывание номера порта, на который принимаются сообщения
        int port = in.nextInt();
        //закрытие сканера
        in.close();
        System.out.println("Receiver is running");
        try {
            //создание датаграммного сокета для приёма сообщений
            DatagramSocket datagramSocket = new DatagramSocket(port);
            while (true) {
                //создание датаграммного пакета, в который записываются принятые данные
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                //ожидание получения датаграммного пакета по сети
                datagramSocket.receive(packet);
                //создание строки текстового сообщения из данных датаграммного пакета
                String message = new String (packet.getData());
                //создание сканера для считывания полученной строки по частям
                Scanner scanner = new Scanner(message);
                //считывание из строки номера, соответствующего типу переданных данных
                int messageType = scanner.nextInt();
                //считывание из строки текстового сообщения
                String textMessage = scanner.nextLine();
                //закрытие сканера
                scanner.close();
                if (messageType == Sender.TEXT_MESSAGE) {
                    //вывод текстового сообщения в консоль
                    System.out.println("Получено сообщение: " + textMessage);
                } else if (messageType == Sender.DIAGRAM){
                    //вывод в консоль сообщения о получении диаграммы
                    System.out.println("Получены значения величины углов: " + textMessage);
                    //создание диаграммы
                    Diagram diagram = new Diagram(textMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
