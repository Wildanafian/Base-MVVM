@file:JvmName("UtilsKt")

package com.base.core.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.base.core.BuildConfig
import com.base.core.R
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Wildan Nafian on 12/01/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

/**
 * Intent to somewhere
 * gooTo<Destination Activity>()
 *
 * Intent to somewhere with extra
 * gooTo<Destination Activity>(putExtras(key, data))
 */
inline fun <reified T : Activity> Activity.gooTo() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Activity> Activity.gooTo(noinline argsBuilder: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply {
        argsBuilder()
    })
}

inline fun <reified T : Activity> Fragment.gooTo(noinline argsBuilder: Intent.() -> Unit) {
    requireActivity().startActivity(Intent(requireActivity(), T::class.java).apply {
        argsBuilder()
    })
}

inline fun <reified T : Activity> Activity.gooToAndClear(noinline argsBuilder: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        argsBuilder()
        finish()
    })
}

inline fun <reified T : Activity> Fragment.gooToAndClear(noinline argsBuilder: Intent.() -> Unit) {
    requireActivity().startActivity(Intent(requireContext(), T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        argsBuilder()
        requireActivity().finish()
    })
}

/**
 * Bundle
 * withArgs { putString(key, data) }
 */
inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply { arguments = Bundle().apply(argsBuilder) }

/**
 * Get Intent Extra
 * getExtraString(key)
 */
fun Activity.getExtraBundle(key: String): Bundle? = intent.getBundleExtra(key)
fun Activity.getExtraParable(key: String): Parcelable? = intent.getParcelableExtra(key)
fun Activity.getExtraString(key: String): String = intent.getStringExtra(key) ?: ""
fun Activity.getExtraInt(key: String): Int = intent.getIntExtra(key, 0)
fun Activity.getExtraBool(key: String): Boolean = intent.getBooleanExtra(key, false)

fun Fragment.getExtraBundle(key: String): Bundle? = requireArguments().getBundle(key)
fun Fragment.getExtraString(key: String): String = requireArguments().getString(key, "") ?: ""
fun Fragment.getExtraInt(key: String): Int = requireArguments().getInt(key, 0)

/**
 * Initialize RecycleView
 * yourRecycleView.initRecycleView(your adapter)
 */
fun <RV : RecyclerView.ViewHolder?> RecyclerView.initRecycleView(
    adapterRV: RecyclerView.Adapter<RV>,
    isVertical: Boolean = true,
    isReverse: Boolean = false,
    fixedSize: Boolean = false,
    cacheRv: Boolean = false
) =
    this.apply {
        layoutManager = LinearLayoutManager(
            this.context,
            if (isVertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL,
            isReverse
        )
        adapter = adapterRV
        setHasFixedSize(fixedSize)
        if (cacheRv) setItemViewCacheSize(25)

    }

/**
 * Load image
 * imageView.LoadImageFrom(your url)
 */
fun ImageView.loadImageFrom(imageUrl: String) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.doc_no_image)
        .error(R.drawable.doc_no_image)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .priority(Priority.HIGH)
        .override(111, 111)
    Glide.with(this.context)
        .load(imageUrl)
        .apply(options)
        .thumbnail(0.25f)
        .into(this)
}

/**
 * Gson
 *
 * Convert model to json string
 * toJson(data)
 *
 * Convert json string to model
 * fromJson<Model>(data)
 */
fun Any?.toJson(): String {
    return try {
        Gson().toJson(this ?: "")
    } catch (e: Exception) {
        ""
    }
}

