package me.oleg.tagliber.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import me.oleg.tagliber.R
import java.util.concurrent.Executors


/**
 * A simple [Fragment] subclass.
 */
class AuthenticationPanelFragment : Fragment() {

    private val TAG = AuthenticationPanelFragment::class.java.name
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    @SuppressLint("ServiceCast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        view.findViewById<Button>(R.id.fingerprint_btn).setOnClickListener {

            val newExecutor = Executors.newSingleThreadExecutor()
            val myBiometricPrompt = BiometricPrompt(
                activity!!,
                newExecutor,
                object : BiometricPrompt.AuthenticationCallback() {
                    //onAuthenticationError is called when a fatal error occurrs//
                    override fun onAuthenticationError(errorCode: Int, @NonNull errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        } else {

                            //Print a message to Logcat//

                            Log.d(TAG, "An unrecoverable error occurred")
                        }
                    }

                    //onAuthenticationSucceeded is called when a fingerprint is matched successfully//

                    override fun onAuthenticationSucceeded(@NonNull result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)

                        //Print a message to Logcat//

                        Log.d(TAG, "Fingerprint recognised successfully")
                    }

                    //onAuthenticationFailed is called when the fingerprint doesn’t match//

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()

                        //Print a message to Logcat//

                        Log.d(TAG, "Fingerprint not recognised")
                    }
                })

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Title text goes here")
                .setSubtitle("Subtitle goes here")
                .setDescription("This is the description")
                .setNegativeButtonText("Cancel")
                .build()

            myBiometricPrompt.authenticate(promptInfo)

        }

    }
}
