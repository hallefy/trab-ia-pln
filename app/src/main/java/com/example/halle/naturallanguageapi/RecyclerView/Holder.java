package com.example.halle.naturallanguageapi.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.halle.naturallanguageapi.R;

/**
 * Created by halle on 29/08/2017.
 */

public class Holder extends RecyclerView.ViewHolder {

    protected TextView palavra, tag;

    public Holder(View itemView, Context context) {
        super(itemView);

        palavra = (TextView) itemView.findViewById(R.id.tvPalavra);
        tag = (TextView) itemView.findViewById(R.id.tvTag);
    }
}
