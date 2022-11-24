package common.hoangdz.lib.extensions

import android.util.Log
import common.hoangdz.lib.BuildConfig

/**
 * Created by HoangDepTrai on 18, July, 2022 at 11:21 AM
 */

fun Any.logError(message: Any?, isDebugOnly: Boolean = true) {
    if (isDebugOnly && !BuildConfig.DEBUG) return
    Log.e("logError_" + javaClass.simpleName, "$message")
}