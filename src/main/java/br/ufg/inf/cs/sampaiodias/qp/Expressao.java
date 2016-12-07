/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.cs.sampaiodias.qp;

import java.util.Objects;

/**
 *
 * @author lucas
 */
public class Expressao {

    /**
     * Equacao a ser testada pelo Parser (ex: 4 * 3).
     */
    public String equacao;
    /**
     * Resultado esperado pela equacao.
     */
    public Float resultadoEsperado;
    /**
     * Nomes das variaveis da expressão e seus valores.
     */
    public String[] varsNome;
    /**
     * Valores das variaveis da expressão e seus valores.
     */
    public Float[] varsValores;
    /**
     * Resultado obtido pelo Parser.
     */
    public Float resultadoObtido;
    /**
     * Erro obtido pelo Parser, caso houver.
     */
    public String mensagemErro;

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
        this.equacao = equacao;
        this.resultadoEsperado = resultado;
        this.varsNome = nomes;
        this.varsValores = valores;
        this.mensagemErro = "";
    }
    /**
     * Verifica se o valor obtido é igual ao esperado pelo usuário.
     * @return verdadeiro se o teste obteve o valor devido.
     */
    public boolean testeAcertou(){
        return Objects.equals(this.resultadoEsperado, this.resultadoObtido);
    }
}
