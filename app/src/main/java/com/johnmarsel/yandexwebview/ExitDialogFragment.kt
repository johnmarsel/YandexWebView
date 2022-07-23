package com.johnmarsel.yandexwebview

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ExitDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle("Exit")
                .setMessage("Do you want to exit? ")
                .setPositiveButton("Yes") { dialog, id ->
                    dialog.dismiss()
                    requireActivity().finish()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(): ExitDialogFragment {
            return ExitDialogFragment()
        }
    }
}