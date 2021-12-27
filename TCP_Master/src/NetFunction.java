import java.io.File;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Scanner;

/**
 * @author mobeiCanyue
 * Create  2021-12-27 0:47
 * Describe: 用于对网络以及文件操作, 因为服务器和客户端操作重复,故提取此类
 */
public class NetFunction {
    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    /**
     * 获取IP以及校验
     *
     * @param sc 输入
     * @return IP
     */
    public static String ipMaker(Scanner sc) {
        System.out.println("请输入用于传输的IP地址, 默认为(输入-1)为本地回环地址:");
        String ip = sc.nextLine();
        if ("-1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 获取端口以及校验
     *
     * @param sc 输入
     * @return 端口
     */
    public static String portMaker(Scanner sc) {
        System.out.println("请输入用于传输的端口号, 默认(输入-1)为9980:");
        String port = sc.nextLine();
        if ("-1".equals(port)) {
            port = "9980";
        }
        return port;
    }

    /**
     * 获取套接字以及循环5次校验
     *
     * @param ip   IP
     * @param port 端口
     * @return 套接字
     */
    public static Socket socketMaker(String ip, String port) {
        for (int i = 5; i >= 0; i--) {
            try {
                System.out.println("开始连接验证...");
                Socket socket = new Socket(ip, Integer.parseInt(port));//如果连接失败就抛异常
                System.out.println("连接成功 ! ! !");
                return socket;//如果连接成功就返回对象
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("\nTCP 连接失败... 剩余的连接次数:" + i);
                try {
                    Thread.sleep(1000); //等一秒再尝试重连
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 输入以及校验文件是否存在,如果 文件不存在,提示:文件不存在,请放在当前目录下或者输入正确的路径
     *
     * @param sc 输入
     * @return 文件
     */
    public static File fileMaker(Scanner sc) {
        System.out.println("请输入文件名称 (放在当前目录下或者输入绝对路径):");
        File file = new File(sc.nextLine());
        while (!file.exists() && !file.isFile()) {
            System.out.println("文件不存在, 请放在当前目录下或者输入正确的路径 !");
            file = new File(sc.nextLine());
        }
        return file;
    }

    public static String checkSum_Hash(String algorithm, byte[] data) throws Exception {
        //MessageDigest类为应用程序提供消息摘要算法的功能，例如SHA-1或SHA-256。
        MessageDigest md = MessageDigest.getInstance(algorithm);//获取MD5MessageDigest类的实例
        md.update(data);//类读取数组里的数据
        byte[] digest = md.digest();//MessageDigest类"消化",也就是计算哈希值
        return NetFunction.byteArrayToHexString(digest);//digest是字节数组,为了便于比较,要转成16进制
    }

    /**
     * 字节数组转十六进制:因为是字节数组, 最高8位 2^8 =16*16 =256,也就是说,÷16不会有余数超过16的情况,所以求一次即可(canyue)
     * 将1个字节（1 byte = 8 bit）转为 2个十六进制位
     * 1个16进制位 = 4个二进制位 （即4 bit）
     * 转换思路：最简单的办法就是先将byte转为10进制的int类型，然后将十进制数转十六进制
     * 源代码:https://www.jianshu.com/p/b419163272c1
     */
    private static String byteToHexString(byte b) {
        // byte类型赋值给int变量时，java会自动将byte类型转int类型，从低位类型到高位类型自动转换
        int n = b;

        // 将十进制数转十六进制
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;

        // d1和d2通过访问数组变量的方式转成16进制字符串；比如 d1 为12 ，那么就转为"c"; 因为int类型不会有a,b,c,d,e,f等表示16进制的字符
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 将字节数组里每个字节转成2个16进制位的字符串后拼接起来
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte value : b) {
            resultSb.append(byteToHexString(value));
        }
        return resultSb.toString();
    }
}
