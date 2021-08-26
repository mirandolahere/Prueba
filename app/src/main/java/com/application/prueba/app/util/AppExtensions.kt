package com.application.prueba.app.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.application.prueba.data.network.Resource
import com.google.android.material.snackbar.Snackbar


// DATA STORE

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

suspend fun DataStore<Preferences>.addToLocalStorage(mutableFunc: MutablePreferences.() -> Unit) {
    edit {
        mutableFunc(it)
    }
}

// VIEW

fun<A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun<A : Activity> Fragment.startNewActivity(activityClass: Class<A>) {
    requireActivity().startNewActivity(activityClass)
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    messageId: Int? = null,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> {
          /*  Log.e("NET", failure.toString())
            requireView().snackbar(
                getString(R.string.no_lo_vimos_venir),
            ) { retry?.invoke() }*/
        }
        failure.errorCode == 401 -> {
            /*if (this is RegisterFragment) {
                requireView().snackbar(getString(R.string.credenciales_incorrectas))
            } else {
                logout()
            }*/
        }
        failure.errorCode == 404 -> {
          /*  val message =  getString(messageId?:R.string.mensaje_de_error_404)
            requireView().snackbar(message)*/
        }
        else -> {
            requireView().snackbar("¿Error ${failure.errorCode}? ¿Qué es eso?")
        }
    }
}

fun View.snackbar(message: String, position: Int? = null, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, 4000)

    position?.let {
        val snacView = snackbar.view
        val params = snacView.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = position
        snacView.layoutParams = params
    }

    snackbar.setAction("OK!") { action?.invoke() }
    snackbar.show()
}
