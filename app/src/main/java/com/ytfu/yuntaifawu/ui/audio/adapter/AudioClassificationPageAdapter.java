package com.ytfu.yuntaifawu.ui.audio.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ytfu.yuntaifawu.ui.audio.fragment.AudioListFragment;
import com.ytfu.yuntaifawu.ui.home.bean.AudioNavBean;

import java.util.ArrayList;
import java.util.List;

public class AudioClassificationPageAdapter extends FragmentStatePagerAdapter {
    private List<AudioNavBean.ListBean> data = new ArrayList<>();

    public AudioClassificationPageAdapter(@NonNull FragmentManager fm) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return AudioListFragment.Companion.newInstance(data.get(position).getId());

    }

    @Override
    public int getCount() {
        return data.size();
    }

    //===Desc:================================================================================


    public void setData(List<AudioNavBean.ListBean> data) {
        this.data = data;
    }
}
