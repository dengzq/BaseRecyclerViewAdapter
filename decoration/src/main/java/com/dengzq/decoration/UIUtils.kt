package com.dengzq.decoration

import android.content.Context

/**
 * <p>author    dengzq</P>
 * <p>date      2018/11/9 上午9:58</p>
 * <p>package   com.dengzq.decoration</p>
 * <p>readMe    UIUtils</p>
 */

fun dp2px(context: Context, dp: Float): Int = (context.resources.displayMetrics.density * dp + 0.5f).toInt()