import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.*;

public class Client {
    private String host;
    private int port;
    private Socket client;
    private BufferedReader mInput, mReader;
    private BufferedWriter mWriter;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            client = new Socket(host,port);
            mInput =  new BufferedReader(new InputStreamReader(System.in));
            mReader =  new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            mWriter =  new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),  StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("ERROR[连接服务器失败 请稍后重试]");
            e.printStackTrace();
        }
        System.out.println("SUCCESS[连接服务器成功]");
        this.start();

    }
    private void start() {
        new ClientSendThread(client,mInput,mWriter).start();
        new ClientReadThread(client,mReader).start();
    }
    public static void main(String[] args) {
        new Client("10.10.10.127", 9000);
    }
}
