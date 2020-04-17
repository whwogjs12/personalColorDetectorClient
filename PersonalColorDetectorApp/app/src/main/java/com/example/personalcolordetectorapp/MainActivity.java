package com.example.personalcolordetectorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//퍼스널 컬러의 설명을 위해 시작되는 화면

    ShowingPagerAdapter adapter;
    ViewPager vp;
    boolean descriptionOff = false; // 다시 보지 않기 버튼 클릭시 저장. 임시로 false
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent returnIntent = getIntent();
        int mode =returnIntent.getIntExtra("mode",0);
        SharedPreferences description = getSharedPreferences("description",MODE_PRIVATE);
        descriptionOff = description.getBoolean("modeOff",false);
        //설명하는 이미지 넣는 부분
        if(mode == 0)
        {
            if(!descriptionOff)
            {
                vp = (ViewPager) findViewById(R.id.viewPager);
                adapter = new ShowingPagerAdapter(this);
                vp.setAdapter(adapter);
            }
            else
            {
                startRunning(getApplicationContext());
            }
        }
        else
        {
            vp = (ViewPager) findViewById(R.id.viewPager);
            adapter = new ShowingPagerAdapter(this);
            vp.setAdapter(adapter);
        }

        Button skipButton = (Button) findViewById(R.id.skipButton);
        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button previousButton = (Button) findViewById(R.id.previousButton);
        Button descriptionOffButton = (Button) findViewById(R.id.doNotSeeFromNext);
        skipButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        descriptionOffButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int position;
        switch (v.getId())
        {
            case R.id.skipButton:
                startRunning(getApplicationContext());
                break;
            case R.id.nextButton:
                position = vp.getCurrentItem();
                if(position == (adapter.getCount()-1))//맨 뒤인 경우
                {
                    startRunning(getApplicationContext());
                }
                else
                {
                    vp.setCurrentItem(position+1, true);
                }
                break;
            case R.id.previousButton:
                position = vp.getCurrentItem();
                if(position != 0) //맨 앞이 아닌 경우
                {
                    vp.setCurrentItem(position-1, true);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "현재 화면이 제일 처음입니다.", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.doNotSeeFromNext:
                //다시 보지 않게 하기 위해 변수 조작
                SharedPreferences dnsFromNext = getSharedPreferences("description",MODE_PRIVATE);
                SharedPreferences.Editor editor = dnsFromNext.edit();
                editor.putBoolean("modeOff",true);
                editor.commit();
                startRunning(getApplicationContext());
                descriptionOff=true;
                break;
        }

    }

    public void startRunning (Context context)
    {
        Intent intent = new Intent(context,PermissionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}
