package ch.fhnw.justme;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ch.fhnw.justme.model.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatEntryAdapter extends RecyclerView.Adapter<ChatEntryAdapter.ChatEntryViewHolder> {

    ArrayList<Message> chatEntries;

    public ChatEntryAdapter(ArrayList<Message> chatEntries) {
        this.chatEntries = chatEntries;
    }

    public static abstract class ChatEntryViewHolder extends RecyclerView.ViewHolder {
        public ChatEntryViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void setContent(Object object);
    }

    public static class ChatEntryTextViewHolder extends ChatEntryViewHolder {

        public TextView textView;

        public ChatEntryTextViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.chat_entry);
        }

        @Override
        public void setContent(Object object) {
            this.textView.setText((String) object);
        }
    }

    public static class ChatEntryImageViewHolder extends ChatEntryViewHolder {

        public ImageView imageView;

        public ChatEntryImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        @Override
        public void setContent(Object object) {
            this.imageView.setImageBitmap((Bitmap) object);
        }
    }

    @NonNull
    @Override
    public ChatEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int resource;
        ChatEntryViewHolder holder;

        if (Message.Sender.USER.getValue() == i) {
            resource = R.layout.user_chat_entry;
            holder = getChatEntryTextViewHolder(viewGroup, resource);
        } else if (Message.Sender.BOT.getValue() == i) {
            resource = R.layout.bot_chat_entry;
            holder = getChatEntryTextViewHolder(viewGroup, resource);
        } else {
            resource = R.layout.user_picture_entry;
            holder = getChatEntryImageViewHolder(viewGroup, resource);
        }

        return holder;
    }

    private ChatEntryViewHolder getChatEntryTextViewHolder(@NonNull ViewGroup viewGroup, int resource) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ChatEntryTextViewHolder(view);
    }

    private ChatEntryViewHolder getChatEntryImageViewHolder(@NonNull ViewGroup viewGroup, int resource) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ChatEntryImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatEntryViewHolder chatEntryViewHolder, int i) {
        if (chatEntryViewHolder instanceof ChatEntryImageViewHolder) {
            chatEntryViewHolder.setContent(chatEntries.get(i).getBitmap());
        }

        if (chatEntryViewHolder instanceof ChatEntryTextViewHolder) {
            chatEntryViewHolder.setContent(chatEntries.get(i).getText());
        }
    }

    @Override
    public int getItemCount() {
        return chatEntries.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chatEntries.get(position).getSender().getValue();
    }
}
