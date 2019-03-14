package exercicecours2.exeinformatique.com.exercicecours2;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    int maxVolume = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.farm);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SeekBar seekBarMedia = findViewById(R.id.seekBar_media);
                int progress = mediaPlayer.getCurrentPosition() * 100 / mediaPlayer.getDuration();
                seekBarMedia.setProgress(progress);
                handler.postDelayed(this, 250);
            }
        }, 250);
        setListener();
    }

    public void onMediaPlayerCompletion(){
        Toast.makeText(
                this,
                "\uD83C\uDF5E",
                Toast.LENGTH_SHORT
        ).show();
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }

    public void setListener(){
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMediaPlayer();
            }
        });
        findViewById(R.id.btn_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseMediaPlayer();
            }
        });
        setMediaListener();
        setSeekBarListener();
        setSeekBarVolumeListener();
    }

    private void setMediaListener() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onMediaPlayerCompletion();
            }
        });
    }

    private void setSeekBarVolumeListener() {
        SeekBar seekBarVolume = findViewById(R.id.seekBar_volume);
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    float log1=(float)(Math.log(maxVolume-progress)/Math.log(maxVolume));
                    mediaPlayer.setVolume(1-log1, 1-log1);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setSeekBarListener(){
        SeekBar seekBarMedia = findViewById(R.id.seekBar_media);
        seekBarMedia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    seekInMedia(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void seekInMedia(int progress) {
        mediaPlayer.seekTo(progress * mediaPlayer.getDuration() / 100);
    }

    private void playMediaPlayer(){
        mediaPlayer.start();
    }

    private void pauseMediaPlayer(){
        mediaPlayer.pause();
    }
}
