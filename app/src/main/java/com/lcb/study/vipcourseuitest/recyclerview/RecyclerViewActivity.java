package com.lcb.study.vipcourseuitest.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcb.study.vipcourseuitest.R;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerViewActivity";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.table);
//        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setAdapter(new TwoAdapter(20));

    }

    class MyAdapter implements RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecyclerViewActivity.this).inflate(
                    R.layout.item_table1, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            Log.i(TAG, "onCreateViewHolder: " + myViewHolder.hashCode());
            return myViewHolder;
        }

        @Override
        public MyViewHolder onBindViewHolder(MyViewHolder viewHodler, int position) {
            viewHodler.tv.setText("码牛为你"+position);
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 2 == 1) {
                return 0;
            }else {
                return 1;
            }
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        @Override
        public int getHeight(int index) {
            return 200;
        }
    }

    class MyViewHolder extends ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.text1);
        }
    }

    class ImageViewHolder extends ViewHolder{
        TextView tv;
        ImageView imageView;
        public ImageViewHolder(View view) {
            super(view);
            tv=(TextView) view.findViewById(R.id.text2);
            imageView=(ImageView) view.findViewById(R.id.img);
        }
    }
    class TwoAdapter implements RecyclerView.Adapter<ViewHolder> {
        int count;
        public TwoAdapter( int count) {
            this.count = count;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == 0) {  //根据不同的viewtype,加载不同的布局
                view = LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_table1, parent, false);
                return new MyViewHolder(view);
            } else {
                view = LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_table2, parent, false);
                return new ImageViewHolder(view);
            }
        }

        @Override
        public ViewHolder onBindViewHolder(ViewHolder viewHodler, int position) {
            switch (getItemViewType(position)) {
                case 0:  //不同的布局，做不同的事
                    MyViewHolder holderOne = (MyViewHolder) viewHodler;
                    holderOne.tv.setText("码牛教育 布局1 ");
                    break;
                case 1:
                    ImageViewHolder holderTwo = (ImageViewHolder) viewHodler;
                    holderTwo.tv.setText("码牛教育 布局2 ");
            }

            return null;
        }

        @Override
        public int getItemViewType(int position) {
            if (position%2==1) {   //根据你的条件，返回不同的type
                return 0;
            } else {
                return 1;
            }
        }
        @Override
        public int getItemCount() {
            return count;
        }

        @Override
        public int getHeight(int index) {
            return 200;
        }
    }
}
