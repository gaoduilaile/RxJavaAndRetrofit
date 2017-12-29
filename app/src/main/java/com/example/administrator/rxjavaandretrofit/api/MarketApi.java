package com.example.administrator.rxjavaandretrofit.api;

import com.example.administrator.rxjavaandretrofit.jbean.FeedBackEntity;
import com.example.administrator.rxjavaandretrofit.jbean.UserInfo;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by Administrator on 2017/6/13.
 * retrofit 接口类
 */

/*GET请求

多个参数在URL问号之后，且个数不确定

http://api.stay4it.com/News?newsId=1&type=类型1…
http://api.stay4it.com/News?newsId={资讯id}&type={类型}…

@GET("News")
Call<NewsBean> getItem(@QueryMap Map<String, String> map);

    1
    2

或者：

  @GET("News")
  Call<NewsBean> getItem(
              @Query("newsId") String newsId，
              @QueryMap Map<String, String> map);

 ---------------------------------------------------------------------

POST请求

    需要补全URL，post的数据只有一条reason

    http://102.10.10.132/api/Comments/1
    http://102.10.10.132/api/Comments/{newsId}

@FormUrlEncoded
@POST("Comments/{newsId}")
Call<Comment> reportComment(
        @Path("newsId") String commentId,
        @Field("reason") String reason);
--------------------------------------------------------------------

    需要补全URL，问号后加入access_token，post的数据只有一条reason

    http://102.10.10.132/api/Comments/1?access_token=1234123
    http://102.10.10.132/api/Comments/{newsId}?access_token={access_token}

@FormUrlEncoded
@POST("Comments/{newsId}")
Call<Comment> reportComment(
        @Path("newsId") String commentId,
        @Query("access_token") String access_token,
        @Field("reason") String reason);
----------------------------------------------------------------------------------
    需要补全URL，问号后加入access_token，post一个body（对象）

    http://102.10.10.132/api/Comments/1?access_token=1234123
    http://102.10.10.132/api/Comments/{newsId}?access_token={access_token}

@POST("Comments/{newsId}")
    Call<Comment> reportComment(
        @Path("newsId") String commentId,
        @Query("access_token") String access_token,
        @Body CommentBean bean);*/

public interface MarketApi {



    @GET("top250")
    Observable<ResponseBody> getTopMovie(@Query("start") int start, @Query("count") int count);

    @POST("uploadfeedback")
    Observable<FeedBackEntity> editFeedBack(@Query("user_name") String user_name, @Query("feedback_message") String feedback_message);


    @Headers({
            "Authorization", "your token",
            "Content-Type", "application/json",
            "User-Agent", "imgfornote",
            "charset", "UTF-8"
    })
    @FormUrlEncoded
    @POST("uploadusersex")
    Observable<FeedBackEntity> upLoadUserMessage(@Body UserInfo info);




    @Headers({
            "Content-Type: application/json"
    })
    @POST("userregister/")
    Observable<ResponseBody> userRegister(@Body RequestBody requestBody);




    @Headers({
            "Content-Type: application/json"
    })
    @POST("newuserwithtoken/")
    Observable<ResponseBody> newuserlogin(@Body RequestBody requestBody);



}
