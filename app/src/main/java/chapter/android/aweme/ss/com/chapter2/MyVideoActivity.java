package chapter.android.aweme.ss.com.chapter2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;
import chapter.android.aweme.ss.com.chapter2.BuildConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.io.File;
import java.util.Locale;

public class MyVideoActivity extends AppCompatActivity{
    private final static int REQUEST_CAMERA = 123;
    private File videoPath;
    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_video);

        //按钮监听
        findViewById(R.id.buttonCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSystemCamera();
            }
        });
    }

    /**
     * 打开系统相机
     */
    private void openSystemCamera() {
        //TODO 这个函数不知道哪里写错了，总是打不开摄像头
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

//        String filePath = getExternalFilesDir("") + "/" + System.currentTimeMillis() + ".mp4";   // 保存路径
//        Log.d("chenhui",filePath);
//        Uri outUri = Uri.fromFile(new File(filePath));//将路径转换为Uri对象
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);

        cameraIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,30);//设置视频录制的最长时间
        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//设置视频录制的画质
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    //重写回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            //TODO 回调之后播放视频
        }
    }
}
