package com.example.wallpaperwizfirebaseadmin.helper

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.wallpaperwizfirebaseadmin.R
import java.util.*

class AppConstant {

    companion object {

        var IS_LOGIN = "is_login"
        var IS_ADMIN_LOGIN = "is_admin_login"

        var MOVIE_NAME = "movie_name"
        var CAST = "cast"
        var IMDB_RATING = "imdb_rating"
        var DIRECTED_BY = "directed_by"
        var IMAGE_URL = "image_url"
        var THUMBNAIL_URL = "thumbnail_url"
        var YEAR_OF_RELEASE = "year_of_release"
        var MOVIE_ID = "movie_id"
        var TRAILER_URL = "trailer_url"
        var MOVIE_TYPE = "movie_type"

        var MAIN_IMAGE_URL = "main_image_url"
        var THUMBNAIL_IMAGE_URL = "thumbnail_image_url"

        var LANGUAGE = "user_language"
        var is_dark_mode_on = "no"

        lateinit var dialog:ProgressDialog

        fun setLanguage(activity: Context?) {
            val sharedPreferences =
                activity!!.getSharedPreferences("LANGUAGE_SETTING", Context.MODE_PRIVATE)
            val languageToLoad: String
            languageToLoad =
                if (sharedPreferences.getString(AppConstant.LANGUAGE, "en").equals("en", ignoreCase = true)) {
                    "en" // your language
                } else {
                    "gu"
                }
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            activity.resources.updateConfiguration(
                config,
                activity.resources.displayMetrics
            )
        }

        fun showProgressDialog(context: Context?) {
            dialog = ProgressDialog(context)
            if (dialog.window != null) dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.isIndeterminate = true
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.show()
            dialog.setContentView(R.layout.custom_progress)
        }

        fun hideProgressDialog() {
            if (dialog != null && dialog.isShowing) dialog.dismiss()
        }


    }
}