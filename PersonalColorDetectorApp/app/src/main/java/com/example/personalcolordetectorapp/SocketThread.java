package com.example.personalcolordetectorapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketThread extends Thread
{

    int port = 4799;
    String ip =  "61.105.154.7";
    String host = null;
    Bitmap image  = null;
    Context context = null;

    public SocketThread(String host, Bitmap image)
    {
        this.host = host;
        this.image = image;
    }
    public void run()
    {
        try {
            Socket socket = new Socket(ip, port);
            if(image!=null)
            {
                byte[] imageArray = bitmapToByteArray(image);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                byte[] imageSize = String.valueOf(imageArray.length).getBytes();
                dos.write(imageSize,0,imageSize.length);
                Log.d("바이트",String.valueOf(String.valueOf(imageArray.length).getBytes()));
                dos.flush();
                dos.write(imageArray,0,imageArray.length);
                dos.flush();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                // int result = Integer.parseInt(String.valueOf(dis.read()));
                byte[] buf = new byte[1024];
                int readCount = dis.read(buf);
                int result = Integer.parseInt(new String(buf,0,readCount,"UTF-8"));
                Intent intent = new Intent(context,ResultActivity.class);
                intent.putExtra("result",result);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
            else return;
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(context,"서버에 문제가 발생하였습니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private byte[] getByte(int num) {
        byte[] buf = new byte[4];
        buf[0] = (byte)( (num >>> 24) & 0xFF );
        buf[1] = (byte)( (num >>> 16) & 0xFF );
        buf[2] = (byte)( (num >>>  8) & 0xFF );
        buf[3] = (byte)( (num >>>  0) & 0xFF );

        return buf;
    }

    public byte[] bitmapToByteArray( Bitmap bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray ;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }
}
