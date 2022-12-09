package com.example.doctruyen_iread.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doctruyen_iread.FragmentCaNhan.EditProfileFragment;
import com.example.doctruyen_iread.FragmentCaNhan.YeuThichFragment;
import com.example.doctruyen_iread.FragmentCaNhan.YourStoryFragment;

public class AdapterViewPagerCaNhan extends FragmentStateAdapter {
    public AdapterViewPagerCaNhan(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new YourStoryFragment();
            case 1:
                return new YeuThichFragment();
            case 2:
                return new EditProfileFragment();
            default:
                return new YourStoryFragment();
        }
    };

    @Override
    public int getItemCount() {
        return 3;
    }
}
