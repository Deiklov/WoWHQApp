package com.example.wowhqapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.presenters.WoWTokenPresenter;
import com.example.wowhqapp.repositories.SettingRepository;

public class WowTokenActivity extends AppCompatActivity implements MainContract.WoWTokenView, ViewSwitcher.ViewFactory {

    private CheckBox mCheckBox;
    private TextView mCurrentText;
    private TextView mMinText;
    private TextView mMaxText;
    private TextView mLastChangeText;
    private RadioButton mLowRadioButton;
    private RadioButton mHighRadioButton;
    private EditText mTargetPriceEditText;
    private ImageSwitcher mArrowSwitcher;
    private WoWTokenPresenter mWoWTokenPresenter;
    private final Integer[] mArrows = {R.drawable.ic_arrow_up_24, R.drawable.ic_arrow_down_24};


    private EditText mTestEditTExt;
    private Button mTestBtn;
    private Button mTestBtn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wow_token);

        mTestEditTExt = (EditText) findViewById(R.id.test_coef_et);
        mTestBtn = (Button) findViewById(R.id.test_btn);
        mTestBtn2 = (Button) findViewById(R.id.test_btn2);

        mTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWoWTokenPresenter.test_updateToken(Integer.parseInt(mTestEditTExt.getText().toString()));
                //mWoWTokenPresenter.init_price();
            }
        });
        mTestBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWoWTokenPresenter.init_price();
            }
        });

        mWoWTokenPresenter = new WoWTokenPresenter(new SettingRepository(getSharedPreferences(SettingRepository.APP_PREFERENCES, Context.MODE_PRIVATE)), this);

        mCheckBox = (CheckBox) findViewById(R.id.token_start);
        mCurrentText = (TextView) findViewById(R.id.token_current);
        mMaxText = (TextView) findViewById(R.id.token_max);
        mMinText = (TextView) findViewById(R.id.token_min);
        mLastChangeText = (TextView) findViewById(R.id.token_change);
        mArrowSwitcher = (ImageSwitcher) findViewById(R.id.token_image_switcher);
        mLowRadioButton = (RadioButton) findViewById(R.id.token_rbtn_low);
        mHighRadioButton = (RadioButton) findViewById(R.id.token_rbtn_high);
        mTargetPriceEditText = (EditText) findViewById(R.id.token_et_target_price);

        Animation inAnimation = new AlphaAnimation(0, 1);
        Animation outAnimation = new AlphaAnimation(1, 0);
        inAnimation.setDuration(1000);
        outAnimation.setDuration(1000);
        mArrowSwitcher.setFactory(this);
        mArrowSwitcher.setInAnimation(inAnimation);
        mArrowSwitcher.setOutAnimation(outAnimation);

        mWoWTokenPresenter.init_traget_price();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                //mWoWTokenPresenter.init_price();

            }
        }, 3000);

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                mWoWTokenPresenter.setServiceStatus(checkBox.isChecked());
                Log.v("REPO_TOKEN", "Обрабатываем нажатие на чекбокс");
            }
        });

        mLowRadioButton.setOnClickListener(radioButtonClickListener);
        mHighRadioButton.setOnClickListener(radioButtonClickListener);

        mTargetPriceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    Log.v("REPO_TOKEN", "Фокус изменился");
                    mWoWTokenPresenter.setTargetPrice(mHighRadioButton.isChecked(), Long.parseLong(mTargetPriceEditText.getText().toString()));
                }
            }
        });

        mTargetPriceEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                try {
                    mWoWTokenPresenter.setTargetPrice(mHighRadioButton.isChecked(), Long.parseLong(mTargetPriceEditText.getText().toString()));
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.token_input_error), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;
            switch (rb.getId()) {
                case R.id.token_rbtn_high:
                    try {
                        mWoWTokenPresenter.setTargetPrice(true, Long.parseLong(mTargetPriceEditText.getText().toString()));
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.token_input_error), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.token_rbtn_low:
                    try {
                        mWoWTokenPresenter.setTargetPrice(false, Long.parseLong(mTargetPriceEditText.getText().toString()));
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.token_input_error), Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("REPO_TOKEN", "destroy");
        mWoWTokenPresenter.destroy();
    }

    @Override
    public void setPrice(long min, long max, long current, long lastChange, int icon) {
        mMinText.setText(getResources().getString(R.string.token_min) + " " +Long.toString(min));
        mMaxText.setText(getResources().getString(R.string.token_max) + " " +Long.toString(max));
        mCurrentText.setText(getResources().getString(R.string.token_current) + " " +Long.toString(current));
        mLastChangeText.setText(getResources().getString(R.string.token_change) + " " + Long.toString(lastChange));
        mArrowSwitcher.setImageResource(mArrows[icon]);
    }

    @Override
    public void setBox(Boolean val) {
        mCheckBox.setChecked(val);
    }

    @Override
    public void setRadioBtn(Boolean val) {
        if (val){
            mHighRadioButton.setChecked(true);
        }
        else {
            mLowRadioButton.setChecked(true);
        }
    }

    @Override
    public void setTargetPriceEditText(long price) {
        mTargetPriceEditText.setText(String.valueOf(price));
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new
                ImageSwitcher.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return imageView;
    }
}
