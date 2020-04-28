package com.example.personalcolordetectorapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener
{

    ResultPageAdapter adapter;
    ViewPager vp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent  = getIntent();
        int result = intent.getIntExtra("result",-100);
        vp = findViewById(R.id.resultViewPager);
        startResultAction(result);
        Button nextButton = findViewById(R.id.nextResultButton);
        Button preButton = findViewById(R.id.preResultButton);
        Button backButton = findViewById(R.id.backResultButton);
        nextButton.setOnClickListener(this);
        preButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    public void startResultAction(int result)
    {
        if(result>=0)
        {
            adapter = new ResultPageAdapter(this);
            adapter.setItem(result);
            vp.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else if(result == -100)
        {
            Toast.makeText(this,"이미지를 입력하지 않았거나 문제가 발생하였습니다.",Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(result == -1)
        {
            Toast.makeText(this,"사진에서 얼굴을 식별할 수 없거나 얼굴이 너무 작습니다.",Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(result == -3)
        {
            Toast.makeText(this,"사진에서 얼굴을 식별할 수 없습니다.",Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(result == -2)
        {
            Toast.makeText(this,"사진에서 식별된 얼굴이 너무 많습니다.",Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(result == -4)
        {
            Toast.makeText(this,"통신 문제가 발생하였습니다. 같은 사진으로 문제가 계속 발생한다면 다른 사진으로 다시 시도해주십시오.",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onClick(View v)
    {
        int position;
        switch (v.getId())
        {
            case R.id.preResultButton:
                position = vp.getCurrentItem();
                if(position != 0) //맨 앞이 아닌 경우
                {
                    vp.setCurrentItem(position-1, true);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "현재 화면이 제일 처음입니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.backResultButton:
                finish();
                break;
            case R.id.nextResultButton:
                position = vp.getCurrentItem();
                if(position == (adapter.getCount()-1))//맨 뒤인 경우
                {
                    Toast.makeText(getApplicationContext(), "현재 화면이 마지막입니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    vp.setCurrentItem(position+1, true);
                }
                break;
        }
    }
}
