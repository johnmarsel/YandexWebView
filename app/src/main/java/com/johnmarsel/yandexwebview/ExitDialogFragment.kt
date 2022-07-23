package com.johnmarsel.yandexwebview

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment


class ExitDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Exit")
                .setMessage("Testing")
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    requireActivity().finish()
                }
                .setPositiveButton("Yes") { dialog, id ->
                    dialog.dismiss()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(): ExitDialogFragment {
            return ExitDialogFragment()
        }
    }
}