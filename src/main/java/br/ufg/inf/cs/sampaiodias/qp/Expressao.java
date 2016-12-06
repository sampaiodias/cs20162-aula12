/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.cs.sampaiodias.qp;

import java.util.Map;

/**
 *
 * @author lucas
 */
public class Expressao {

    /**
     * Equacao a ser testada pelo Parser (ex: 4 * 3).
     */
    public static String equacao;
    /**
     * Resultado esperado pela equacao.
     */
    public static Float resultadoEsperado;
    /**
     * Nomes das variaveis da expressão e seus valores.
     */
    public static String[] varsNome;
    /**
     * Valores das variaveis da expressão e seus valores.
     */
    public static Float[] varsValores;
    /**
     * Resultado obtido pelo Parser.
     */
    public static Float resultadoObtido;

    /**
     * Instancia um objeto do tipo Expressao (linha tratada do arquivo txt).
     *
     * @param equacao Equacao a ser testada pelo Parser (ex: 4 * 3).
     * @param resultado Resultado esperado pela equacao.
     * @param nomes Nomes das variaveis contidas na expressão.
     * @param valores Valores das variaveis contidas na expressão.
     */
    public Expressao(final String equacao, final Float resultado,
            final String[] nomes, final Float[] valores) {
        Expressao.equacao = equacao;
        Expressao.resultadoEsperado = resultado;
        Expressao.varsNome = nomes;
        Expressao.varsValores = valores;
    }
}
