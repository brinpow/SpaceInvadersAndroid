package com.example.spaceinvaders.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.database.HighScore;
import com.example.spaceinvaders.database.HighScoresDao;

import java.util.List;

public class HighScoresViewAdapter extends RecyclerView.Adapter<HighScoresViewAdapter.ViewHolder> {
    private final Context context;
    private final HighScoresDao highScoresDao;

    public HighScoresViewAdapter(Context context, HighScoresDao highScoresDao){
        this.context = context;
        this.highScoresDao = highScoresDao;
    }

    @NonNull
    @Override
    public HighScoresViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HighScoresViewAdapter.ViewHolder holder, int position) {
        List<HighScore> highScores = highScoresDao.getAllHighScores();
        HighScore highScore = highScores.get(position);
        holder.name.setText("Score: "+highScore.getHighScore());
    }

    @Override
    public int getItemCount() {
        List<HighScore> highScores = highScoresDao.getAllHighScores();
        return highScores.size();
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.row_name);
        }
    }
}
