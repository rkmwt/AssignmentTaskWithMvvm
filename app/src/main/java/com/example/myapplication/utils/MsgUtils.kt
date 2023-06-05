package com.example.myapplication.utils

import android.content.Context
import android.widget.Toast

object MsgUtils {

    fun showToast(mContext: Context, msg: String){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show()
    }
}
