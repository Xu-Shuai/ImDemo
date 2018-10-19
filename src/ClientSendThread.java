import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientSendThread extends Thread implements Runnable {
    private Socket client;
    private BufferedReader mInput;
    private BufferedWriter mWriter;
    private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ClientSendThread(Socket client, BufferedReader mInput, BufferedWriter mWriter) {
        this.client = client;
        this.mInput = mInput;
        this.mWriter = mWriter;
    }
    @Override
    public void run(){
        super.run();
        String msg;
        try{
            while ((msg = mInput.readLine())!=null){
                Map<String ,Object> map = new HashMap<>();
                map.put("time",df.format(new Date()));
                map.put("msg",msg);
                map.toString();
                msg = utils.mapToStr(map);
                mWriter.write(Objects.requireNonNull(msg));
                mWriter.write(msg);
                mWriter.newLine();
                mWriter.flush();
            }
        }catch (Exception e){
            System.out.println("ERROR[与服务器断开连接]");
            e.printStackTrace();
        }finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