inline fun <reified T> String?.fromJson(): T {
    return try {
        Gson().fromJson<T>(this ?: "", object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        e.printLog()
        Gson().fromJson<T>("", object : TypeToken<T>() {}.type)
    }
}

//inline fun <reified T> fromJsonDecrypt(json: String?):T =  Gson().fromJson<T>(AESUtil.decrypt(json), object : TypeToken<T>() {}.type)

/**
 * JSON
 * jsonObject {
 *  put("key", "data")
 *  put("key2", jsonArray{})
 * }
 *
 * jsonArray {
 *  put(jsonObject{
 *      put("key", "data")
 *      put("key", "data")
 *  })
 * }
 *
 */
inline fun jsonObject(argsBuilder: JSONObject.() -> Unit): JSONObject =
    JSONObject().apply(argsBuilder)

inline fun jsonArray(argsBuilder: JSONArray.() -> Unit): JSONArray = JSONArray().apply(argsBuilder)

/**
 * Date Formater
 *
 * Get current date
 * getCurrentDate()
 *
 * Get current date with format
 * getCurrentDateWithFormat(your format)
 *
 * You can also get current time with getCurrentDateWithFormat()
 * getCurrentDateWithFormat("HH:mm:ss")
 *
 * Get date before/after current date
 * getDateBeforeCurrentDate(how many day)
 *
 * Change current string date to other date format
 * 21 01 2022.formatDateWith("dd MM yyyy", "MM-yyyy")
 * result = 01-2022
 **/

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String = doMagic("dd MM yyyy", Calendar.getInstance().time)

fun getCurrentDateWithFormat(outputDatePattern: String): String =
    doMagic(outputDatePattern, Calendar.getInstance().time)

fun getDateBeforeCurrentDate(number: Int): String = doMagic("dd MM yyyy", doMagicCalendar(-number))

fun getDateAfterCurrentDate(number: Int): String = doMagic("dd MM yyyy", doMagicCalendar(number))

fun getDateBeforeCurrentDate(outputDatePattern: String, number: Int): String =
    doMagic(outputDatePattern, doMagicCalendar(-number))

fun getDateAfterCurrentDate(outputDatePattern: String, number: Int): String =
    doMagic(outputDatePattern, doMagicCalendar(number))

fun String?.formatDateWith(currentPattern: String, outputPattern: String): String = try {
    doMagic(outputPattern, SimpleDateFormat(currentPattern, Locale("id", "ID")).parse(this ?: "")!!)
} catch (e: Exception) {
    e.printLog()
    ""
}


fun doMagic(pattern: String, inputDate: String?): String {
    return try {
        SimpleDateFormat(pattern, Locale("id", "ID")).format(inputDate)
    } catch (e: Exception) {
        e.printLog()
        ""
    }
}

fun doMagic(pattern: String, inputDate: Date): String {
    return try {
        SimpleDateFormat(pattern, Locale("id", "ID")).format(inputDate)
    } catch (e: Exception) {
        e.printLog()
        ""
    }
}

private fun doMagicCalendar(number: Int): Date {
    val c = Calendar.getInstance()
    c.add(Calendar.DATE, number)
    return c.time
}

/**
 * Format data to Rupiah format
 * yourStringData.formatToRupiah()
 * 10.000, 10.000,00, 10.000.00
 * result = Rp 10.000
 *
 */
fun String?.formatToRupiah(): String {
    return "Rp ${
        NumberFormat.getIntegerInstance(Locale.GERMANY).format(this.clearRupiahNumbering().toLong())
    }"
}

fun Int?.formatToRupiah(): String {
    return "Rp ${
        NumberFormat.getIntegerInstance(Locale.GERMANY)
            .format(this.toString().clearRupiahNumbering().toLong())
    }"
}

/**
 * Clear rupiah format
 * yourString.clearRupiah()
 * Rp 10.000 , Rp 10.000.00, Rp 10.000,00, 10.000
 * result = 10.000
 */
fun String?.clearRupiah(): String {
    return NumberFormat.getIntegerInstance(Locale.GERMANY)
        .format(this.clearRupiahNumbering().toLong())
}

fun Int?.clearRupiah(): String {
    return NumberFormat.getIntegerInstance(Locale.GERMANY)
        .format(this.toString().clearRupiahNumbering().toLong())
}

/**
 * Clear numbering format to raw string
 * yourString.clearNumbering()
 * 10.000 , 10.000.00, 10.000,00
 * result = 10000
 */
fun String?.clearNumbering(): String = this.clearRupiahNumbering()

/**
 * Clear all rupiah format
 * yourString.clearRupiah()
 * Rp 10.000 , Rp 10.000.00, Rp 10.000,00, 10.000
 * result = 10000
 */
fun String?.clearRupiahNumbering(): String {
    val replaceable =
        String.format("[Rp,.\\s]", NumberFormat.getCurrencyInstance(Locale("id", "ID"))).toRegex()
    return try {
        if (this?.takeLast(3) == ".00" || this?.takeLast(3) == ",00") {
            val temp = this.removeRange(this.length - 3, this.length)
            temp.replace(replaceable, "")
        } else {
            this?.replace(replaceable, "") ?: "0"
        }
    } catch (e: Exception) {
        "0"
    }
}

