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
import com.example.spaceinvaders.database.Achievement;
import com.example.spaceinvaders.database.AchievementDao;
import com.example.spaceinvaders.database.Counter;

import java.util.List;

public class AchievementsViewAdapter extends RecyclerView.Adapter<AchievementsViewAdapter.ViewHolder> {
    private final Context context;
    private List<Achievement> achievements = null;

    public AchievementsViewAdapter(Context context){
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAchievements(List<Achievement> achievements){
        this.achievements = achievements;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Achievement achievement = achievements.get(position);
        Counter.AchievementType type = Counter.AchievementType.valueOf(achievement.getName());
        holder.name.setText(type.getAchievementText(achievement.getValue()));
    }

    @Override
    public int getItemCount() {
        if(achievements!=null)
            return achievements.size();
        return 0;
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.row_name);
        }
    }
}
