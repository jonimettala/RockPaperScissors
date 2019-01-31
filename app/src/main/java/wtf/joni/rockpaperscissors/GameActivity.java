package wtf.joni.rockpaperscissors;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private ImageView player;
    private int playerImg;
    private int playerScore;
    private ImageView computer;
    private int computerImg;
    private int computerScore;
    private SoundPool soundPool;
    boolean loaded = false;
    private int soundBaby;
    private int soundSlapping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        player = (ImageView) findViewById(R.id.player_weapon);
        computer = (ImageView) findViewById(R.id.computer_weapon);
        playerScore = 0;
        computerScore = 0;
        handleDialog();

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });
        soundBaby = soundPool.load(this, R.raw.baby, 1);
        soundSlapping = soundPool.load(this, R.raw.slapping, 1);
    }

    public void playSound(int soundID) {
        if (loaded) {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            float actualVolume = (float) audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = (float) audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = actualVolume / maxVolume;
            soundPool.play(soundID, volume, volume, 1, 0, 1f);
        }
    }

    public void handleTurn(int playerWeapon) {
        Animation animLeft = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        Animation animRight = AnimationUtils.loadAnimation(this, R.anim.translate_right);
        setImage(player, playerWeapon);
        setImage(computer, generateRandomNumber());
        player.startAnimation(animRight);
        computer.startAnimation(animLeft);
        isGameWon();
    }

    public void handleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your weapon");
        String[] weapons = {"Rock", "Paper", "Scissors"};
        builder.setItems(weapons, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        handleTurn(1);
                        break;
                    case 1:
                        handleTurn(2);
                        break;
                    case 2:
                        handleTurn(3);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean isGameWon() {
        if (playerImg == 1 && computerImg == 3) {
            // rock wins
            handleWin(player);
            return true;
        } else if (playerImg == 2 && computerImg == 1) {
            // paper wins
            handleWin(player);
            return true;
        } else if (playerImg == 3 && computerImg == 2) {
            // scissors win
            handleWin(player);
            return true;
        } else if (playerImg == 3 && computerImg == 1) {
            // rock wins
            handleWin(computer);
            return true;
        } else if (playerImg == 1 && computerImg == 2) {
            // paper wins
            handleWin(computer);
            return true;
        } else if (playerImg == 2 && computerImg == 3) {
            // scissors win
            handleWin(computer);
            return true;
        }
        setMiddleText("Tie!");
        return false;
    }

    public void handleWin(ImageView winner) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
        if (winner == player) {
            playSound(soundSlapping);
            setMiddleText("You won!");
            playerScore++;
            ((TextView) findViewById(R.id.player_score)).setText(playerScore + " - Player");
            ((TextView) findViewById(R.id.game_center_text)).startAnimation(anim);
        } else {
            playSound(soundBaby);
            setMiddleText("You lost!");
            computerScore++;
            ((TextView) findViewById(R.id.computer_score)).setText("Computer - " + computerScore);
        }
    }

    public void setMiddleText(String text) {
        ((TextView) findViewById(R.id.game_center_text)).setText(text);
    }

    public int generateRandomNumber() {
        return 1 + (int)(Math.random() * ((3 - 1) + 1));
    }

    public void setImage(ImageView iv, int img) {
        if (iv == player) {
            playerImg = img;
        } else {
            computerImg = img;
        }
        switch(img) {
            case 1:
                iv.setImageResource(R.drawable.rock);
                break;
            case 2:
                iv.setImageResource(R.drawable.paper);
                break;
            case 3:
                iv.setImageResource(R.drawable.scissors);
                break;
        }
    }

    public void clicked(View view) {
        handleDialog();
    }
}
