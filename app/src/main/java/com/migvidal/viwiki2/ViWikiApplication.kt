package com.migvidal.viwiki2

import android.app.Application
import com.migvidal.viwiki2.data.database.ViWikiDatabase

class ViWikiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ViWikiDatabase.getInstance(this)//initialize database instance
    }
}