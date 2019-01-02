package com.alexandredev.financaskotlin.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.alexandredev.financaskotlin.R
import com.alexandredev.financaskotlin.extension.formataParaBrasileiro
import com.alexandredev.financaskotlin.extension.limitaEmAte
import com.alexandredev.financaskotlin.model.Tipo
import com.alexandredev.financaskotlin.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(
    private val context: Context,
    private val transacoes: List<Transacao>
) : BaseAdapter() {

    private val limiteDaCategoria = 14

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[position]

        adicionaValor(transacao, view)
        adicionaIcone(transacao, view)
        adicionaCategoria(view, transacao)
        adicionaData(view, transacao)

        return view
    }

    private fun adicionaData(view: View, transacao: Transacao) {
        view.transacao_data.text = transacao.data.formataParaBrasileiro()
    }

    private fun adicionaCategoria(view: View, transacao: Transacao) {
        view.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDaCategoria)
    }

    private fun adicionaValor(transacao: Transacao, view: View) {
        val cor: Int = corPor(transacao.tipo)
        view.transacao_valor.setTextColor(cor)
        view.transacao_valor.text = transacao.valor.formataParaBrasileiro()
    }

    private fun adicionaIcone(transacao: Transacao, view: View) {
        val icone: Int = iconePor(transacao.tipo)
        view.transacao_icone.setBackgroundResource(icone)
    }

    private fun iconePor(tipo: Tipo): Int {
        return if (Tipo.RECEITA.equals(tipo)) {
            R.drawable.icone_transacao_item_receita
        } else {
            R.drawable.icone_transacao_item_despesa
        }
    }

    private fun corPor(tipo: Tipo): Int {
        return if (Tipo.RECEITA.equals(tipo)) {
            ContextCompat.getColor(context, R.color.receita)
        } else {
            ContextCompat.getColor(context, R.color.despesa)
        }
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0;
    }

    override fun getCount(): Int {
        return transacoes.size
    }

}