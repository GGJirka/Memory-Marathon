package jimmy.gg.flashingnumbers.sockets;

import android.support.annotation.RequiresPermission;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import jimmy.gg.flashingnumbers.multiplayer.RoomActivity;

/*praise jimmy*/

public class FakeClient implements IFakeClient{

    private Socket clientSocket;
    private BufferedWriter bw;
    private String message = "";
    private RoomActivity activity;
    public TextView view;
    public static BufferedReader br;

    public FakeClient(TextView view){
        this.view = view;
    }

    @Override
    public void startSocket(){
        new Thread(new SocketThread()).start();
    }
    /*
    * This creating creating a client socket to connect to local server
    * and proccess mssages
    */
    @Override
    public boolean socketClose(){
        try{
            clientSocket.close();
            bw.close();
            br.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public void sendMessage(String message) {
        try {
            bw.write(message + "\r\n");
            bw.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean attemptToConnectRoom(String roomName) {
        return false;
    }

    @Override
    public boolean isConnected() {
        return clientSocket.isConnected();
    }

    public class SocketThread implements Runnable{
        //@RequiresPermission(android.Manifest.permission.INTERNET)
        @Override
        public void run(){
            try {
                clientSocket = new Socket(InetAddress.getByName("192.168.1.101"), 4758);
                bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                sendMessage("ahojky");

                while(true){
                    message = br.readLine();
                    view.setText(message);
                   // if(message.equals("ROOMCONNECT")){
                        //if(view != null){
                            //if(message.split(" ")[2].equals(RoomActivity.UserData.roomName)){
                              //  view.setText(message.split(" ")[2]+"a");
                              //  RoomActivity.UserData.users.add(new Score(message.split(" ")[1]+"jo",true));
                              //  RoomActivity.UserData.connectedUsers.setAdapter(RoomActivity.UserData.userAdapter);
                           // }
                       // }
                   // }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
