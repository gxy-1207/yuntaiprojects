package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.lvshiwode.bean.AddCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.ClassificationOfCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.CommonWordsListBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.EditDeleteCommonWordsBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Auther gxy
 * @Date 2020/7/15
 * @Des 常用语
 */
public interface CommonWordsApi {
    /**
     * http://yuntaifawu.com/api/Find/setReply
     * 添加快捷短语接口
     *
     * @param UserID  用户ID
     * @param Content 短语内容
     * @param cid     常用语分类id
     */
    @POST("Find/setReply")
    @FormUrlEncoded
    Observable<AddCommonWordsBean> setAddCommonWords(@Field("UserID") String UserID,
                                                     @Field("Content") String Content,
                                                     @Field("cid") String cid);

    /**
     * http://yuntaifawu.com/api/Find/getReplyContent
     * 常用语列表
     *
     * @param UserID 用户ID
     */
    @POST("Find/getReplyContent")
    @FormUrlEncoded
    Observable<CommonWordsListBean> setCommonWordList(
            @Field("UserID") String UserID,
            @Field("cid") String cid
    );


    /**
     * http://yuntaifawu.com/api/Find/saveReplyContent
     * 修改短语内容
     *
     * @param ReplyID 短语主键id
     * @param Content 修改的内容
     */
    @POST("Find/saveReplyContent")
    @FormUrlEncoded
    Observable<EditDeleteCommonWordsBean> setUpdateCommonWords(@Field("ReplyID") String ReplyID,
                                                               @Field("Content") String Content);

    /**
     * http://yuntaifawu.com/api/Find/saveReplyContent
     * 修改短语内容
     *
     * @param ReplyID 短语主键id
     * @param Type    删除传1
     */
    @POST("Find/saveReplyContent")
    @FormUrlEncoded
    Observable<EditDeleteCommonWordsBean> setdelCommonWords(@Field("ReplyID") String ReplyID,
                                                            @Field("Type") String Type);

    /**
     * 常用语分类
     * https://yuntaifawu.com/api/find/reply_class
     */
    @POST("find/reply_class")
    Observable<ClassificationOfCommonWordsBean> setClassificationOfCommonWords();
} 