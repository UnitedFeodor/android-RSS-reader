package com.example.rss_reader.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.rss_reader.R;
import com.example.rss_reader.interfaces.ItemClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView txtTitle, txtPubDate,txtContent;
    private ItemClickListener itemClickListener;

    public FeedViewHolder(@NonNull View itemView) {
        super(itemView);

        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtPubDate = (TextView) itemView.findViewById(R.id.txtPubDate);
        txtContent = (TextView) itemView.findViewById(R.id.txtContent);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}

public class FeedAdapter {
}
