package com.mingzheng.lwzzr.model

import com.mingzheng.common.request.RetrofitServiceManager

class AppRetrofitManager: RetrofitServiceManager() {
    override fun getBaseUrl(): String {
        return "https://api.lwzzr.com/";
    }
}