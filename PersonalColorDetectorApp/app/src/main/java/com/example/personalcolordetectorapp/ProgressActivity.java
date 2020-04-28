package com.example.personalcolordetectorapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;


public class ProgressActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progressbar);

        //데이터 가져오기
        Intent intent = getIntent();
        String local = intent.getStringExtra("local");
        Bitmap image = (Bitmap)intent.getParcelableExtra("image");
        SocketThread socket = new SocketThread(local,image);
        socket.setContext(this);
        socket.start();
        while(socket.isAlive()) { }
        finish();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}