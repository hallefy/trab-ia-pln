package com.example.halle.naturallanguageapi.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.halle.naturallanguageapi.R;

import java.util.List;

/**
 * Created by halle on 29/08/2017.
 */

public class Adapter extends RecyclerView.Adapter<Holder>  {

    private List<FeedItem> feedItemList;
    private Context mContext;


    public Adapter(Context context, List<FeedItem> feedItemList){
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, null);

        return new Holder(v, mContext);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final FeedItem feedItem = feedItemList.get(position);
        //Setting text view

        holder.palavra.setText(feedItem.getPalavra());
        holder.tag.setText(feedItem.getTag());
    }


    @Override
    public int getItemCount() {

        return feedItemList.size();

    }
}
