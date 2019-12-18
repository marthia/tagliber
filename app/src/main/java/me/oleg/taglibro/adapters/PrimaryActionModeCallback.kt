package me.oleg.taglibro.adapters

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes

class PrimaryActionModeCallback(
    private val onActionItemClickListener: OnActionItemClickListener,
    private val onActionModeDestroyCallback: OnActionModeDestroyCallback
) : ActionMode.Callback {

    private var mode: ActionMode? = null
    @MenuRes
    private var menuResId: Int = 0
    private var title: String? = null
    private var subtitle: String? = null


    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        onActionItemClickListener.onActionItemClick(item)
        return false
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        this.mode = mode
        mode?.menuInflater?.inflate(menuResId, menu)
        mode?.title = title
        mode?.subtitle = subtitle
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        onActionModeDestroyCallback.onActionModeDestroy()
        this.mode = null
    }


    fun startActionMode(
        view: View,
        @MenuRes menuResId: Int,
        title: String? = null,
        subtitle: String? = null
    ) {
        if (mode == null) {
            this.title = title
            this.subtitle = subtitle
            this.menuResId = menuResId
            view.startActionMode(this)
        } else {
            mode?.title = title
        }

    }

    fun finishActionMode() {
        mode?.finish()
    }
}