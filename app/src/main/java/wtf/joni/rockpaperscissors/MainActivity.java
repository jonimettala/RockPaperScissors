package wtf.joni.rockpaperscissors;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = (TextView) findViewById(R.id.rps_title);
        ObjectAnimator titleFadeAnimation = ObjectAnimator.ofFloat(title, "alpha", 0);
        ObjectAnimator titleShowAnimation = ObjectAnimator.ofFloat(title, "alpha", 1);
        titleFadeAnimation.setDuration(500);
        titleShowAnimation.setDuration(900);

        AnimatorSet titleFade = new AnimatorSet();
        titleFade.play(titleFadeAnimation).before(titleShowAnimation);

        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Button startGame = (Button) findViewById(R.id.start_game);
        Button highScore = (Button) findViewById(R.id.high_score);

        startGame.startAnimation(rotate);
        highScore.startAnimation(rotate);
        titleFade.start();
    }

    public void clicked(View view) {
        switch (view.getId()) {
            case R.id.start_game:
                Intent i = new Intent(this, GameActivity.class);
                startActivity(i);
                break;
            case R.id.high_score:
                break;
        }
    }
}
