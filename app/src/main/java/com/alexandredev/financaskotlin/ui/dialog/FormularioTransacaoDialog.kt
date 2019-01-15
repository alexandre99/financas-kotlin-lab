package com.alexandredev.financaskotlin.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.alexandredev.financaskotlin.R
import com.alexandredev.financaskotlin.extension.converteParaCalendar
import com.alexandredev.financaskotlin.extension.formataParaBrasileiro
import com.alexandredev.financaskotlin.model.Tipo
import com.alexandredev.financaskotlin.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(
    private val context: Context,
    private val viewGroup: ViewGroup?
) {
    private val viewCriada = criaLayout()
    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoCategoria = viewCriada.form_transacao_categoria
    protected val campoData = viewCriada.form_transacao_data

    abstract protected val tituloBotaoPositivo: String

    fun chama(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, delegate)
    }

    private fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        val titulo = tituloPor(tipo)
        AlertDialog.Builder(this.context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(tituloBotaoPositivo, ({ _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()

                val dataConvertida = dataEmTexto.converteParaCalendar()

                val valor = converteCampoValor(valorEmTexto)

                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    categoria = categoriaEmTexto,
                    data = dataConvertida
                )

                delegate(transacaoCriada)
            }))
            .setNegativeButton("Cancelar", null)
            .show()
    }

    abstract fun tituloPor(tipo: Tipo): Int

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (e: NumberFormatException) {
            Toast.makeText(
                context,
                "Falha na conversão de valor",
                Toast.LENGTH_LONG
            )
                .show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {
        val categorias = categoriasPor(tipo)
        val adapter = ArrayAdapter.createFromResource(
            context,
            categorias,
            android.R.layout.simple_spinner_dropdown_item
        )
        campoCategoria.adapter = adapter
    }

    protected fun categoriasPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.array.categorias_de_receita
        } else {
            R.array.categorias_de_despesa
        }
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formataParaBrasileiro())

        campoData.setOnClickListener {
            DatePickerDialog(
                this.context,
                { _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    campoData.setText(dataSelecionada.formataParaBrasileiro())
                }, ano, mes, dia
            ).show()
        }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(this.context)
            .inflate(R.layout.form_transacao, this.viewGroup, false)
    }
}