/**
 * Log
 * yourString.printLog(optional tag)
 * yourString.printLog("@@")
 */
fun String?.printLog(tag: String = ProjectConstant.TAG) {
    if (BuildConfig.DEBUG) Timber.tag(tag).d(this ?: "null")
}

fun Exception.printLog(tag: String = ProjectConstant.TAG) {
    if (BuildConfig.DEBUG) Timber.tag(tag).i(this.localizedMessage)
}

fun Throwable.printLog(tag: String = ProjectConstant.TAG) {
    if (BuildConfig.DEBUG) Timber.tag(tag).i(this.localizedMessage)
}

/**
 * Set color & drawable
 * setColor(R.color.red) // setDrawable(getExtraInt("icon"))
 */
fun <T : Fragment> T.setColor(color: Int): Int = ContextCompat.getColor(requireContext(), color)
fun <T : Activity> T.setColor(color: Int): Int = ContextCompat.getColor(this, color)

fun <T : Fragment> T.setDrawable(drawable: Int): Drawable? =
    ContextCompat.getDrawable(requireContext(), drawable)

fun <T : Activity> T.setDrawable(drawable: Int): Drawable? =
    ContextCompat.getDrawable(this, drawable)

/**
 * View Enable
 * yourView.setEnable()
 */
fun View.setEnable() {
    this.isEnabled = true
}

fun View.setDisable() {
    this.isEnabled = false
}

/**
 * Visibility
 * yourView.setVisible()
 */
fun View.setVisile() {
    this.visibility = View.VISIBLE
}

fun setVisile() = View.VISIBLE


fun View.setGone() {
    this.visibility = View.GONE
}

fun setGone() = View.GONE

fun View.setInvincible() {
    this.visibility = View.INVISIBLE
}

fun setInvincible() = View.INVISIBLE

fun View.setVisibilityWhen(value: Boolean) {
    this.visibility = if (value) View.VISIBLE else View.GONE
}

/**
 * Network checker online / offline
 * networkCheck(context)
 */
@SuppressLint("ObsoleteSdkInt")
fun Context.networkCheck(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) return true
    }
    return false
}

typealias Inflate<T> = (LayoutInflater) -> T


/**
 * Created by Ian Damping on 21,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
fun <T> Fragment.observeEvent(data: LiveData<Event<T>>, onBound: ((T) -> Unit)) {
    data.observe(this.viewLifecycleOwner, EventObserver {
        onBound.invoke(it)
    })
}

fun <T> Fragment.observe(data: LiveData<T>, onBound: ((T?) -> Unit)) {
    data.observe(this.viewLifecycleOwner) {
        onBound.invoke(it)
    }
}

fun <T> Fragment.observeText(data: LiveData<T>, onBound: ((T) -> Unit)) {
    data.observe(this.viewLifecycleOwner) {
        onBound.invoke(it)
    }
}

/**
 **************************************
 */


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.listener(callback : ((Int)->Unit)? = null) {
    this.addTextChangedListener(onTextChanged = { _, _, _, count ->
        callback?.invoke(count)
    })
}


fun View.listener(callback: () -> Unit) = this.setOnClickListener {
    callback()
}

fun SearchView.listener(
    onTextChange: ((String) -> Unit)? = null,
    onTextSubmit: ((String) -> Unit)? = null
) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            onTextChange?.invoke(newText)
            return false
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            onTextSubmit?.invoke(query)
            return false
        }
    })
}

fun LifecycleOwner.disableBackPressed(owner: FragmentActivity) = owner.onBackPressedDispatcher.addCallback(this) {}

fun Fragment.spinnerAdapter(data: ArrayList<String>): ArrayAdapter<String> =
    ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, data).apply<ArrayAdapter<String>> {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

fun EditText.getTexts() = this.text.toString()

fun View.setVisibilityAnimSlide(visibility: Int, duration: Long = 200) {
    val transition: Transition = Slide(Gravity.BOTTOM)
    transition.duration = duration
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    this.visibility = visibility
}

fun View.changeMotionLayoutChildVisibility(visibility: Boolean) {
    val motionLayout = parent as MotionLayout
    motionLayout.constraintSetIds.forEach {
        val constraintSet = motionLayout.getConstraintSet(it) ?: return@forEach
        constraintSet.setVisibility(this.id, if (visibility) View.VISIBLE else View.GONE)
        constraintSet.applyTo(motionLayout)
    }
}