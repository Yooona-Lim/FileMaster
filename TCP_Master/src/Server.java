import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author mobeiCanyue
 * Create  2021-12-25 12:01
 * Describe:
 */
public class Server {
    public static void runServer(Scanner sc) throws Exception {
        System.out.println("服务器开始运行...");
        System.out.println("当前服务器的IP地址是:" + InetAddress.getLocalHost().getHostAddress());

        String port = Client.portMaker(sc);

        System.out.println("文件将在当前项目下保存...");
        System.out.println("Waiting......");

        ServerSocket ss = new ServerSocket(Integer.parseInt(port));//服务器套接字

        while (true) {
            System.out.println("监听中...");
            Socket socket = ss.accept();//服务器端口接受socket套节字
            new Thread(new FileThread(socket)).start();
        }
    }


}
