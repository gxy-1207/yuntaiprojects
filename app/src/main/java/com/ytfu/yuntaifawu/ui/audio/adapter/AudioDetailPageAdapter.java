package com.ytfu.yuntaifawu.ui.audio.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ytfu.yuntaifawu.ui.audio.fragment.AudioDetailAboutFragment;
import com.ytfu.yuntaifawu.ui.audio.fragment.AudioDetailFragment;
import com.ytfu.yuntaifawu.ui.home.bean.AudioDetailsBean;

public class AudioDetailPageAdapter extends FragmentStatePagerAdapter {
    private AudioDetailsBean data;

    public AudioDetailPageAdapter(@NonNull FragmentManager fm, AudioDetailsBean data) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            default:
            case 0:
                return AudioDetailFragment.newInstance(data.getList().getPost_yuanwen());
            case 1:
                return AudioDetailAboutFragment.newInstance(data.getAudio_list());
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
