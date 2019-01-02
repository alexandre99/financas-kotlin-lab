package com.alexandredev.financaskotlin.model

import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Calendar

class Transacao(
    val valor: BigDecimal,
    val categoria: String,
    val tipo: Tipo,
    val data: Calendar = Calendar.getInstance()
) {
}