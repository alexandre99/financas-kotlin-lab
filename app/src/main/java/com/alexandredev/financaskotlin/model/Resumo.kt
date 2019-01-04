package com.alexandredev.financaskotlin.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {
    val receita get() = somarPor(Tipo.RECEITA)

    val despesa get() = somarPor(Tipo.DESPESA)

    val total get() = receita.subtract(despesa)

    private fun somarPor(tipo: Tipo): BigDecimal {
        val somaDeTransacoesPeloTipo = transacoes.filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble() }
        return BigDecimal(somaDeTransacoesPeloTipo)
    }
}