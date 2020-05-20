package chapter.android.aweme.ss.com.chapter2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import chapter.android.aweme.ss.com.chapter2.BuildConfig;
import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import chapter.android.aweme.ss.com.chapter2.R;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements VideoAdapter.ListItemClickListener{

    private VideoAdapter mAdapter;//适配器
    private RecyclerView mNumbersListView;//RecyclerView
    private File imagePath;
    private final static int REQUEST_CAMERA = 123;
    private final static int REQUEST_PERMISSION = 123;
    private String[] mPermissionsArrays = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

//    private static final String TAG = "Homework";
//    //是否第二次点击标志
//    private boolean flag = true;
//    // 第一次点击时间
//    private long one = 0;
//    // 第二次点击时间
//    private long two = 0;
//    // 算为双击点击时间间隔
//    private int interval = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //配置recycleview
        mNumbersListView = findViewById(R.id.rv_numbers);
        mNumbersListView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//创建一个layoutManager
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//为layoutManager其设置样式
        mNumbersListView.setLayoutManager(layoutManager);//为RecyclerView设置layoutManager
        mAdapter = new VideoAdapter(MainActivity.this,MainActivity.this);//创建Adapter，输入item总数和当前activity。Adapter负责在列表中展示每个item
        mNumbersListView.setAdapter(mAdapter);//为RecyclerView设置Adapter

        //按钮
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                requestPermission();
            }
        });
    }

    private void requestPermission() {
        //权限
        if (!checkPermissionAllGranted(mPermissionsArrays)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(mPermissionsArrays, REQUEST_PERMISSION);
            } else {
                Toast.makeText(MainActivity.this, "出现错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "已经获取所有所需权限", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MyVideoActivity.class)); //跳转
        }
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        // 6.0以下不需要
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            Toast.makeText(this, "已经授权" + Arrays.toString(permissions), Toast.LENGTH_LONG).show();
        }
    }

    //只要recycleview上出现点击事件就会调用此函数（在GreenAdapter中规定的）
    @Override
    public void onListItemClick(int clickedItemIndex) {
        //开启另一个activity
        Intent intent = new Intent(this, videoplay.class);//创建intent
        intent.putExtra("user", clickedItemIndex+"");//把字符串放进intent
        startActivity(intent);
    }

//    public void twoTouch(View v){
//        if(!flag){
//            if((System.currentTimeMillis() - one) > interval) {
//                flag = true;
//                one = 0;
//                two = 0;
//            }
//        }
//        if (flag) {
//            Log.i(TAG, "one 赋值");
//            one = System.currentTimeMillis();
//            flag = false;
//            Toast.makeText(getApplicationContext(), "第一次点击！",
//                    Toast.LENGTH_SHORT).show();
//        }else {
//            Log.i(TAG, "two 赋值");
//            two = System.currentTimeMillis();
//            if ((two - one) > interval) {
//                // 取第二次时间时超时
//                flag = true;
//                one = 0;
//                two = 0;
//            }
//        }
//        // 计算两次点击间隔
//        long t_o = two - one;
//        // 判断间隔是否在双击范围中
//        if (t_o > 0 && t_o < interval) {
//            Log.i(TAG, "双击发生");
//            // 双击发生则设置标志之类复位
//            flag = true;
//            one = 0;
//            two = 0;
//            // 对话框构建器
//            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//            // 设置标题、内容
//            dialog.setTitle("提示");
//            dialog.setMessage("你双击了哈！！！");
//            // 设置不能使用 back 键关闭
//            dialog.setCancelable(false);
//            // 设置左边按钮
//            dialog.setPositiveButton("确定",
//                    new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //Toast.makeText(getApplicationContext(),
//                        // "点击了对话框确定钮！", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//            dialog.setNegativeButton("取消",
//                    new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Toast.makeText(getApplicationContext(),
//                                // "点击了对话框取消钮！", Toast.LENGTH_SHORT).show();
//                            }
//                    });
//            // 创建对话框
//            AlertDialog alert = dialog.create();
//            // 显示对话框
//            alert.show();
//        } else if (two > 0 && !flag) {
//            Log.i(TAG, "双击没有发生");
//            // 双击没有发生设置标志之类复位
//            flag = true;
//            one = 0;
//            two = 0;
//        }
//    }
}
