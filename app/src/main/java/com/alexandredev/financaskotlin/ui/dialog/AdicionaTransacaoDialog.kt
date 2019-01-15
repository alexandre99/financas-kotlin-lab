package com.alexandredev.financaskotlin.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.alexandredev.financaskotlin.R
import com.alexandredev.financaskotlin.model.Tipo

class AdicionaTransacaoDialog(
    private val context: Context,
    private val viewGroup: ViewGroup
) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }
    }

}