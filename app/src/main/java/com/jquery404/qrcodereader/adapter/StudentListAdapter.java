package com.jquery404.qrcodereader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jquery404.qrcodereader.R;
import com.jquery404.qrcodereader.helper.MyForm;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Faisal on 8/27/17.
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentlistHolder> {
    private List<MyForm> songs = new ArrayList<>();
    private Context context;

    public StudentListAdapter(Context context, List<MyForm> songs) {
        this.context = context;
        this.songs = songs;
    }

    @Override
    public StudentlistHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_studentlist, viewGroup, false);
        return new StudentlistHolder(v, context, songs);
    }

    @Override
    public void onBindViewHolder(StudentlistHolder holder, int i) {
        MyForm song = songs.get(i);
        holder.songTitle.setText(song.getName() + " " + song.getPrName() + " " + song.getMrName());
        holder.songArtist.setText(song.getCareer());
        holder.dni.setText(song.getShift());
        holder.btnPush.setTag(i);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }


    class StudentlistHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.song_title)
        TextView songTitle;
        @BindView(R.id.dni)
        TextView dni;
        @BindView(R.id.song_artist)
        TextView songArtist;
        @BindView(R.id.btn_push)
        Button btnPush;


        List<MyForm> songs = new ArrayList<>();
        Context context;

        StudentlistHolder(View itemView, Context context, List<MyForm> songs) {
            super(itemView);
            this.context = context;
            this.songs = songs;

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.btn_push)
        void onClick(View v) {
            Toast.makeText(context, ""+songs.get((int)v.getTag()).getQrcode(), Toast.LENGTH_SHORT).show();
        }
    }
}
