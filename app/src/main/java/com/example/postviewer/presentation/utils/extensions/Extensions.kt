package com.example.postviewer.presentation.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.postviewer.R
import com.example.postviewer.presentation.utils.FRAGMENT_CONTAINER

fun ViewGroup.inflate(layout: Int): View {
    return LayoutInflater.from(this.context).inflate(layout, this, false)
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.replaceTransaction(f: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(FRAGMENT_CONTAINER, f)
        .addToBackStack(null)
        .commit()
}

fun AppCompatActivity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.postsRecyclerViewDialog(message: String) {
    val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.DialogStyle)

    val alertDialog = alertDialogBuilder.create()

    val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.post_dialog_layout, null).apply {
        findViewById<TextView>(R.id.dialogMessage).apply { text = message }
    }
    alertDialog.setView(dialogView)
    alertDialog.show()

}
