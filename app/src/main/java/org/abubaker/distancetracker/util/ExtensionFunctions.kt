package org.abubaker.distancetracker.util

import android.view.View
import android.widget.Button

object ExtensionFunctions {

    // Show
    fun View.show() {
        this.visibility = View.VISIBLE
    }

    // Hide
    fun View.hide() {
        this.visibility = View.INVISIBLE
    }

    // Enable
    fun Button.enable() {
        this.isEnabled = true
    }

    // Disable
    fun Button.disable() {
        this.isEnabled = false
    }

}
