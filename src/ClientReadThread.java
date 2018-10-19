import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;

public class ClientReadThread  extends Thread implements Runnable{
    private Socket socket ;
    private BufferedReader bufferedReader;

    public ClientReadThread(Socket socket, BufferedReader bufferedReader) {
        this.socket = socket;
        this.bufferedReader = bufferedReader;
    }
    @Override
    public void run(){
        super.run();
        String str ;
        try {
            while ((str = bufferedReader.readLine())!=null){
                Map<String,Object> map = utils.strToMap(str);
                System.out.println(Objects.requireNonNull(map).get("location") + ">>" + map.get("time") + " : " + map.get("msg"));
            }
        }catch (Exception e){
            System.out.println("ERROR[与服务器断开连接]");
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }
}
