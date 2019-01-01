package com.dengzq.demo

import android.app.Application
import com.codemonkeylabs.fpslibrary.TinyDancer



/**
 * <p>author    dengzq</P>
 * <p>date      2018/12/28 16:37</p>
 * <p>package   com.dengzq.demo</p>
 * <p>readMe    MyApplication</p>
 */
class MyApplication:Application(){


    override fun onCreate() {
        super.onCreate()
        TinyDancer.create()
                .show(this)

        //alternatively
        TinyDancer.create()
                .redFlagPercentage(.1f) // set red indicator for 10%....different from default
                .startingXPosition(200)
                .startingYPosition(600)
                .show(this)

        //you can add a callback to get frame times and the calculated
        //number of dropped frames within that window
        TinyDancer.create()
                .addFrameDataCallback { previousFrameNS, currentFrameNS, droppedFrames ->
                    //collect your stats here
                }
                .show(this)
    }

}