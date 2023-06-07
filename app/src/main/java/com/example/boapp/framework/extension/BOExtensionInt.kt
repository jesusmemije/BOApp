package com.example.boapp.framework.extension

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Int.toFormatCoinMXN(): String {
    val symbols = DecimalFormatSymbols()
    symbols.groupingSeparator = ','
    symbols.decimalSeparator = '.'
    val decimalFormat = DecimalFormat("$#,###.00", symbols)
    return decimalFormat.format(this)
}