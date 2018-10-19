import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

public class ServiceSendThread extends Thread implements Runnable {
    private int pos;
    private Socket socket;
    private BufferedWriter mWriter;
    private Vector<String> msgQuey;
    private Vector<Socket> sockets;

    public ServiceSendThread(Vector<Socket> sockets,Socket socket, BufferedWriter mWriter, Vector<String> msgQuey) {
        this.sockets = sockets;
        this.socket = socket;
        this.mWriter = mWriter;
        this.msgQuey = msgQuey;
        this.pos = this.msgQuey.size();
    }

    @Override
    public void run(){
        super.run();
        while (true){
            for (int i = pos;i<msgQuey.size();i++){
                String msg = null;
                try {
                    msg = msgQuey.get(i);
                    mWriter.write(msg);
                    mWriter.newLine();
                    mWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pos++;
                System.out.println(this.getName() + "线程>>>>" +"服务端发送消息:" + msg);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
