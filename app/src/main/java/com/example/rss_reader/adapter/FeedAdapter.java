package com.example.rss_reader.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rss_reader.R;
import com.example.rss_reader.interfaces.ItemClickListener;
import com.example.rss_reader.model.RSSObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row,parent,false);

        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtPubDate.setText(rssObject.getItems().get(position).getPubDate());
        holder.txtContent.setText(rssObject.getItems().get(position).getContent());

        // TODO maybe not here

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(@NonNull View view, int position, boolean isLongClick) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(browserIntent);
                System.out.println("onClick " + browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return rssObject.items.size();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView txtTitle, txtPubDate,txtContent;
        private ItemClickListener itemClickListener;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtPubDate = (TextView) itemView.findViewById(R.id.txtPubDate);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);

            // set event
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);

        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),true);

            return true;
        }
    }
}
