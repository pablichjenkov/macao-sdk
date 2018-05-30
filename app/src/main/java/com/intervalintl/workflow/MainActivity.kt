package com.intervalintl.workflow

import android.os.Bundle


class MainActivity : WorkFlowActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onProvideRootFlow(): Flow<*, *> {
        return LoginFlow("LoginFlow")
    }
}
