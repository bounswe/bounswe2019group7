package com.example.app.tradersapp

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

class AnnotationsActionModeCallback(val bodyTextView: TextView, val mContext: Context?) : android.view.ActionMode.Callback {
    override fun onActionItemClicked(mode: android.view.ActionMode?, item: MenuItem?): Boolean {
        val startIndex = bodyTextView.selectionStart
        val endIndex = bodyTextView.selectionEnd
        Toast.makeText(
            mContext,
             startIndex.toString() + " ----- " + endIndex.toString(),
            Toast.LENGTH_SHORT
        ).show()
        return true
    }

    override fun onCreateActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
        menu?.add(0, 0, 0, "Annotate")?.setIcon(R.drawable.abc_ab_share_pack_mtrl_alpha);
        return true;
    }

    override fun onPrepareActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
        menu?.clear()
        return true;
    }

    override fun onDestroyActionMode(mode: android.view.ActionMode?) {

    }


}