package com.example.shoppy.retrofit;

import com.example.shoppy.model.LoaiSpModel;
import com.example.shoppy.model.SanPhamMoiModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getloaisp.php")
//    Observable<LoaiSpModel> getLoaiSp();
    Observable<LoaiSpModel> getLoaiSp();
    //STEP 6:
    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();
}
