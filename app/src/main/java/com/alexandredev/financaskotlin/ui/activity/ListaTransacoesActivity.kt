package com.alexandredev.financaskotlin.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.alexandredev.financaskotlin.R
import com.alexandredev.financaskotlin.delegate.TransacaoDelegate
import com.alexandredev.financaskotlin.model.Tipo
import com.alexandredev.financaskotlin.model.Transacao
import com.alexandredev.financaskotlin.ui.ResumoView
import com.alexandredev.financaskotlin.ui.adapter.ListaTransacoesAdapter
import com.alexandredev.financaskotlin.ui.dialog.AdicionaTransacaoDialog
import com.alexandredev.financaskotlin.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private var transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogAdicao(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogAdicao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(this, window.decorView as ViewGroup)
            .chama(tipo, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    transacoes.add(transacao)
                    atualizaTransacoes()
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(this, transacoes);
        lista_transacoes_listview.setOnItemClickListener { parent, view, position, id ->
            val transacao = transacoes[position]
            AlteraTransacaoDialog(this, window.decorView as ViewGroup)
                .chama(transacao, object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        transacoes[position] = transacao
                        atualizaTransacoes()
                    }
                })
        }
    }
}