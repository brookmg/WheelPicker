package com.itimetraveler.widget.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itimetraveler.widget.demo.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import io.itimetraveler.widget.picker.TextSingleWheelPicker;
import io.itimetraveler.widget.picker.WheelPicker;
import io.itimetraveler.widget.pickerselector.ChineseCityWheelPicker;
import io.itimetraveler.widget.pickerselector.CountDownWheelPicker;
import io.itimetraveler.widget.pickerselector.CountryWheelPicker;
import io.itimetraveler.widget.pickerselector.DateWheelPicker;
import io.itimetraveler.widget.pickerselector.TimeWheelPicker;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;

    private String s1, s2, s3, s4;

    private TextSingleWheelPicker mTextSingleWheelPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s1 = s2 = s3 = s4 = "";
        mTextView = (TextView) findViewById(R.id.hello_world);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UsageActivity.class));
            }
        });

        // 城市选择器
        findViewById(R.id.chinese_city_wheelpicker_button)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChineseCityWheelPicker chineseCityWheelPicker = new ChineseCityWheelPicker(MainActivity.this);
                showDialog("ChineseCityWheelPicker\n(城市选择器)", chineseCityWheelPicker);
            }
        });

        findViewById(R.id.country_wheelpicker_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog("CountryWheelPicker\n(国家选择器)", new CountryWheelPicker(MainActivity.this));
                    }
                });

        findViewById(R.id.time_wheelpicker_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimeWheelPicker picker = new TimeWheelPicker(MainActivity.this);
                        picker.setOnTimeChangedListener(new TimeWheelPicker.OnTimeChangedListener() {
                            @Override
                            public void onTimeChanged(TimeWheelPicker view, Calendar date) {
                                Toast.makeText(MainActivity.this, TimeWheelPicker.DEFAULT_TIME_FORMAT.format(date),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        showDialog("TimeWheelPicker\n(时间选择器)", picker);
                    }
                });

        // 日期选择器
        findViewById(R.id.date_wheelpicker_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DateWheelPicker picker = new DateWheelPicker(MainActivity.this);
                        picker.setOnDateChangedListener(new DateWheelPicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DateWheelPicker view, int year, int monthOfYear, int dayOfMonth) {
                                Toast.makeText(MainActivity.this,
                                        year + "/" + monthOfYear + "/" + dayOfMonth,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        showDialog("DateWheelPicker\n(日期选择器)", picker);
                    }
                });

        findViewById(R.id.digital_wheelpicker_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog("CountDownWheelPicker\n(倒计时器)", new CountDownWheelPicker(MainActivity.this));
                    }
                });


        final List<String> mList = Arrays.asList("铜仁市",
                "德江县",
                "江口县",
                "思南县",
                "石阡县",
                "玉屏侗族自治县",
                "松桃苗族自治县",
                "印江土家族苗族自治县",
                "沿河土家族自治县",
                "万山特区",
                "其他");

        mTextSingleWheelPicker = (TextSingleWheelPicker) findViewById(R.id.text_wheel_picker);
        mTextSingleWheelPicker.setTextList(mList);
        mTextSingleWheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker parentView, int[] index) {
                s1 = "" + mList.get(index[0]);
                updateTextView();
            }
        });
//        mTextSingleWheelPicker.setSelection(10);
        mTextSingleWheelPicker.setTheme(TextSingleWheelPicker.Theme.white);
    }


    private void showDialog(String title, View v) {
        DialogUtil.showDialog(MainActivity.this, title, v);
    }


    private List<String> generateDateList(int daysCount){
        Format dateFormat = new SimpleDateFormat("MM月dd日");
        Format weekFormat = new SimpleDateFormat("E");
        Date today = new Date();

        final int todayIdx = daysCount;
        String[] arr = new String[daysCount * 2 + 1];
        for(int i = daysCount; i > 0; i--){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DAY_OF_MONTH, -i);   // 今天-1天
            arr[todayIdx - i] = dateFormat.format(c.getTime()) + " " + weekFormat.format(c.getTime());

            Calendar c1 = Calendar.getInstance();
            c1.setTime(new Date());
            c1.add(Calendar.DAY_OF_MONTH, i);   // 今天+1天
            arr[todayIdx + i] = dateFormat.format(c1.getTime()) + " " + weekFormat.format(c1.getTime());
        }
        arr[todayIdx] = dateFormat.format(today) + " 今天";

        return Arrays.asList(arr);
    }

    private void updateTextView(){
        mTextView.setText(s1 + "  |  " + s2 + "  |  " + s3 + "  |  " + s4);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TimeUtils.getInstance().setEndTime("onResume");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        TimeUtils.getInstance().setEndTime("onWindowFocusChanged > hasFocus:" + hasFocus);
    }

}
