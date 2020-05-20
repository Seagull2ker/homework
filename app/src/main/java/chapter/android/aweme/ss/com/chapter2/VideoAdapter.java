package chapter.android.aweme.ss.com.chapter2;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ImageViewHolder>{
    private static final String TAG = "VideoAdapter";//标签
    private final ListItemClickListener mOnClickListener;//监听器，即activity
    private static int viewHolderCount;//viewHolder的数量
    private List<String> mDatas;//数据
    private Context mContext;
    private String[] image = new String[]{"https://i0.hdslb.com/bfs/archive/b1c14459274d12a7794a4d0762114a62b48d2736.jpg@336w_190h.webp",
            "https://i1.hdslb.com/bfs/archive/272e9f1d6d92e60a84cd73795eb5f51dd2f8a0da.jpg@336w_190h.webp",
            "https://i0.hdslb.com/bfs/archive/e9227e44a97b57a36f520c0ae42ba417d4fd6573.jpg@336w_190h.webp",
            "https://i2.hdslb.com/bfs/archive/f36887e8edd7489db92f1b12a376ed13c6bcc1d1.jpg@336w_190h.webp",
            "https://i0.hdslb.com/bfs/archive/d1614ba3b5721d1939a7d8a7b276b871845769a1.jpg@336w_190h.webp"};

    //构造函数
    public VideoAdapter(Context context ,ListItemClickListener listener) {
        mOnClickListener = listener;//点击监听器，即activity
        mContext = context;
        viewHolderCount = 0;//viewHolder的数量
    }

    //这个方法主要为每个Item inflater生成出一个View，返回的是一个ViewHolder。
    //该方法把View直接封装在ViewHolder中，然后我们面向的是ViewHolder这个实例，当然这个ViewHolder需要我们自己去编写。
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.image_list_item;//item的xml布局资源信息
        LayoutInflater inflater = LayoutInflater.from(context);//创建一个LayoutInflater
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);//创建一个View对象
        ImageViewHolder viewHolder = new ImageViewHolder(view);//使用ImageViewHolder内部类，创建一个ViewHolder对象

        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: " + viewHolderCount);
        viewHolderCount++;//记录viewHolder数量加一
        return viewHolder;//将ViewHolder返回
    }

    //适配渲染数据到ViewHolder中
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder numberViewHolder, int position) {
        numberViewHolder.bind(position);
    }

    //返回数据集大小，即item的总数，总共有多少个条目
    @Override
    public int getItemCount() {
        return 5;
    }

    //ViewHolder类，一个继承RecyclerView.ViewHolder的内部类
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //自定义两个TextView成员
        private ImageView listItemNumberView;

        //构造函数
        public ImageViewHolder(@NonNull View itemView) {//传入一个view
            super(itemView);
            //将自定义的两个TextView与相应xml成员绑定
            listItemNumberView = (ImageView) itemView.findViewById(R.id.tv_item_number);
            itemView.setOnClickListener(this);//为传入的view设置点击监听，为此下面需重写onClick()函数
        }

        //绑定函数
        public void bind(int position) {
            Glide.with(mContext)
                    .load(image[position])
                    .into(listItemNumberView);
        }

        //首写点击函数，因为上面设置了为传入的view设置了监听，所以点击view就会调用此函数
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();//获取Adapter位置
            if (mOnClickListener != null) {//不为空
                mOnClickListener.onListItemClick(clickedPosition);//以clickedPosition为参数，调用接口中的onListItemClick函数
            }
        }
    }

    //接口
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
