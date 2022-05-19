package com.compose.jreader.utils

import android.util.Patterns
import androidx.compose.runtime.MutableState

/**
 * To check [MutableState] string value is valid one
 * @return True if valid else false
 */
fun MutableState<String>.isValidInput(): Boolean =
    this.trimValue().isNotBlank()

/**
 * To trim [MutableState] string value
 * @return Trimmed string
 */
fun MutableState<String>.trimValue(): String = this.value.trim()

/**
 * To check the email is valid or not
 * @return true if it is valid else false
 */
fun String.isValidEmail(): Boolean =
    this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()




