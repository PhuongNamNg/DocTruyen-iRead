package com.example.doctruyen_iread.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.doctruyen_iread.Module.Photo;
import com.example.doctruyen_iread.R;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {

    private Context mContext;

    public PhotoAdapter(Context mContext, List<Photo> mListPhoto) {
        this.mContext = mContext;
        this.mListPhoto = mListPhoto;
    }

    private List<Photo> mListPhoto;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container,false);
        ImageView imgPhoto=view.findViewById(R.id.img_photo);

        Photo photo = mListPhoto.get(position);
        if (photo != null){
            Glide.with(mContext).load(photo.getResourceId()).into(imgPhoto);
        }
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if (mListPhoto != null)
        {
            return mListPhoto.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
