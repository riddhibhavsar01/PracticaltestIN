package com.example.practicaltest.ui.auth

import android.app.Activity
import android.content.*
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.example.practicaltest.R
import com.example.practicaltest.databinding.ActivityAuthBinding
import com.example.practicaltest.ui.auth.adapter.AuthPagerAdapter
import com.example.practicaltest.ui.base.BaseActivity
import com.example.practicaltest.ui.main.MainActivity
import javax.inject.Inject

import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.example.practicaltest.utils.PreferenceManager
import timber.log.Timber

class AuthActivity : BaseActivity<ActivityAuthBinding, AuthActivityViewModel>(),
    AuthActivityViewInteractor {

    companion object {
        private const val CREDENTIAL_PICKER_REQUEST = 1
        private const val SMS_CONSENT_REQUEST = 2

        fun getIntent(context: Context): Intent {
            return Intent(context, AuthActivity::class.java)
        }
    }

    override fun getViewModelClass(): Class<AuthActivityViewModel> =
        AuthActivityViewModel::class.java

    override fun layoutId(): Int = R.layout.activity_auth

    @Inject
    lateinit var authPagerAdapter: AuthPagerAdapter

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private val smsVerificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

                when (smsRetrieverStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val consentIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        try {
                            startActivityForResult(consentIntent, SMS_CONSENT_REQUEST)
                        } catch (e: ActivityNotFoundException) {
                            showSnackBar(e.message?: "Something went wrong")
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        // Time out occurred, handle the error.
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var preferenceManager = PreferenceManager(this)
        if(preferenceManager.getBooleanPreference("login")){
            goToGoalActivity()
        }
        else {
            viewModel.viewInteractor = this

            val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
            registerReceiver(smsVerificationReceiver, intentFilter)
            setUpPager()
            if (savedInstanceState == null) {
                requestHint()
            }
            viewModel.pagerPagePosition.observe(
                this, Observer<Int?> { value ->
                    binding.authViewPager.currentItem = value ?: 0
                }
            )
        }
    }

    private fun setUpPager() {
        authPagerAdapter.setCount(2)
        binding.authViewPager.adapter = authPagerAdapter
        binding.authViewPager.isUserInputEnabled = false
    }

    private fun requestHint() {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val credentialsClient = Credentials.getClient(this)
        val intent = credentialsClient.getHintPickerIntent(hintRequest)
        startIntentSenderForResult(
            intent.intentSender,
            CREDENTIAL_PICKER_REQUEST,
            null, 0, 0, 0
        )
    }

    override fun startSMSListener() {
        val smsRetrieverClient = SmsRetriever.getClient(this)
        val task = smsRetrieverClient.startSmsUserConsent(null)
        task.addOnSuccessListener {
            Toast.makeText(this, "SMS Retriever starts", Toast.LENGTH_LONG).show()
        }
        task.addOnFailureListener {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CREDENTIAL_PICKER_REQUEST ->
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val credential = data.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                    viewModel.selectedPhoneNumber.value = credential?.id
                }

            SMS_CONSENT_REQUEST ->
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    val oneTimeCode = message?.substring(0, 6)
                    Timber.d("AuthActivity.onActivityResult message $oneTimeCode")
                    viewModel.selectedOtpNumber.value = oneTimeCode?.trim()
                }
        }
    }

    override fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) {
                if (it.isSuccessful) {
                    var preferenceManager1 = PreferenceManager(this)
                    preferenceManager1.setBooleanPreference("login",true)
                    goToGoalActivity()
                } else {
                    // Show Error
                    if (it.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        showSnackBar(it.exception?.message?: "Verification Failed")
                    } else {
                        showSnackBar("Verification Failed")
                    }
                }
            }
    }

    override fun onBackPressed() {
        when (binding.authViewPager.currentItem) {
            AuthActivityViewModel.PHONE_VERIFICATION_PAGE -> super.onBackPressed()
            AuthActivityViewModel.OTP_VERIFICATION_PAGE -> {
                binding.authViewPager.currentItem = AuthActivityViewModel.PHONE_VERIFICATION_PAGE

            }
            else -> super.onBackPressed()
        }
    }

    override fun goToGoalActivity() {
        startActivity(MainActivity.getIntent(this))
        finish()
    }

    override fun showSnackBarMessage(message: String) {
        showSnackBar(message)
    }

    override fun onDestroy() {
        var preferenceManager = PreferenceManager(this)
        if(!preferenceManager.getBooleanPreference("login")) {
            unregisterReceiver(smsVerificationReceiver)
        }
        super.onDestroy()
    }
}