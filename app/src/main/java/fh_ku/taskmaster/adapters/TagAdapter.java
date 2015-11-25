package fh_ku.taskmaster.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import fh_ku.taskmaster.R;
import fh_ku.taskmaster.models.Tag;
import fh_ku.taskmaster.repositories.TagRepository;

/**
 * Created by Benedikt on 25.11.2015.
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder>{

    private TagRepository tagRepository;
    private Cursor cursor;

    public TagAdapter(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public void init() { this.cursor = this.tagRepository.queryAllTags(); }

    public static class TagViewHolder extends RecyclerView.ViewHolder{
        public TextView tag;

        public TagViewHolder(View itemView){
            super(itemView);

            tag = (TextView) itemView.findViewById(R.id.tag_name);
        }
    }

    public Tag getTagAtPosition(int position) {
        return this.tagRepository.tagAtCursorPosition(this.cursor, position);
    }

    public TagAdapter.TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_list_item,parent,false);
        return new TagAdapter.TagViewHolder(itemView);
    }

    public void onBindViewHolder(TagAdapter.TagViewHolder viewHolder, final int position) {
        final Tag tag = getTagAtPosition(position);
        viewHolder.tag.setText(tag.getTag());

        viewHolder.tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.i("TAG ADAPTER", "Tag is clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.cursor != null ? this.cursor.getCount() : 0;
    }
}
