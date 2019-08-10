package com.example.untitledproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ViewHolder>{
    FirstLoginActivity firstLoginActivity;
    private ArrayList mData = null ;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView ;

        ViewHolder(View itemView) {
            super(itemView) ;
            textView = itemView.findViewById(R.id.interestTextView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int index = getLayoutPosition();
                    firstLoginActivity.interestsFlag[index] = !firstLoginActivity.interestsFlag[index];
                    int color = (firstLoginActivity.interestsFlag[index]) ? 0xFF00BCD4 : 0xFFFFFFFF;
                    textView.setBackgroundColor(color);
                    for (int i = 0; i < 5; i++) {
                        if(firstLoginActivity.interestsFlag[i]){
                            firstLoginActivity.firstLoginButton.setVisibility(View.VISIBLE);
                            break;
                        }
                        if(i == 4){
                            firstLoginActivity.firstLoginButton.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });
        }
    }
    public InterestsAdapter(ArrayList list, FirstLoginActivity firstLoginActivity) {
        this.firstLoginActivity = firstLoginActivity;
        mData = list ;
    }

    @Override
    public InterestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.interests_item_layout, parent, false) ;
        InterestsAdapter.ViewHolder vh = new InterestsAdapter.ViewHolder(view) ;

        return vh ;
    }
    @Override
    public void onBindViewHolder(@NonNull InterestsAdapter.ViewHolder viewHolder, int position) {
        viewHolder.textView.setText((String)mData.get(position));
    }
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}