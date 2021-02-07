package com.example.practicaltest.ui.splash

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.firebase.auth.FirebaseAuth
import com.example.practicaltest.R
import com.example.practicaltest.databinding.ActivityHomeBinding
import com.example.practicaltest.databinding.ActivitySplashBinding
import com.example.practicaltest.ui.auth.AuthActivity
import com.example.practicaltest.ui.base.BaseActivity

import com.example.practicaltest.ui.main.MainActivity
import com.example.practicaltest.ui.weatherinfo.WeatherInfoActivity
import com.example.practicaltest.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: BaseActivity<ActivitySplashBinding, SplashActivityViewModel>() {


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }
    override fun getViewModelClass(): Class<SplashActivityViewModel> = SplashActivityViewModel::class.java

    override fun layoutId(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animateView(binding.ivDone)
        Handler(Looper.getMainLooper()).postDelayed({
            var preferenceManager = PreferenceManager(this)
            if(preferenceManager.getBooleanPreference("login")){
                startActivity(MainActivity.getIntent(this))
            }else {
                startActivity(AuthActivity.getIntent(this))
            }
            finish()
        }, 2000)
    }

    private fun animateView(view: ImageView) {
        when (val drawable = view.drawable) {
            is AnimatedVectorDrawableCompat -> drawable.start()
            is AnimatedVectorDrawable -> drawable.start()
        }
    }
}