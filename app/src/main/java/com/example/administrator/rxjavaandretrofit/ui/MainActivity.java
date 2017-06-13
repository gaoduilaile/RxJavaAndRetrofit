package com.example.administrator.rxjavaandretrofit.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.rxjavaandretrofit.R;
import com.example.administrator.rxjavaandretrofit.api.HttpMethods;
import com.example.administrator.rxjavaandretrofit.jbean.MovieEntity;

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
            }
        });
    }

    //进行网络请求
    private void getMovie() {
        mSubscriber = new Subscriber<MovieEntity>() {
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
            public void onNext(MovieEntity movieEntity) {
                Log.e(TAG, "onNext: ");

                int count = movieEntity.getCount();
                String title = movieEntity.getTitle();
                int total = movieEntity.getTotal();
                List<MovieEntity.SubjectsBean> subjects = movieEntity.getSubjects();
                mResultTV.setText("title=" + title + "  count=" + count + " total=" + "   subjects=" + subjects.toString());
            }
        };
        HttpMethods.getInstance().getTopMovie(mSubscriber, 0, 10);
    }
}
