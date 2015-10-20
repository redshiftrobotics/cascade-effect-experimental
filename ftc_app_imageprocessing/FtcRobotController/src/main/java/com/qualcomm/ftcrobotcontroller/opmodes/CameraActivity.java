package com.qualcomm.ftcrobotcontroller.opmodes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qualcomm.ftcrobotcontroller.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CameraActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Create an instance of Camera
//        mCamera = getCameraInstance();
//
//        // Create our Preview view and set it as the content of our activity.
//        mPreview = new CameraPreview(this, mCamera);
//        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
//        preview.addView(mPreview);
//        mCamera.takePicture(null, null, mPicture);
//


    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            camera.startPreview();
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
//                Log.d(TAG, "Error creating media file, check storage permissions: " +
//                        e.getMessage());
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                //findAvgSides();


                
//              Thread thread = new Thread() {
//                    @Override
//                    public void run() {
//                        try {
//
//                            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
//                            findAvgSides();
//                            boolean lol = false;
//                            if(lol) {
//                                throw new InterruptedException("Nothing really errored, the try catch block just needed an exception... So here it is.");
//                            }
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                thread.start();




            } catch (FileNotFoundException e) {
                Log.d("FileNotFounc", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("IOException", "Error accessing file: " + e.getMessage());
            }



        }
    };

    public void findAvgSides() {

        Bitmap bitproc = BitmapFactory.decodeFile("/sdcard/Pictures/processing/proc.jpg");
        //1/6
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {

                            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                            //findAvgSides();
                            boolean lol = false;
                            if(lol) {
                                throw new InterruptedException("Nothing really errored, the try catch block just needed an exception... So here it is.");
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
        int readArray[];
            readArray= new int[100];
            int arrayLoadNumber = -1;
            for (int i = 0; i < 4; i++) {
                int xcord = (bitproc.getWidth()/8)*((2*i) + 1);
                for (int j = 0; j < 4; j++) {
                    int ycord = (bitproc.getHeight()/8)*((2*j)+1);
                    arrayLoadNumber++;
                    //Log.d("someshit", String.valueOf(arrayLoadNumber));
                    readArray[arrayLoadNumber] = Color.blue(bitproc.getPixel(xcord, ycord));
                }
        }


        int leftsum = 0;
        int rightsum = 0;
        for (int i = 0; i <= 7; i++) {
            leftsum += readArray[i];
        }
        for (int i = 8; i <= 15; i++) {
            //Log.d("readArrayval", String.valueOf(readArray[i]));
            rightsum += readArray[i];
        }
        //Log.d("rightsum", String.valueOf(rightsum));
        int leftavg = leftsum/8;
        int rightavg = rightsum/8;



        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;


        if (leftavg > rightavg){

            String text = "Blue is on the Left";
            Log.d("bitproc", text);
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();

        } else if (leftavg < rightavg){
            String text = "Blue is on the Right";
            Log.d("bitproc", text);
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
        } else {
            String text = "Something broke";
            Log.d("bitproc", text);
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }


    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "processing");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("processing", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "proc.jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

}

