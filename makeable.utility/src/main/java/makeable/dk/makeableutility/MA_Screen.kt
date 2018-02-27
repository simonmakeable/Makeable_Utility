package makeable.dk.makeableutility

import android.content.res.Resources

/**
 * Created by simonchristensen on 27/02/2018.
 */
class MA_Screen(){
    fun getScreenWidth(): Int {
        return Resources.getSystem().getDisplayMetrics().widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().getDisplayMetrics().heightPixels
    }
}