package com.example.myprojectv_002.CreateRequest;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Пользователь on 12.12.2015.
 */
public class SendRequest {

    public String SendAndGet(Socket socket, String str) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(str);
            dataOutputStream.flush();

            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(1024);
            DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
            byte[] buffer=new byte[1024];
            int byteRead;
            String getText="";
            while((byteRead=dataInputStream.read(buffer))!=-1){
                byteArrayOutputStream.write(buffer,0,byteRead);
                getText+=byteArrayOutputStream.toString("UTF-8");
            }
            return getText;
        }
        catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }
}
