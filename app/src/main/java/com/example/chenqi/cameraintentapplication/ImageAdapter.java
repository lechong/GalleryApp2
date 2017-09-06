package com.example.chenqi.cameraintentapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.zip.Inflater;

/**
 * Created by chenqi on 6/09/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{

    private File imagesFile;

    public ImageAdapter(File folderFile){
        imagesFile = folderFile;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_images_relative_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File imageFile = imagesFile.listFiles()[position];
        Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());


        holder.getImageView().setImageBitmap(imageBitmap);

    }

    @Override
    public int getItemCount() {
        return imagesFile.listFiles().length;
    }
//holder
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

        public ViewHolder(View view){
            super(view);

            imageView = (ImageView)view.findViewById(R.id.imageGalleryView);
        }

        public ImageView getImageView(){
            return imageView;
        }
    }
}
