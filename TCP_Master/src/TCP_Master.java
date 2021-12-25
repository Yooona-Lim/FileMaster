import java.util.Scanner;

/**
 * @author mobeiCanyue
 * Create  2021-12-25 11:59
 * Describe:重新写,解决了不能上传文件名的问题
 */
public class TCP_Master {
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("1:客户端 2:服务器 3:退出");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    Client.runClient(sc);
                    break;
                case 2:
                    Server.runServer(sc);
                    break;
                case 3: {
                    sc.close();
                    System.exit(0);
                }
                default:
                    System.out.println("请输入正确的指令...");
            }
        }
    }
}
