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
    private String equacao;
    /**
     * Resultado esperado pela equacao.
     */
    private Float resultadoEsperado;
    /**
     * Nomes das variaveis da expressão e seus valores.
     */
    private String[] varsNome;
    /**
     * Valores das variaveis da expressão e seus valores.
     */
    private Float[] varsValores;
    /**
     * Resultado obtido pelo Parser.
     */
    private Float resultadoObtido;
    /**
     * Erro obtido pelo Parser, caso houver.
     */
    private String mensagemErro;

    /**
     * Instancia um objeto do tipo Expressao (linha tratada do arquivo txt).
     *
     * @param eq Equacao a ser testada pelo Parser (ex: 4 * 3).
     * @param resultado Resultado esperado pela equacao.
     * @param nomes Nomes das variaveis contidas na expressão.
     * @param valores Valores das variaveis contidas na expressão.
     */
    public Expressao(final String eq, final Float resultado,
            final String[] nomes, final Float[] valores) {
        this.equacao = eq;
        this.resultadoEsperado = resultado;
        this.varsNome = nomes;
        this.varsValores = valores;
        this.mensagemErro = "";
    }

    /**
     * Verifica se o valor obtido é igual ao esperado pelo usuário.
     *
     * @return verdadeiro se o teste obteve o valor devido.
     */
    public final boolean testeAcertou() {
        return Objects.equals(this.resultadoEsperado, this.resultadoObtido);
    }

    /**
     * Encontra a equação/expressão.
     *
     * @return String com a equação
     */
    public final String getEquacao() {
        return this.equacao;
    }

    /**
     * Encontra o nome de cada variável contida na expressão. Seu respectivo
     * valor está em varsValor.
     *
     * @return Vetor de String em que cada elemento é uma variável.
     */
    public final String[] getVarsNome() {
        return this.varsNome;
    }

    /**
     * Encontra o valor de cada variável contida na expressão. Seu respectivo
     * nome está em varsNome.
     *
     * @return Vetor de Float em que cada elemento é o valor de uma variável.
     */
    public final Float[] getVarsValores() {
        return this.varsValores;
    }

    /**
     * Retorna o valor obtido pelo Parser, salvo neste objeto.
     *
     * @return valor float do resultado, caso já tenha sido obtido pelo Parser
     */
    public final Float getResObtido() {
        return this.resultadoObtido;
    }

    /**
     * Define na expressão qual foi o valor encontrado como resultado.
     *
     * @param valor Resultado obtido pelo Patser
     */
    public final void setResObtido(final Float valor) {
        this.resultadoObtido = valor;
    }

    /**
     * Retorna a mensagem de erro obtida pelo teste, caso houver.
     *
     * @return Mensagem de erro ou "" caso não houverem erros
     */
    public final String getMsgErro() {
        return this.mensagemErro;
    }

    /**
     * Define uma mensagem de erro caso haja um problema no teste da expressão.
     *
     * @param mensagem String contendo o aviso para o usuário
     */
    public final void setMsgErro(final String mensagem) {
        this.mensagemErro = mensagem;
    }
}
