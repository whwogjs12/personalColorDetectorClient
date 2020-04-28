package com.example.personalcolordetectorapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class RunningActivity extends AppCompatActivity implements View.OnClickListener
{

    public static final int MODE_CAMERA = 0;
    public static final int MODE_GALLERY = 1;

    //카메라 화면과 설명이 존재하는 메인 화면
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        Button descriptionButton = findViewById(R.id.viewDescription);
        Button cameraCallButton = findViewById(R.id.cameraCallButton);
        Button imageCallButton = findViewById(R.id.imageCallButton);
        Button resultButton = findViewById(R.id.resultButton);
        resultButton.setVisibility(View.GONE);
        descriptionButton.setOnClickListener(this);
        cameraCallButton.setOnClickListener(this);
        imageCallButton.setOnClickListener(this);
        resultButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        Intent intent;
        switch (v.getId())
        {
            case R.id.viewDescription:
                //descriptionOff 를 다시 false로 만들기
                intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("mode",1);
                startActivity(intent);
                break;
            case R.id.cameraCallButton:
                cameraCall();
                break;
            case R.id.imageCallButton:
                goToAlbum();
                break;
            case R.id.resultButton:
                ImageView imageView = findViewById(R.id.loadImageWindow);
                BitmapDrawable bd = (BitmapDrawable) imageView.getDrawable();
                Bitmap image = bd.getBitmap();
                float width = image.getWidth();
                float height = image.getHeight();
                if(height > 150)
                {
                    float percente = (float)(height / 100);
                    float scale = (float)(150 / percente);
                    width *= (scale / 100);
                    height *= (scale / 100);
                }
                image = Bitmap.createScaledBitmap(image, (int) width, (int) height, true);
                //SocketThread socket = new SocketThread(getLocalIpAddress(),image);
                //socket.setContext(this);
                Intent setProgress = new Intent(this, ProgressActivity.class);
                setProgress.putExtra("local", getLocalIpAddress());
                setProgress.putExtra("image", image);
                startActivity(setProgress);
                //socket.start();
                break;
        }

    }

    public void cameraCall()
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, MODE_CAMERA);
    }

    public void goToAlbum()
    {
        Intent AlbumIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        AlbumIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(AlbumIntent, "어플리케이션 선택"), MODE_GALLERY);
    }



    public void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        if(resultCode ==RESULT_OK)
        {
            ImageView loadImage = findViewById(R.id.loadImageWindow);
            Bitmap img;
            if(requestCode ==MODE_CAMERA)
            {
                img = (Bitmap) data.getExtras().get("data");
                loadImage.setImageBitmap(img);
            }
            else if( requestCode ==MODE_GALLERY)
            {
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    img = BitmapFactory.decodeStream(in);
                    in.close();
                    loadImage.setImageBitmap(img);
                }catch(Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"사진을 불러오는데 실패하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(getApplicationContext(),"사진을 불러오는데 성공하였습니다.",Toast.LENGTH_SHORT).show();
            findViewById(R.id.resultButton).setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"사진을 불러오기를 취소하셨습니다.",Toast.LENGTH_SHORT).show();
        }
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
