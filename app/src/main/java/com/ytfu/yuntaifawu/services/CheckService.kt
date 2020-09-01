package com.ytfu.yuntaifawu.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class CheckService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)

    }

}