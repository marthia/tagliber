package me.oleg.taglibro.utitlies

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher

class DelayedTextWatcher(private val delay: Long, private val task: (String) -> Unit) :
    TextWatcher {

    private val handler = Handler(Looper.getMainLooper())
    private var delayedTask: Runnable? = null

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {

        delayedTask?.let {
            handler.removeCallbacks(it)
        }


        delayedTask = Runnable { task.invoke(p0.toString()) }

        handler.postDelayed(delayedTask!!, delay)
    }
}