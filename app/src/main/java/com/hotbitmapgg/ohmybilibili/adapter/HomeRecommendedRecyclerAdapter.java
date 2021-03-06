package com.hotbitmapgg.ohmybilibili.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotbitmapgg.ohmybilibili.R;
import com.hotbitmapgg.ohmybilibili.adapter.base.AbsRecyclerViewAdapter;
import com.hotbitmapgg.ohmybilibili.entity.recommended.Body;
import com.hotbitmapgg.ohmybilibili.entity.recommended.Result;
import com.hotbitmapgg.ohmybilibili.module.home.AllHotRankActivity;
import com.hotbitmapgg.ohmybilibili.module.video.BiliBiliLivePlayerActivity;
import com.hotbitmapgg.ohmybilibili.module.video.VideoDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/8/4 12:45
 * 100332338@qq.com
 * <p/>
 * 首页推荐ItemAdapter
 */
public class HomeRecommendedRecyclerAdapter extends AbsRecyclerViewAdapter
{

    private List<Result> results = new ArrayList<>();

    private int[] icons = new int[]{
            R.drawable.ic_header_hot,
            R.drawable.ic_head_live,
            R.drawable.ic_category_t13,
            R.drawable.ic_category_t1,
            R.drawable.ic_category_t3,
            R.drawable.ic_category_t129,
            R.drawable.ic_category_t4,
            R.drawable.ic_category_t119,
            R.drawable.ic_category_t36,
            R.drawable.ic_header_activity_center,
            R.drawable.ic_category_t160,
            R.drawable.ic_category_t155,
            R.drawable.ic_category_t5,
            R.drawable.ic_category_t11,
            R.drawable.ic_category_t23
    };

    public HomeRecommendedRecyclerAdapter(RecyclerView recyclerView, List<Result> results)
    {

        super(recyclerView);
        this.results = results;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_home_recommended, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {

        if (holder instanceof ItemViewHolder)
        {
            Result result = results.get(position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mTypeImg.setImageResource(icons[position]);
            itemViewHolder.mTypeTv.setText(result.getHead().getTitle());
            if (position == 0)
            {
                itemViewHolder.mTypeMore.setVisibility(View.GONE);
                itemViewHolder.mTypeRankBtn.setVisibility(View.VISIBLE);
                itemViewHolder.mAllLiveNum.setVisibility(View.GONE);
            } else if (position == 1)
            {
                itemViewHolder.mTypeRankBtn.setVisibility(View.GONE);
                itemViewHolder.mTypeMore.setVisibility(View.VISIBLE);
                itemViewHolder.mAllLiveNum.setVisibility(View.VISIBLE);
                itemViewHolder.mAllLiveNum.setText("当前" + result.getHead().getCount() + "个直播");
            } else
            {
                itemViewHolder.mTypeRankBtn.setVisibility(View.GONE);
                itemViewHolder.mTypeMore.setVisibility(View.VISIBLE);
                itemViewHolder.mAllLiveNum.setVisibility(View.GONE);
            }

            initGrid(itemViewHolder, result, position);
            setRankBtnClick(itemViewHolder);
        }
        super.onBindViewHolder(holder, position);
    }

    private void setRankBtnClick(ItemViewHolder itemViewHolder)
    {

        itemViewHolder.mTypeRankBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                getContext().startActivity(new Intent(getContext(), AllHotRankActivity.class));
            }
        });
    }

    private void initGrid(ItemViewHolder itemViewHolder, Result result, final int pos)
    {

        final ArrayList<Body> body = result.getBody();
        itemViewHolder.mItemRecycle.setHasFixedSize(true);
        itemViewHolder.mItemRecycle.setNestedScrollingEnabled(false);
        itemViewHolder.mItemRecycle.setLayoutManager(new GridLayoutManager(getContext(), 2));
        HomeRecommendedGridAdapter mGridAdapter = new HomeRecommendedGridAdapter(itemViewHolder.mItemRecycle, body, pos);
        itemViewHolder.mItemRecycle.setAdapter(mGridAdapter);
        mGridAdapter.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(int position, ClickableViewHolder holder)
            {

                int aid = Integer.valueOf(body.get(position).getParam());
                if (pos == 1)
                {
                    //直播item点击事件
                    Intent intent = new Intent(getContext(), BiliBiliLivePlayerActivity.class);
                    intent.putExtra("cid", Integer.valueOf(body.get(position).getParam()));
                    intent.putExtra("title", body.get(position).getTitle());
                    intent.putExtra("online", body.get(position).getOnline());
                    intent.putExtra("face", body.get(position).getUpFace());
                    intent.putExtra("name", body.get(position).getUp());
                    intent.putExtra("mid", "");
                    getContext().startActivity(intent);
                } else if (pos == 2)
                {
                    //番剧item点击事件,暂时没有实现
                } else
                {
                    //视频点击事件
                    VideoDetailsActivity.launch((Activity) getContext(), aid);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {

        return results.size();
    }

    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder
    {

        public RecyclerView mItemRecycle;

        public ImageView mTypeImg;

        public TextView mTypeTv;

        public TextView mTypeMore;

        public TextView mTypeRankBtn;

        public TextView mAllLiveNum;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mItemRecycle = $(R.id.item_grid);
            mTypeImg = $(R.id.item_type_img);
            mTypeTv = $(R.id.item_type_tv);
            mTypeMore = $(R.id.item_type_more);
            mTypeRankBtn = $(R.id.item_type_rank_btn);
            mAllLiveNum = $(R.id.item_live_all_num);
        }
    }
}
