package com.mingzheng.lwzzr.model.db

import com.mingzheng.common.utils.sp.SPManager

class DataManager:SPManager {

    companion object {
        val LOGIN_INFO = "login_info"
        val instance: DataManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DataManager()
        }
    }

    constructor() {

    }
}