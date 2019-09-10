package com.geekbrains.lesson1.clear.presentation.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geekbrains.lesson1.R;
import com.geekbrains.lesson1.clear.domain.model.Article;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private ArrayList<Article> articlesList = new ArrayList<>();

    void setList(List<Article> data) {
        articlesList.clear();
        articlesList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(articlesList.get(position));
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView articleImage;
        private TextView articleTitle;
        private TextView articleContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            articleImage = itemView.findViewById(R.id.article_image);
            articleTitle = itemView.findViewById(R.id.article_title);
            articleContent = itemView.findViewById(R.id.article_content);
        }

        void bind(Article item) {
            articleTitle.setText(item.title);
            articleContent.setText(item.content);

            Glide.with(itemView.getContext())
                    .load(item.urlToImage)
                    .into(articleImage);
        }
    }
}
