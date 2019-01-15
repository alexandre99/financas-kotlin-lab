package com.alexandredev.financaskotlin.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.alexandredev.financaskotlin.R
import com.alexandredev.financaskotlin.extension.formataParaBrasileiro
import com.alexandredev.financaskotlin.model.Tipo
import com.alexandredev.financaskotlin.model.Transacao

class AlteraTransacaoDialog(
    private val context: Context,
    private val viewGroup: ViewGroup
) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.altera_receita
        } else {
            R.string.altera_despesa
        }
    }

    fun chama(transacao: Transacao, delegate: (transacao: Transacao) -> Unit) {
        val tipo = transacao.tipo

        chama(tipo, delegate);

        inicializaCampos(transacao, tipo)
    }

    private fun inicializaCampos(
        transacao: Transacao,
        tipo: Tipo
    ) {
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(transacao)
    }

    private fun inicializaCampoCategoria(
        transacao: Transacao
    ) {
        val tipo = transacao.tipo
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }
}