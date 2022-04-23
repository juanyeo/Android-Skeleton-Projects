package com.example.camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageView image1;
    VideoView video1;

    String dir_path;
    Uri contentUri;
    String [] permission_list = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image1 = (ImageView) findViewById(R.id.imageView);
        video1 = (VideoView) findViewById(R.id.videoView);

        requestPermissionsByVersion();
    }

    public void requestPermissionsByVersion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0);
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int a1 : grantResults) {
            if(a1 == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        init();
    }

    public void init() {
        // 내장 메모리 주소 + /Android/data/ + 패키지 명
        String exdir_path = Environment.getExternalStorageDirectory().getAbsolutePath();
        dir_path = exdir_path + "/Android/data/" + getPackageName();
        File file = new File(dir_path);
        if(file.exists() == false) {
            file.mkdir();
        }
    }

    public void captureImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    public void saveOriginalImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String file_name = "/temp_" + System.currentTimeMillis() + ".jpg";
        String pic_path = dir_path + file_name;
        File file = new File(pic_path);

        // N 버전부터 Uri를 다른 앱으로 바로 넘기는게 차단됨, ContentProvider를 통해 넘겨야 함.
        // res > xml > file_path.xml 파일과 Manifest.xml > Provider 세팅이 필요
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(this, "com.example.camera.file_provider", file);
        } else {
            contentUri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, 2);
    }

    public void getImageFromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 3);
    }

    public void takeVideo(View view) {
        Intent intent= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        String fileName = "/video_" + System.currentTimeMillis() + ".mp4";
        String videoPath = dir_path + fileName;
        File file = new File(videoPath);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(this, "com.example.camera.file_provider", file);
        } else {
            contentUri = Uri.fromFile(file);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, 4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case 1:
                    Bitmap bitmap1 = (Bitmap) data.getParcelableExtra("data");
                    image1.setImageBitmap(bitmap1);
                    break;
                case 2:
                    Bitmap bitmap2 = BitmapFactory.decodeFile(contentUri.getPath());

                    Bitmap bitmap3 = resizeBitmap(1024, bitmap2);
                    float degree = getDegree();
                    Bitmap bitmap4 = rotateBitmap(bitmap3, degree);

                    image1.setImageBitmap(bitmap4);
                    break;
                case 3:
                    // Uri 객체와 Content Resolver를 통해 이미지의 정보를 가져온다
                    Uri uri = data.getData();
                    ContentResolver resolver = getContentResolver();
                    Cursor cursor = resolver.query(uri, null, null, null, null);
                    cursor.moveToNext();

                    // 이미지의 정보 중 이미지의 경로를 추출한다
                    int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    String source = cursor.getString(index);

                    // 이미지 경로로 이미지 객체를 생성한다
                    Bitmap bitmap5 = BitmapFactory.decodeFile(source);

                    Bitmap bitmap6 = resizeBitmap(1024, bitmap5);
                    float degree2 = getDegree2(source);
                    Bitmap bitmap7 = rotateBitmap(bitmap6, degree2);

                    image1.setImageBitmap(bitmap7);
                    break;
                case 4:
                    video1.setVideoURI(contentUri);
                    video1.start();
            }
        }
    }

    public Bitmap resizeBitmap(int targetWidth, Bitmap bitmap) {
        double ratio = (double) targetWidth / (double) bitmap.getWidth();
        int targetHeight = (int)(bitmap.getHeight() * ratio);
        Bitmap result = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
        if(result != bitmap) {
            // 원본 이미지 객체를 소멸시킨다
            bitmap.recycle();
        }
        return result;
    }

    public float getDegree() {
        try{
            // 이미지 파일에 저장된 정보를 가져온다
            ExifInterface exif = new ExifInterface(contentUri.getPath());
            int degree = 0;
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            switch(ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            return (float) degree;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public float getDegree2(String source) {
        try{
            // 이미지 파일에 저장된 정보를 가져온다
            ExifInterface exif = new ExifInterface(source);
            int degree = 0;
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            switch(ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            return (float) degree;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Bitmap rotateBitmap(Bitmap bitmap, float degree) {
        try{
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Matrix matrix = new Matrix();
            matrix.postRotate(degree);

            Bitmap result = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
            bitmap.recycle();
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}