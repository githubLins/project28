package com.example.cathleen.mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "myTag";
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        //播放完停止
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.v(TAG,"播放完成即停止");
            }
        });
        final Button startResoure = (Button)findViewById(R.id.startResoure);
        final Button startPhone = (Button)findViewById(R.id.startPhone);
        final Button startNet = (Button)findViewById(R.id.startNet);
        final Button pause = (Button)findViewById(R.id.pause);
        final Button stop = (Button)findViewById(R.id.stop);
        final Button loop = (Button)findViewById(R.id.loop);
        final TextView showLoop = (TextView)findViewById(R.id.showLoop);

        loop.setEnabled(false);
        pause.setEnabled(false);
        stop.setEnabled(false);

        //开始播放资源文件
        startResoure.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 try {
                     Log.v(TAG, "startSourse");
                     MediaPlayer mp=new MediaPlayer();
                     mediaPlayer.reset();

                     AssetManager assetManager = getAssets();
                     AssetFileDescriptor assetFileDescriptor =
                             assetManager.openFd("test1.mp3");
                     mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                             assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                     mediaPlayer.prepare();
                     mediaPlayer.start();
                     pause.setEnabled(true);
                     stop.setEnabled(true);
                     loop.setEnabled(true);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         });
        //开始播放外部文件
        startPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "startPhone");
                MediaPlayer mp=new MediaPlayer();
                mediaPlayer.reset();
                try {
                    mp.setDataSource("/手机存储/You Are My Everything.mp3");
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v(TAG,"not found");
                }
                mp.start();

            }
        });
        //开始播放网络文件
        startNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "startNet");
                MediaPlayer mp=new MediaPlayer();
                Uri uri=Uri.parse("http://wap.sogou.com/web/uID=MrvN8OCrZMHMqeFH/v=5/type=1/sp=1/ct=160906212023/keyword=%E9%9F%B3%E4%B9%90/id=3dd9c162-6312-40ac-8d2e-49790768f8cd/sec=CXokz8Wp0LP_4px_3O5MUw../dp=1/vr=70066702/k=%E9%9F%B3%E4%B9%90/tc?dp=1&key=%E9%9F%B3%E4%B9%90&pno=1&g_ut=3&&bid=sogou-mobo-412604be30f701b1is_per=0&clk=1&url=http%3A%2F%2Fi.y.qq.com%2Fv8%2Fplaysong.html%3Fsongmid%3D000HvI6m0sWQoG&vrid=70066702&wml=1&linkid=tagmusic1");
                try {
                    mp.setDataSource(MainActivity.this,uri);
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v(TAG,"没找到");
                }
            }
        });


        //暂停播放
         pause.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (mediaPlayer.isPlaying()) {
                     pause.setText("Play");
                     mediaPlayer.pause();
                 } else {
                     pause.setText("Pause");
                     mediaPlayer.start();
                 }
             }
         });
        //停止播放
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
            }
        });
        //循环播放
        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Looping");
                boolean loop = mediaPlayer.isLooping();
                mediaPlayer.setLooping(!loop);
                if (!loop)
                    showLoop.setText("循环播放");
                else
                    showLoop.setText("一次播放");
            }
        });




    }

}
