package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.mine.bean.ShenHeJInduBean;
import com.ytfu.yuntaifawu.ui.search.bean.RecommendWordBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SearchService {

    @POST("find/domainList")
    Observable<RecommendWordBean> getRecommendWords();

}
