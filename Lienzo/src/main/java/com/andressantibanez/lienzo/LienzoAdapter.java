package com.andressantibanez.lienzo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrés on 12/7/15.
 */
public class LienzoAdapter extends RecyclerView.Adapter<LienzoAdapter.ViewHolder> {


    ArrayList<String> mImagesPaths;
    boolean mIsVerticalGallery;


    public LienzoAdapter(ArrayList<String> imagesPaths, boolean isVerticalGallery) {
        mImagesPaths = imagesPaths;
        mIsVerticalGallery = isVerticalGallery;
    }


    /**
     * Adapter implementation
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(mIsVerticalGallery)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_vertical, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_horizontal, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String imagePath = mImagesPaths.get(position);

        Picasso.with(holder.imagePlaceholder.getContext())
                .load(imagePath)
                .into(holder.imagePlaceholder);

        holder.imagePlaceholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                context.startActivity(LienzoActivity.launchIntent(context, mImagesPaths, position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImagesPaths.size() ;
    }


    /**
     * ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePlaceholder;

        public ViewHolder(View view) {
            super(view);

            imagePlaceholder = (ImageView) view.findViewById(R.id.image_placeholder);
        }
    }

}
