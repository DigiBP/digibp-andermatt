package ch.fhnw.justme;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ch.fhnw.justme.model.PictureDescription;

public class StoreItemAdapter extends RecyclerView.Adapter<StoreItemAdapter.StoreItemViewHolder> {
    List<PictureDescription> items;
    private AmountChangedListener listener;

    public StoreItemAdapter(List<PictureDescription> items, AmountChangedListener onAmountChangedListener) {
        this.items = items;
        this.listener = onAmountChangedListener;
    }


    public class StoreItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView description;
        public TextView price;
        public TextView counter;
        public Button more;
        public Button less;
        public int c = 0;

        public StoreItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.picture);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            counter = itemView.findViewById(R.id.counter);

            more = itemView.findViewById(R.id.more);
            less = itemView.findViewById(R.id.less);
        }
    }

    @NonNull
    @Override
    public StoreItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display, parent, false);
        return new StoreItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreItemViewHolder holder, int position) {
        AsyncTask<String, Integer, Drawable> task = new DownloadImageTask().execute(items.get(position).getUrl());

        holder.description.setText(items.get(position).getDescription());
        holder.price.setText(items.get(position).getPrice().toString());
        holder.counter.setText(Integer.toString(holder.c));

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.c += 1;
                holder.counter.setText(Integer.toString(holder.c));
                listener.onAmountChanged(items.get(position).getPrice());
            }
        });

        holder.less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.c > 0) {
                    holder.c -= 1;
                    holder.counter.setText(Integer.toString(holder.c));
                    listener.onAmountChanged(-items.get(position).getPrice());
                }
            }
        });

        try {
            holder.imageView.setImageDrawable(task.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Integer, Drawable> {

        @Override
        protected Drawable doInBackground(String... urls) {
            try {
                InputStream is = (InputStream) new URL(urls[0]).getContent();
                return Drawable.createFromStream(is, "src name");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
