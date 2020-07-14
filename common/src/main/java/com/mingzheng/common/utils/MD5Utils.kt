package com.mingzheng.common.utils

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MD5Utils {
    companion object{
        fun md5(plainText: String): String {
            // 返回字符串
            var md5Str = ""
            try {
                // 操作字符串
                val buf = StringBuffer()
                val md = MessageDigest.getInstance("MD5")
                // 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
                md.update(plainText.toByteArray())
                // 计算出摘要,完成哈希计算。
                val b = md.digest()
                var i: Int
                for (offset in b.indices) {
                    i = b[offset].toInt()
                    if (i < 0) {
                        i += 256
                    }
                    if (i < 16) {
                        buf.append("0")
                    }
                    // 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
                    buf.append(Integer.toHexString(i))
                }
                // 32位的加密
                md5Str = buf.toString()
                // 16位的加密
                // md5Str = buf.toString().md5Strstring(8,24);
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return md5Str
        }

        @Synchronized
        fun Md5(args: String): String {
            var strBuf: StringBuffer? = null
            try {
                val md5 = MessageDigest.getInstance("MD5")
                md5.update(args.toByteArray(charset("UTF-8")))
                val encryption = md5.digest()
                strBuf = StringBuffer()
                for (i in encryption.indices) {
                    if (Integer.toHexString(0xff and encryption[i].toInt()).length == 1) {
                        strBuf.append("0").append(Integer.toHexString(0xff and encryption[i].toInt()))
                    } else {
                        strBuf.append(Integer.toHexString(0xff and encryption[i].toInt()))
                    }
                }
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return strBuf!!.toString()
        }
    }
}