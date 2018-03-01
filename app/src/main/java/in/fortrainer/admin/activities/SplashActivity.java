package in.fortrainer.admin.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.fortrainer.admin.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivityTag";
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.activity_splash)
    RelativeLayout activitySplash;
    @BindView(R.id.ll_splash_holder)
    LinearLayout llSplashHolder;
    @BindView(R.id.rl_progress_holder)
    RelativeLayout rlProgressHolder;
    @BindView(R.id.img_retry)
    ImageView imgRetry;

    /**
     * ButterKnife Code
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        AdminHelper.hideStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
            Log.d(TAG, "onResume: No ssb account available");
            startTimer();
    }

    private void startTimer() {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            public void onFinish() {
                launchNextActivity();
            }
        }.start();
    }

    /**
     * base on session continuity the next activity will be decided and
     * launches next activity
     */
    private void launchNextActivity() {
        goToLoginScreen();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void goToLoginScreen() {
        Intent loginIntent = new Intent(this, HomeActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
