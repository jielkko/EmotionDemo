package com.hjl.emotionpicker.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hjl.emotionpicker.R;

import java.util.List;

public class EmotionDotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "PersonSelectedAdapter";

    private Context mContext;
    private List<String> mAppData;
    private int choose = 0;

    public EmotionDotAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mAppData = data;
    }

    public void setChoose(int c) {
        choose = c;
    }

    // 设置ITEM类型，可以自由发挥，返回Item的样式。
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder holder = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_emotion_dot, parent, false);
        holder = new ListViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final String item = mAppData.get(position);
        if (holder instanceof ListViewHolder) {
            final ListViewHolder itemViewHolder = (ListViewHolder) holder;


            itemViewHolder.mDot.setImageDrawable((position == choose) ? itemViewHolder.mDotBlue : itemViewHolder.mDotGray);


            itemViewHolder.mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonInterface != null) {
                        int position = itemViewHolder.getLayoutPosition();
                        buttonInterface.onclick(v, position);
                    }

                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return mAppData.size();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mContainer;
        private ImageView mDot;

        private Drawable mDotGray;
        private Drawable mDotBlue;
        private Drawable mAddGray;
        private Drawable mAddBlue;

        public ListViewHolder(View view) {
            super(view);
            mContainer = (LinearLayout) view.findViewById(R.id.container);
            mDot = (ImageView) view.findViewById(R.id.dot);

            Resources resources = view.getResources();
           /*  VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(resources,R.drawable.ic_dot_gray,mContext.getTheme());
            //你需要改变的颜色
            vectorDrawableCompat.setTint(resources.getColor(R.color.colorPrimary));*/
            mDotGray = resources.getDrawable(R.drawable.shape_emotion_dot_gray);
            mDotBlue = resources.getDrawable(R.drawable.shape_emotion_dot_blue);
        }
    }


    private ButtonInterface buttonInterface;

    public void buttonSetOnclick(ButtonInterface buttonInterface) {
        this.buttonInterface = buttonInterface;
    }

    public interface ButtonInterface {
        public void onclick(View view, int position);
    }

}

