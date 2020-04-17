package com.example.personalcolordetectorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ShowingPagerAdapter extends PagerAdapter {

    private Context context = null;
    private LayoutInflater inflater;
    private int [] images = {R.drawable.personal_color_description1,R.drawable.personal_color_description2
            ,R.drawable.personal_color_description3,R.drawable.personal_color_description4,R.drawable.personal_color_description5};

    public ShowingPagerAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null ;
        if (context != null) {
            // LayoutInflater를 통해 "/res/layout/layout_page.xml"을 뷰로 생성.
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_page, container, false);
            ImageView image = (ImageView) view.findViewById(R.id.imageView);
            image.setImageResource(images[position]); // 이미지 위치 찾아 제공
            image.setScaleType(ImageView.ScaleType.FIT_XY); //이미지 가득 채우기
        }
        // 뷰페이저에 추가.
        container.addView(view) ;
        return view ;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);//false면 이미지 이미지 구현 안됨
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
