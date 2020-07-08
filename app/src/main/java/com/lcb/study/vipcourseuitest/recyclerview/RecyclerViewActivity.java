package com.lcb.study.vipcourseuitest.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        recyclerView.setAdapter(new MyAdapter());

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
            return 20000;
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
}
