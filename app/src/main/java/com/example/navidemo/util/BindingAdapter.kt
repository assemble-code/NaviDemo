package com.example.navidemo.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.navidemo.R
import com.example.navidemo.constants.Constants.SERVER_DATE_TIME_FORMAT
import com.example.navidemo.constants.Constants.UI_DATE_FORMAT
import com.example.navidemo.constants.Constants.UI_TIME_FORMAT
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {
    private val serverDateFormatter = SimpleDateFormat(SERVER_DATE_TIME_FORMAT, Locale.getDefault())
    private val requiredDateFormatter = SimpleDateFormat(UI_DATE_FORMAT, Locale.getDefault())
    private val requiredTimeFormatter = SimpleDateFormat(UI_TIME_FORMAT, Locale.getDefault())

    @JvmStatic
    @BindingAdapter("profileImage")
    fun CircleImageView.setProfileImage(url: String) {
        val requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_broken_image_72)
            .error(R.drawable.ic_baseline_broken_image_72)
        Glide.with(this).load(url).apply(requestOptions).into(this)
    }

    @JvmStatic
    @BindingAdapter("dateFromServer", "isCreatedDate")
    fun TextView.setFormattedDateTime(serverDate: String, isCreatedDate: Boolean) {
        kotlin.runCatching { serverDateFormatter.parse(serverDate) }.onSuccess {
            it?.let { date ->
                this.apply {
                    text = if (isCreatedDate) {
                        this.context.getString(
                            R.string.created_on,
                            requiredDateFormatter.format(date)+" at "+requiredTimeFormatter.format(date)
                        )
                    } else {
                        this.context.getString(
                            R.string.closed_on,
                            requiredDateFormatter.format(date)+" at "+requiredTimeFormatter.format(date)
                        )
                    }
                }
            }
        }
    }
}