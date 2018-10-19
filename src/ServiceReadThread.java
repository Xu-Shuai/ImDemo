import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class ServiceReadThread extends Thread implements Runnable {
    private Socket socket ;
    private BufferedReader bufferedReader;
    private Vector<String> msgQuey;
    private Vector<Socket> sockets;


    public ServiceReadThread(Socket socket, BufferedReader bufferedReader, Vector<String> msgQuey, Vector<Socket> sockets) {
        this.socket = socket;
        this.bufferedReader = bufferedReader;
        this.msgQuey = msgQuey;
        this.sockets = sockets;
    }
    @Override
    public void  run(){
        super.run();
        String str;
        try {
            while ((str = bufferedReader.readLine()) != null){
                System.out.println(str);
                Map<String, Object> map = utils.strToMap(str);
                if (map.get("time")==null){
                    continue;
                }
                Objects.requireNonNull(map).put("location", socket.getInetAddress());
                msgQuey.add(str);
            }
        }catch (Exception e){
            sockets.remove(socket);
            System.out.println(this.getName() + "线程>>>>" + "ERROR[服务器与客户端断开连接]");
            e.printStackTrace();
        }

    }
}
