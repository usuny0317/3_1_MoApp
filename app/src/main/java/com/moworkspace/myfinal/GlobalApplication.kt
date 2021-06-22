package com.moworkspace.myfinal

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        //카카오 sdk 초기화
        KakaoSdk.init(this, "86e11642d47ea3426e3f253d6c8210f5")
    }
}