import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Service {
    /**
     * 请求人数
     */
    private   int count;
    /**
     *
     */
    private ServerSocket server;
    private BufferedReader mInput, mReader;
    private BufferedWriter mWriter;
    private int port;
    private Vector<Socket> sockets;
    private Vector<String> msgQuery;

    public Service(int port) {
        this.port = port;
        try {

            server = new ServerSocket(this.port);
            sockets =new Vector<>();
            msgQuery = new Vector<>();

        } catch (IOException e) {
            System.out.println("ERROR[服务器启动失败 请重新启动]");
            System.exit(0);
        }
        System.out.println("SUCCESS[服务器启动成功]");
        start();
    }
    private void start() {
        Socket socket = null;
        while (true) {
        try {

                socket = server.accept();
                sockets.add(socket);
                mReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                mWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                new ServiceReadThread(socket, mReader, msgQuery, sockets).start();
                new ServiceSendThread(sockets, socket, mWriter, msgQuery).start();
                System.out.println("客户端:" + socket.getInetAddress() + "与服务器连接成功");
                Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("现在有" +(sockets.size()) + "个在线用户");
        }

    }
    public static void main(String[] args) {
        new Service(9000);
    }
}
