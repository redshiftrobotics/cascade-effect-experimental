/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ColorPicker extends LinearOpMode {
    static final int PICK_CONTACT_REQUEST = 1;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Camera mCamera;
    private CameraPreview mPreview;
    Context context = hardwareMap.appContext;

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();
// Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        //mPreview = new CameraPreview(context, mCamera);
        //FrameLayout pre = FtcRobotControllerActivity.preview;
        //pre.addView(mPreview);

        //mCamera.takePicture(null, null, mPicture);
        releaseCamera();


    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //camera.startPreview();
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



            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {

                        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                        findAvgSides();
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




            } catch (FileNotFoundException e) {
                Log.d("FileNotFounc", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("IOException", "Error accessing file: " + e.getMessage());
            }



        }
    };


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
            Log.d("ayy", "lmao");
        }
        return c; // returns null if camera is unavailable
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

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
                int ycord = (bitproc.getHeight()/8)*((2*j) + 1);
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

    static final int REQUEST_IMAGE_CAPTURE = 1;



}
