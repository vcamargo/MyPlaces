package com.vcamargo.myplaces.fragment

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import com.vcamargo.myplaces.R

class ErrorDialog(val onRetry: () -> Unit, val onCancel : () -> Unit) {
    private var errorDialog: AlertDialog? = null

    private fun createErrorDialog(errorMsg: String, context: Context): AlertDialog {
        val builder: AlertDialog.Builder? = context.let {
            AlertDialog.Builder(ContextThemeWrapper(it, R.style.ErrorDialog))
        }
        builder?.let {
            it.setMessage(errorMsg)
                .setTitle(context.getString(R.string.error_dialog_title))
                .apply {
                    setPositiveButton(context.getString(R.string.error_dialog_btn_retry)
                    ) { dialog, id ->
                        onRetry()
                        dialog.cancel()
                    }
                    setNegativeButton(context.getString(R.string.error_dialog_btn_cancel)
                    ) { dialog, id ->
                        onCancel()
                        dialog.cancel()
                    }
                }
            return it.create()
        } ?: run {
            // if context if null then we shouldn't be here
            throw RuntimeException("context is null when creating an error dialog")
        }
    }

    fun showErrorDialog(errorMsg: String, context: Context) {
        errorDialog = createErrorDialog(errorMsg, context)
        errorDialog?.show()
    }
}