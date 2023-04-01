package com.example.shoppy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.shoppy.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar tbMain;
    ViewFlipper vfMain;
    RecyclerView rcSanPham;
    NavigationView nvMain;
    ListView lvMain;
    DrawerLayout drawerLayoutMain;
    Button button;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();//V1
        actionBar();//V2
        actionViewFlipper();//add QC cho viewFlipp
        //Up vào dev_Canh
        //main up dev_canh


    }

    private void actionViewFlipper() {
        List<String> mangQuangCao= new ArrayList<>();
        mangQuangCao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mangQuangCao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mangQuangCao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        //Dùng thư viện glider đưa hình ảnh vào imageView
        for(int i=0; i<mangQuangCao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            vfMain.addView(imageView);
        }
        vfMain.setFlipInterval(3000);
        vfMain.setAutoStart(true);
        //set Animation cho viewFlipp
        Animation slideIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slideOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        vfMain.setInAnimation(slideIn);
        vfMain.setOutAnimation(slideOut);
    }

    private void actionBar() {
        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbMain.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        tbMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutMain.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhXa() {
        tbMain = findViewById(R.id.tbMain);
        vfMain = findViewById(R.id.vfMain);
        rcSanPham = findViewById(R.id.rcSanPham);
        nvMain = findViewById(R.id.nvMain);
        lvMain = findViewById(R.id.lvMain);
        drawerLayoutMain = findViewById(R.id.drawerLayoutMain);
    }
}