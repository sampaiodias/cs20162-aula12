/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.cs.sampaiodias.qp;

/**
 * Classe que auxilia a geração de JSON para o Relatorio.
 *
 * @author lucas
 */
public class InfoRelatorio {

    /**
     * Duração total dos testes (em NanoSec).
     */
    private long duracaoEmNanosec;
    /**
     * Média de cada (em NanoSec).
     */
    private long tempoMedioEmNanosec;
    /**
     * Memoria inicial da JVM dos testes.
     */
    private long memoriaInicial;
    /**
     * Memoria final da JVM dos testes.
     */
    private long memoriaFinal;

    /**
     * Instancia um objeto que auxilia a criação do relatório em JSON.
     *
     * @param duracaoDoTeste tempo total dos testes em NanoSec
     * @param media tempo médio de cada teste em NanoSec
     * @param memoriaStart Memoria inicial da JVM dos testes
     * @param memoriaEnd Memoria final da JVM dos testes
     */
    public InfoRelatorio(final long duracaoDoTeste, final long media,
            final long memoriaStart, final long memoriaEnd) {
        this.duracaoEmNanosec = duracaoDoTeste;
        this.tempoMedioEmNanosec = media;
        this.memoriaInicial = memoriaStart;
        this.memoriaFinal = memoriaEnd;
    }
}
