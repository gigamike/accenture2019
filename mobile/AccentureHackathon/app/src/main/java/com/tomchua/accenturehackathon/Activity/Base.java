package com.tomchua.accenturehackathon.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.tomchua.accenturehackathon.R;

import java.lang.reflect.Field;

public class Base extends AppCompatActivity {



    // For Side Menu Contents
    private String[] mMenuTitles = {"Home","Categories"};
    private Integer[] mMenuIcons = {R.mipmap.ic_launcher_round,R.mipmap.ic_launcher};
    Context context = this;
    private Thread thread;

    public MaterialDialog.Builder mMaterialDialog;


    public ResideMenu initMenu(final Activity activity, final int toolbarId) {
        final ResideMenu resideMenu = new ResideMenu(activity);
        // setNac
        resideMenu.setBackground(ContextCompat.getDrawable(this,R.drawable.yellowbg));
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        resideMenu.attachToActivity(activity);

        for (int x = 0; x < mMenuTitles.length; x++) {
            ResideMenuItem menuItem = new ResideMenuItem(this, mMenuIcons[x], mMenuTitles[x]);
            try {
                Field privateTextView = ResideMenuItem.class.getDeclaredField("tv_title");
                privateTextView.setAccessible(true);
                TextView tv = (TextView) privateTextView.get(menuItem);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(15);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            final int index = x;
            menuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Class classTo = null;
                    if (index == 0) classTo = ShoppingActivity.class;
                    if (index == 1) classTo = ShoppingActivity.class;
//                    if (index == 1) classTo = Plants.class;
//                    if (index == 2) classTo = Diseases.class;
//                    if (index == 3) classTo = HowtoPlant.class;
//                    if (index == 4) classTo = AboutUs.class;
//                    if (index == 3) classTo = Task.class;
//                    if (index == 4) classTo = SendReport.class;
//                    if (index == 8) logoutUser();

                    if (classTo != null) {
                        if (!classTo.getName().equals(activity.getClass().getName())) {
                            if (!activity.getClass().equals(ShoppingActivity.class)) finish();
                            startActivity(new Intent(v.getContext(), classTo));
                        }
                        else resideMenu.closeMenu();
                    }
                }
            });

            resideMenu.addMenuItem(menuItem, ResideMenu.DIRECTION_LEFT);
        }
        activity.findViewById(toolbarId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolbarId == R.id.content_hamburger)
                    resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//                else if (toolbarId == R.id.back) {
//                    activity.finish();
//                }
            }
        });

        return resideMenu;
    }


    private void startThread(Runnable run) {
        if (thread != null) {
            thread.interrupt();
        }
        thread = new Thread(run);
        thread.start();
    }
//
//
//
//    public SessionHelper getSessionHelper() {
//        return new SessionHelper(this);
//    }

    MaterialDialog loadingdialog;
    public void showLoadingDialog(Context context){
        loadingdialog =  new MaterialDialog.Builder(context)
                .title("Inserting  ")
                .content("please wait......")
                .progress(true, 0)
                .show();
    }

    public void dismissDialog(){
        loadingdialog.dismiss();
    }

    public String getApiUrl() {
        return getSharedPreferences("API_URL", Context.MODE_PRIVATE).getString("API_URL", "192.168.21.48:8080");
    }


    // local db
//    public DBHelper getLocalDB() {
//        return new DBHelper(this);
//    }
}
