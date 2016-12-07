/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.cs.sampaiodias.qp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Classe que gera relatórios em HTML e JSON de testes de qualidade de Parser.
 *
 * @author lucas
 */
public final class Relatorio {

    /**
     * Restringe criação de instância.
     */
    private Relatorio() {

    }

    /**
     * Gera arquivo HTML indicando a duração e o tempo médio de cada teste.
     *
     * @param duracaoTestes duração em ms de cada teste executado
     * @param memInicial memória inicial empregada pela JVM, antes dos testes
     * @param memFinal memória empregadada pela JVM ao terminar os testes
     * @param exp vetor de expressões usadas nos testes
     * @throws java.io.FileNotFoundException Arquivo não encontrado
     */
    public static void gerarHTML(final long[] duracaoTestes,
            final long memInicial, final long memFinal, final Expressao[] exp)
            throws FileNotFoundException {
        StringBuilder relatorio = new StringBuilder();

        gerarArquivo(relatorio, true);
    }

    /**
     * Gera arquivo JSON indicando a duração e o tempo médio de cada teste.
     *
     * @param duracaoTestes duração em ms de cada teste executado
     * @param memInicial memória inicial empregadada pela JVM, antes dos testes
     * @param memFinal memória empregadada pela JVM ao terminar os testes
     * @param exp vetor de expressões usadas nos testes
     * @throws java.io.FileNotFoundException Arquivo não encontrado
     */
    public static void gerarJSON(final long[] duracaoTestes,
            final long memInicial, final long memFinal, final Expressao[] exp)
            throws FileNotFoundException {
        StringBuilder relatorio = new StringBuilder();

        InfoRelatorio info = new InfoRelatorio(duracaoTestes[0],
                duracaoTestes[1], memInicial, memFinal);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        relatorio.append("[");
        relatorio.append(gson.toJson(info));
        relatorio.append(",\n");
        relatorio.append(gson.toJson(exp));
        relatorio.append("]");

        gerarArquivo(relatorio, false);
    }

    /**
     * Gera o arquivo final de acordo com o tipo pedido.
     *
     * @param conteudo conteúdo a ser colocado no arquivo
     * @param isHTML se o arquivo é HTML ou JSON
     * @throws FileNotFoundException Arquivo não encontrado
     */
    private static void gerarArquivo(final StringBuilder conteudo,
            final boolean isHTML) throws FileNotFoundException {
        if (isHTML) {
            try (PrintWriter out = new PrintWriter("relatorio.html")) {
                out.println(conteudo.toString());
            }
        } else {
            try (PrintWriter out = new PrintWriter("relatorio.json")) {
                out.println(conteudo.toString());
            }
        }
    }
}
