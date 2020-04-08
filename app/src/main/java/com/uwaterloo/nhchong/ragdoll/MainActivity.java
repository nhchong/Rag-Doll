package com.uwaterloo.nhchong.ragdoll;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.PopupWindow;
import android.view.View;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private Activity mActivity;
    private Context mContext;

    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;

    DrawingView dView;

    int currPose = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inflate the view
        // demonstrates how this could be dynamic
        ViewGroup view_group = (ViewGroup) findViewById(R.id.main_region);
        dView = new DrawingView(this.getBaseContext());
        view_group.addView(dView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.reset:
                // do your code
                dView.reset(currPose);
                return true;
            case R.id.normal:
                // do your code
                dView.reset(1);
                currPose = 1;
                return true;
            case R.id.dog:
                // do your code
                dView.reset(2);
                currPose = 2;
                return true;
            case R.id.tree:
                // do your code
                dView.reset(3);
                currPose = 3;
                return true;
            case R.id.about:
                // do your code
                showPopup();
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPopup() {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(dView, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}