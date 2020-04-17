package com.example.personalcolordetectorapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ResultPageAdapter extends PagerAdapter
{

    private LayoutInflater inflater;
    private Context context = null;
    private int [] images;

    public ResultPageAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount() {
        if(images !=null) return images.length;
        return 0;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View view = null ;
        if (context != null) {
            // LayoutInflater를 통해 "/res/layout/layout_page.xml"을 뷰로 생성.
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_page, container, false);
            ImageView image = (ImageView) view.findViewById(R.id.imageView);
            image.setImageResource(images[position]); // 이미지 위치 찾아 제공
            image.setScaleType(ImageView.ScaleType.FIT_XY); //이미지 가득 채우기
            Log.d("이리로","와요");
        }
        // 뷰페이저에 추가.
        container.addView(view) ;
        return view ;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        Log.d("이리로",Boolean.toString(view == ((LinearLayout) object)));
        return view == ((LinearLayout) object);//false면 이미지 이미지 구현 안됨
    }


    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }

    public void setItem(int result)
    {
        switch (result)
        {
            case 0:
                images = new int[]{R.drawable.spring_result_1, R.drawable.spring_result_2};
                break;
            case 1:
                images = new int[]{R.drawable.summer_result_1, R.drawable.summer_result_2};
                break;
            case 2:
                images = new int[]{R.drawable.atumn_result_1, R.drawable.atumn_result_2};
                break;
            case 3:
                images = new int[]{R.drawable.winter_result_1, R.drawable.winter_result_2};
                break;
        }
    }
}
