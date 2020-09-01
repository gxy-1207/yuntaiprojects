package com.ytfu.yuntaifawu.ui.audio.v;


import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.home.bean.AudioNavBean;

import java.util.List;

public interface AudioClassificationView extends BaseView {

    void onGetAudioClassify(List<AudioNavBean.ListBean> list);

}
