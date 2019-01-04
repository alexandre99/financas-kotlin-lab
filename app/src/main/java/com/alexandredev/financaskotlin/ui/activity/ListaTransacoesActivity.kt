package com.alexandredev.financaskotlin.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexandredev.financaskotlin.R
import com.alexandredev.financaskotlin.model.Tipo
import com.alexandredev.financaskotlin.model.Transacao
import com.alexandredev.financaskotlin.ui.ResumoView
import com.alexandredev.financaskotlin.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)
        configuraLista(transacoes)
    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(this, transacoes);
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
            Transacao
                (
                BigDecimal(120.50),
                "Comida", Tipo.DESPESA
            ),
            Transacao(
                BigDecimal(200),
                "Economia", Tipo.RECEITA
            )
        )
    }
}