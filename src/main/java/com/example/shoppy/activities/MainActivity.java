package com.example.shoppy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.shoppy.R;
import com.example.shoppy.adapter.LoaiSpAdapter;
import com.example.shoppy.model.LoaiSp;
import com.example.shoppy.model.LoaiSpModel;
import com.example.shoppy.retrofit.ApiBanHang;
import com.example.shoppy.retrofit.RetrofitClient;
import com.example.shoppy.utils.Ultils;
import com.facebook.appevents.ml.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar tbMain;
    ViewFlipper vfMain;
    RecyclerView rcSanPham;
    NavigationView nvMain;
    ListView lvMain;
    DrawerLayout drawerLayoutMain;
    //STEP 2:
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangLoaiSps;
    //STEP 3:
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
//    LoaiSpModel loaiSpModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //canhdao2001@gmail.com
        //STEP 1: SỬA MAINACTIVITY, GIAO DIỆN MAIN CHẠY QUẢNG CÁO, 2 FILE ANIMATION, THÊM THƯ VIỆN GLIDER, MINSDK -> 25
        //STEP 2: TẠO ADAPTER CHO LISTVIEW CHỌN LOẠI SP: Sửa MainActivity, tạo 1 ADAPTER, 1 MODEL, 1 icon per_media
        //STEP 3: KẾT NỐI SERVER LẤY DATA:Cấp quyền, hàm ktra kết nối INTERNET, tạo retrofit,tạo class LoaiSpModel, tạo Util lấy link => lỗi api cần chọn rxjava
        //STEP 4: ĐƯA DATA VÀO LISTVIEW LOẠI SP: sửa thông báo, tạo hàm onDestroy, getLoaiSanPham, lỗi lặp data LoaiSpAdapter, giao diện item_sp: kích thước, sửa data server
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //STEP 3:
        apiBanHang = RetrofitClient.getInstance(Ultils.BASE_URL).create(ApiBanHang.class);

        //STEP 1:
        anhXa();
        actionBar();
//        actionViewFlipper();//add QC cho viewFlipp

        //STEP 3:
        if (isConnected(this)) {
//            Toast.makeText(getApplicationContext(),"ok ket noi",Toast.LENGTH_LONG).show();
            actionViewFlipper();//add QC cho viewFlipp
            //Hàm kết nối file php lấy tên loại sp
            getLoaiSanPham();
        } else {
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối!!!", Toast.LENGTH_LONG).show();
        }
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess()) {
//                                Toast.makeText(getApplicationContext(),loaiSpModel.getResult().get(0).getTensanpham(),Toast.LENGTH_LONG).show();
                                //STEP 4
                                //Thêm data cho list view
                                mangLoaiSps = loaiSpModel.getResult();//nối data từ loại sp model vào mangLoaiSp
                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangLoaiSps);
                                lvMain.setAdapter(loaiSpAdapter);
                            }
                        }

                ));
    }

    private void actionViewFlipper() {
        //STEP 1:
        List<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mangQuangCao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mangQuangCao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        //Dùng thư viện glider đưa hình ảnh vào imageView
        for (int i = 0; i < mangQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            vfMain.addView(imageView);
        }
        vfMain.setFlipInterval(3000);
        vfMain.setAutoStart(true);
        //set Animation cho viewFlipp
        Animation slideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        vfMain.setInAnimation(slideIn);
        vfMain.setOutAnimation(slideOut);

    }

    private void actionBar() {
        //STEP 1:
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
        //STEP 1:
        tbMain = findViewById(R.id.tbMain);
        vfMain = findViewById(R.id.vfMain);
        rcSanPham = findViewById(R.id.rcSanPham);
        nvMain = findViewById(R.id.nvMain);
        lvMain = findViewById(R.id.lvMain);
        drawerLayoutMain = findViewById(R.id.drawerLayoutMain);

        //STEP 2:
        //Khởi tạo list
        mangLoaiSps = new ArrayList<>();
//        //Khởi tạo Adapter
//        loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangLoaiSps);
//        lvMain.setAdapter(loaiSpAdapter);
    }

    //STEP 3:
    //Tạo hàm kiểm tra kết nối internet
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}