package com.example.xrecyclview;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private XRecyclerView mRecyclerView;
    //商品头部
    public static final int ITEM_PRODUCT_HEAD = 3;
    //商品
    public static final int ITEM_PRODUCT = 4;
    //其他系列
    public static final int ITEM_OTHER = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycleView();
    }

    private void initRecycleView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mRecyclerView.loadMoreComplete();
                    }
                }, 1000);

            }
        });
        Adapter mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //todo 适配器
    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(viewGroup.getContext()).
//                    inflate(R.layout.item, viewGroup, false);
//            return new ViewHolder(view);
            //todo 复杂布局
            if (viewType == ITEM_PRODUCT_HEAD) {
                return new ProductHeadViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_head_tv, parent, false));
            } else if (viewType == ITEM_PRODUCT) {
                return new ProductViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_product, parent, false));
            } else if (viewType == ITEM_OTHER) {
                return new OtherViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_other, parent, false));
            } else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            if (viewHolder instanceof ProductHeadViewHolder) {
                ((ProductHeadViewHolder) viewHolder).tv_head.setText("头部");

            } else if (viewHolder instanceof ProductViewHolder) {

                Glide.with(getApplicationContext())
                        .load(R.drawable.icon_long)
                        .apply(GlideRequestOptions.getInstance().normalRequestOption())
                        .into(((ProductViewHolder) viewHolder).image_product);
                ((ProductViewHolder) viewHolder).tv_name.setText("我的名字1~");
                ((ProductViewHolder) viewHolder).tv_price.setText("我的价格1~");
            } else if (viewHolder instanceof OtherViewHolder) {
                Glide.with(getApplicationContext())
                        .load(R.drawable.icon_rule)
                        .apply(GlideRequestOptions.getInstance().normalRequestOption())
                        .into(((OtherViewHolder) viewHolder).image_other);
                ((OtherViewHolder) viewHolder).tv_name.setText("我的名字2~");
            }
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return ITEM_PRODUCT_HEAD;
            } else if (position == 1) {
                return ITEM_PRODUCT;
            } else {
                return ITEM_OTHER;
            }
        }

        /**
         * 商品头部
         */
        public class ProductHeadViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_head;

            public ProductHeadViewHolder(View itemView) {
                super(itemView);
                tv_head = itemView.findViewById(R.id.tv_head);
            }
        }


        /**
         * 商品
         */
        public class ProductViewHolder extends RecyclerView.ViewHolder {
            //图片
            public ImageView image_product;
            //名称
            public TextView tv_name;
            //价格
            public TextView tv_price;

            public ProductViewHolder(View itemView) {
                super(itemView);
                image_product = itemView.findViewById(R.id.image_product);
                tv_name = itemView.findViewById(R.id.tv_name);
                tv_price = itemView.findViewById(R.id.tv_price);
            }
        }

        /**
         * 其他
         */
        public class OtherViewHolder extends RecyclerView.ViewHolder {
            public ImageView image_other;
            public TextView tv_name;

            public OtherViewHolder(View itemView) {
                super(itemView);
                image_other = itemView.findViewById(R.id.image_other);
                tv_name = itemView.findViewById(R.id.tv_name);
            }
        }
    }


}
