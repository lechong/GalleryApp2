package com.example.chenqi.cameraintentapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraIntentActivity extends AppCompatActivity {
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private ImageView mPhotoCaptureImageView;
    private String mImageFileLocation="";
    private String GALLERY_LOCATION = "image galley";
    private File mGalleryFolder;

    private RecyclerView mRecyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);

        createImageGallery();

        mRecyclerView = (RecyclerView) findViewById(R.id.galleryRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter imageAdapter = new ImageAdapter(mGalleryFolder);
        mRecyclerView.setAdapter(imageAdapter);


    }

    //click
    public void takePhoto(View view) {
        Intent CallCameraApplicationIntent = new Intent();
        CallCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile =null;
       try{
            photoFile= createImageFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        CallCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        startActivityForResult(CallCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
    }

    protected void onActivityResult(int requestCode, int resultcode, Intent data) {
    if (requestCode == ACTIVITY_START_CAMERA_APP && resultcode == RESULT_OK) {
        //Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();
       //Bundle extras = data.getExtras();
       // Bitmap photoCaptureBitmap = (Bitmap) extras.get("data");
       // mPhotoCaptureImageView.setImageBitmap(photoCaptureBitmap);
       // use BitmaoFactory to creat a fullsize image
        //Bitmap photoCapturedBitmap= BitmapFactory.decodeFile(mImageFileLocation);
        //mPhotoCaptureImageView.setImageBitmap(photoCapturedBitmap);
        //setReducedImageSize();
        RecyclerView.Adapter newImageAdapter = new ImageAdapter(mGalleryFolder);
        mRecyclerView.swapAdapter(newImageAdapter,false);
      }
    }

    private void createImageGallery(){
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mGalleryFolder = new File(storageDirectory,GALLERY_LOCATION);
        if(!mGalleryFolder.exists()){
            mGalleryFolder.mkdirs();
        }
    }

    //creat a file
    File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE"+timeStamp+"_";
        //File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName,"jpg",mGalleryFolder);
        mImageFileLocation = image.getAbsolutePath();
        return image;

    }

    void setReducedImageSize(){
        int targetImageViewWidth = mPhotoCaptureImageView.getWidth();
        int targeImageViewHeight = mPhotoCaptureImageView.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageFileLocation,bmOptions);
        int cameraImageWidth = bmOptions.outWidth;
        int cameraImageHeight = bmOptions.outHeight;

        int scaleFactor = Math.min(cameraImageWidth/targetImageViewWidth,cameraImageHeight/targeImageViewHeight);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;//?

        Bitmap photoReducedSizeBitmao = BitmapFactory.decodeFile(mImageFileLocation,bmOptions);
        mPhotoCaptureImageView.setImageBitmap(photoReducedSizeBitmao);


    }
}
