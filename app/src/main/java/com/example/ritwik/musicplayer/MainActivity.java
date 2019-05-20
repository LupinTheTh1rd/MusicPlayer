package com.example.ritwik.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

//import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    SeekBar song_bar;
    //SeekBar vol_bar;
    TextView time_done,time_left;
    Button pause,next,prev;
    MediaPlayer mp;
    int total_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time_done = findViewById(R.id.start_timer);
        time_left = findViewById(R.id.end_timer);
        song_bar = findViewById(R.id.seekBar);
        pause = findViewById(R.id.pause_btn);
        next = findViewById(R.id.next_btn);
        prev = findViewById(R.id.previous_btn);

        next.setOnClickListener(this);
        //prev.setOnClickListener(this);    //for first song; no previous song

        //For music playback
        mp = MediaPlayer.create(this,R.raw.hysteria);
        mp.setLooping(true);        //restarts the song once it ends [literally runs a loop]
        mp.seekTo(0);          //when activity starts; sets position to 0sec
        mp.setVolume(0.5f, 0.5f);       //set volume for right and left output
        total_time = mp.getDuration();      //gets the length of song; and store for reference

        //For the seekbar [song control]
        song_bar.setMax(total_time);
        song_bar.setOnSeekBarChangeListener(this);

        // Stuff for displaying elapsed time and remaining time for the playback
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int current_position = msg.what;
            song_bar.setProgress(current_position);

            //Update time data as seekbar changed
            String time_elapsed = timeLabel(current_position);
            time_done.setText(time_elapsed);
            time_left.setText("-" + timeLabel((total_time - current_position)));
        }
    };

    private String timeLabel(int current_position) {
        String time="";
        int min = current_position / 1000 /60;
            //current_position is basically the time of song in milliseconds
            //so, dividing it by 1000 gives in seconds;
            //further dividing this by 60; gives the minutes
        int sec = current_position / 1000 % 60;
            //again; dividing by 1000 gives seconds
            //further; remainder of division by 60 is used to determine if 60 secs have completed; and it should be counted as minute or not
        time = min+":";     //so format(uptil now) is like 2:..
        if(sec < 10) {
            time += "0";    //if seconds under 10; need to display like "2:0.."; otherwise "2:.."
        }
        time += sec;        //if not under 10; simply concatenate to format for: "2:26"
        return time;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.pause_btn: {
                if (!mp.isPlaying()) {
                    //Pause
                    mp.start();
                    pause.setBackgroundResource(R.drawable.pause_btn);
                } else {
                    //Play
                    mp.pause();
                    pause.setBackgroundResource(R.drawable.play_btn);
                }
                break;
            }
            case R.id.next_btn: {
                Intent intent2 = new Intent(this,Main2Activity.class);
                startActivity(intent2);
                break;
            }
            case R.id.previous_btn: {
                break;
            }
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean user_input) {
        if(user_input) {
            mp.seekTo(progress);                //seeks the music playback to requested position
            song_bar.setProgress(progress);     //moves the seekbar cursor to position
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
