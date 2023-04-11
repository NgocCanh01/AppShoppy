package com.example.shoppy.retrofit;

import com.example.shoppy.model.LoaiSpModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getloaisp.php")
//    Observable<LoaiSpModel> getLoaiSp();
    Observable<LoaiSpModel> getLoaiSp();
}
