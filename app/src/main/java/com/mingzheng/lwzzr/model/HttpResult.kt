package com.mingzheng.lwzzr.model

import java.io.Serializable

class HttpResult<DATA:Serializable>{
    var code:Int?=null
    var message:String?=null
    var data:DATA?=null


}