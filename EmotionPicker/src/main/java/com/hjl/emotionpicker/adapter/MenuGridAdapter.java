package com.hjl.emotionpicker.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.emotionpicker.R;
import com.hjl.emotionpicker.model.EmotionModel;

import java.util.List;

public class MenuGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "MenuGridAdapter";

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_ITEM_LIST = 1;
    private Context mContext;
    private List<EmotionModel> mAppData;


    public MenuGridAdapter(Context context, List<EmotionModel> data) {
        this.mContext = context;
        this.mAppData = data;
    }


    @Override
    public int getItemViewType(int position) {
        return mAppData.get(position).getName().equals("删除") ? 0 : 1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_emotion_delete, parent, false);
                holder = new DeleteViewHolder(view);
                break;
            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_emotion, parent, false);
                holder = new GridViewHolder(view);
                break;
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_emotion, parent, false);
                holder = new GridViewHolder(view);
                break;

        }
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final EmotionModel item = mAppData.get(position);


        if (holder instanceof DeleteViewHolder) {
            final DeleteViewHolder itemViewHolder = (DeleteViewHolder) holder;
            itemViewHolder.mIcon.setImageResource(item.getIcon());
            itemViewHolder.mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        int position = itemViewHolder.getLayoutPosition();
                        mOnItemClickListener.onDeleteClick(v, position);
                    }
                }
            });
        }

        if (holder instanceof GridViewHolder) {
            final GridViewHolder itemViewHolder = (GridViewHolder) holder;
            itemViewHolder.mIcon.setImageResource(item.getIcon());
            itemViewHolder.mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        int position = itemViewHolder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(v, position);
                    }
                }
            });

        }


    }


    @Override
    public int getItemCount() {
        return mAppData.size();
    }

    public class DeleteViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mContainer;
        private ImageView mIcon;


        public DeleteViewHolder(View view) {
            super(view);
            mContainer = (LinearLayout) view.findViewById(R.id.container);
            mIcon = (ImageView) view.findViewById(R.id.icon);
        }
    }



    public class GridViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mContainer;
        private ImageView mIcon;

        public GridViewHolder(View view) {
            super(view);
            mContainer = (LinearLayout) view.findViewById(R.id.container);
            mIcon = (ImageView) view.findViewById(R.id.icon);
        }
    }


    //点击
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onDeleteClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //长按
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }



}

