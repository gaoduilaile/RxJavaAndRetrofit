package com.example.administrator.rxjavaandretrofit.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.rxjavaandretrofit.R;
import com.example.administrator.rxjavaandretrofit.api.HttpMethods;
import com.example.administrator.rxjavaandretrofit.jbean.FeedBackEntity;
import com.example.administrator.rxjavaandretrofit.jbean.MovieEntity;
import com.example.administrator.rxjavaandretrofit.jbean.UserInfo;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.toString();
    @Bind(R.id.click_me_BN)
    Button mClickMeBN;
    @Bind(R.id.result_TV)
    TextView mResultTV;
    private Subscriber<MovieEntity> mSubscriber;
    private Subscriber<FeedBackEntity> mSubscriber2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListening();

    }

    private void initListening() {
        mClickMeBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovie();
//                editFeedBack();
//                upLoadUserMessage();
            }

        });
    }





    //进行网络请求
    private void getMovie() {

        HttpMethods.getInstance(this).getTopMovie(0, 5, new HttpMethods.Func() {
            @Override
            public void onJsonString(String response) throws JSONException {
                Gson gson=new Gson();
                MovieEntity movieEntity = gson.fromJson(response, MovieEntity.class);
                int count = movieEntity.getCount();
                String title = movieEntity.getTitle();
                int total = movieEntity.getTotal();
                List<MovieEntity.SubjectsBean> subjects = movieEntity.getSubjects();
                mResultTV.setText("title=" + title + "  count=" + count + " total=" + "   subjects=" + subjects.toString());


//            }
            }

            @Override
            public void onError() {

            }
        });
    }


    //反馈信息
    private void editFeedBack() {
        mSubscriber2 = new Subscriber<FeedBackEntity>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);

                mResultTV.setText(e.getMessage());
            }

            @Override
            public void onNext(FeedBackEntity feedBackEntity) {
                Log.e(TAG, "onNext: ");
                boolean upload_result = feedBackEntity.isUpload_result();
                mResultTV.setText(upload_result + "");
            }
        };

        HttpMethods.getInstance(this).editFeedBack(mSubscriber2, "13783539372", "henhao1");
    }



    //下载用户信息
    private void upLoadUserMessage() {
        mSubscriber2 = new Subscriber<FeedBackEntity>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);

                mResultTV.setText(e.getMessage());
            }

            @Override
            public void onNext(FeedBackEntity feedBackEntity) {
                Log.e(TAG, "onNext: ");
                boolean upload_result = feedBackEntity.isUpload_result();
                mResultTV.setText(upload_result + "");
            }
        };

        UserInfo info=new UserInfo();
        info.setUser_name("13783539372");
        info.setImage_url("");
        info.setUser_other_name("gaoqiong");
        info.setUser_sex(0);
        info.setUser_age(0);
        info.setUser_eye_condition(0);

        HttpMethods.getInstance(this).upLoadUserMessage(mSubscriber2,info);

    }
}
