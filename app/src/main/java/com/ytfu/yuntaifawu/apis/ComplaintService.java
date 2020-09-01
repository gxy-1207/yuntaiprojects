package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.complaint.bean.ComplaintClassificationBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ComplaintService {
    /**
     * 起诉状分类接口
     *https://yuntaifawu.com/api/qisuzhuang/get_new_qisufenlei_list
     * */
    @POST("qisuzhuang/get_new_qisufenlei_list")
    Observable<ComplaintClassificationBean> getComplaintClassification();
}
