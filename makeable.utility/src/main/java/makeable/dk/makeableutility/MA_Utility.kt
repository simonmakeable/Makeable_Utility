package makeable.dk.makeableutility

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import java.io.File
import java.util.*

/**
 * Created by simonchristensen on 27/02/2018.
 */
class MA_Utility {
    companion object {
        fun getScreenWidth(): Int {
            return Resources.getSystem().getDisplayMetrics().widthPixels
        }

        fun getScreenHeight(): Int {
            return Resources.getSystem().getDisplayMetrics().heightPixels
        }

        fun setColorForView(context: Context, imageview: ImageView, color: Int) {
            imageview.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN)
        }

        fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
            if (v.getLayoutParams() is ViewGroup.MarginLayoutParams) {
                val p = v.getLayoutParams() as ViewGroup.MarginLayoutParams
                p.setMargins(l, t, r, b)
                v.requestLayout()
            }
        }

        fun setPadding(v: View, l: Int, t: Int, r: Int, b: Int, context: Context) {
            val scale = context.getResources().getDisplayMetrics().density
            v.setPadding((l * scale + 0.5f).toInt(), (t * scale + 0.5f).toInt(), (r * scale + 0.5f).toInt(), (b * scale + 0.5f).toInt())
            v.requestLayout()
        }

        fun isOdd(`val`: Int): Boolean {
            return `val` and 0x01 != 0
        }

        fun setFont(context: Context, fontname: String): Typeface {
            return Typeface.createFromAsset(context.getAssets(), "fonts/" + fontname + ".ttf")
        }

        fun getLanguage(): String {
            return Locale.getDefault().getLanguage()
        }

        fun getDeviceName(): String {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

        private fun capitalize(s: String?): String {
            if (s == null || s.length == 0) {
                return ""
            }
            val first = s[0]
            return if (Character.isUpperCase(first)) {
                s
            } else {
                Character.toUpperCase(first) + s.substring(1)
            }
        }

        fun getPictures(activity: Activity): ArrayList<File> {
            val cursor = activity.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                    null, null, null)
            val count = cursor.getCount()
            val arrPath = arrayOfNulls<String>(count)
            val list = ArrayList<File>()

            for (i in 0 until count) {
                cursor.moveToPosition(i)
                val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                arrPath[i] = cursor.getString(dataColumnIndex)
                val file = File(cursor.getString(1))
                list.add(file)
            }
            cursor.close()

            return list
        }
    }
}