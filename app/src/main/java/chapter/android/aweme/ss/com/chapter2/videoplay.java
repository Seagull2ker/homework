package chapter.android.aweme.ss.com.chapter2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;

public class videoplay extends AppCompatActivity {
    private VideoView videoView;

    private static final String TAG = "Homework";
    //是否第二次点击标志,true代表未开始双击
    private boolean flag = true;
    // 第一次点击时间
    private long one = 0;
    // 第二次点击时间
    private long two = 0;
    // 算为双击点击时间间隔
    private int interval = 500;

    //private LottieAnimationView animationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.videoplay);

        //获取视频序号并播放
        Intent intent = getIntent();
        String message = intent.getStringExtra("user");
        int videoid;//
        switch(message){
            case "0":videoid = R.raw.bytedance;break;
            case "1":videoid = R.raw.a;break;
            case "2":videoid = R.raw.b;break;
            case "3":videoid = R.raw.c;break;
            case "4":videoid = R.raw.d;break;
            // TODO 播放拍摄的视频
            default:videoid = R.raw.b;
        }
        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(getVideoPath(videoid));
        videoView.setMediaController(new MediaController(videoplay.this));
        videoView.start();
    }
    public void twoTouch(View v) {
        // 判断是否第二次点击
        if (!flag) {
            // 取第二次时间时超时，重置标志之类
            if ((System.currentTimeMillis() - one) > interval) {
                if(videoView.isPlaying())
                    videoView.pause();
                else{
                    videoView.start();}
                flag = true;
                one = 0;
                two = 0;
            }
        }
        // 判断标志给 one two 赋值，初始 true 给 one 赋值
        if (flag) {
            Log.i(TAG, "one 赋值");
            one = System.currentTimeMillis();
            flag = false;
        } else {
            Log.i(TAG, "two 赋值");
            two = System.currentTimeMillis();
            if ((two - one) > interval) {
                // 取第二次时间时超时
                flag = true;
                one = 0;
                two = 0;
            }
        }
        // 计算两次点击间隔
        long t_o = two - one;
        // 判断间隔是否在双击范围中
        if (t_o > 0 && t_o < interval) {
            Log.i(TAG, "双击发生");
            // 双击发生
            Toast.makeText(getApplicationContext(), "点赞成功!!!",
                    Toast.LENGTH_SHORT).show();
            flag = true;
            one = 0;
            two = 0;
        } else if (two > 0 && !flag) {
            Log.i(TAG, "双击没有发生");
            // 双击没有发生
            flag = true;
            one = 0;
            two = 0;
        }
    }
    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
}