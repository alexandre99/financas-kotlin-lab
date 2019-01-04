package com.alexandredev.financaskotlin.delegate

import com.alexandredev.financaskotlin.model.Transacao

interface TransacaoDelegate {
    fun delegate(transacao: Transacao)
}