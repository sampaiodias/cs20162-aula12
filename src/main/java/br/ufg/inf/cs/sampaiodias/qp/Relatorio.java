/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.cs.sampaiodias.qp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

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
     * Divisor para converter tempo de Nanosegundo para Milisegundo.
     */
    private static final int CONVERSAO_NS_MS = 1000000;

    /**
     * Gera arquivo HTML indicando a duração e o tempo médio de cada teste.
     *
     * @param duracaoTestes duração em ms de cada teste executado
     * @param memInicial memória inicial empregada pela JVM, antes dos testes
     * @param memFinal memória empregadada pela JVM ao terminar os testes
     * @param exp vetor de expressões usadas nos testes
     * @throws java.io.FileNotFoundException Arquivo não encontrado
     * @throws IOException Falha de entrada e saída
     */
    public static void gerarHTML(final long[] duracaoTestes,
            final long memInicial, final long memFinal, final Expressao[] exp)
            throws FileNotFoundException, IOException {
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("<html>\n<head>\n<meta charset=\"UTF-8\">\n");
        relatorio.append("<title>Testes do Parser</title>\n");
        relatorio.append("</head>\n<body>\n");
        relatorio.append("<h1>Resultado geral</h1>\n");
        relatorio.append("<table>\n<tr>\n");
        relatorio.append("<td><b>Tempo total: </b></td>\n");
        relatorio.append("<td>").append((duracaoTestes[0]) / CONVERSAO_NS_MS);
        relatorio.append("ms</td>\n</tr>\n");
        relatorio.append("<td><b>Tempo médio: </b></td>\n");
        relatorio.append("<td>").append((float)(duracaoTestes[0] / exp.length)
                / CONVERSAO_NS_MS);
        relatorio.append("ms</td>\n</tr>\n");
        relatorio.append("<td><b>Num. de testes: </b></td>\n");
        relatorio.append("<td>").append(exp.length);
        relatorio.append("</td>\n</tr>\n");

        int numErros = 0;
        for (Expressao item : exp) {
            if (!"".equals(item.getMsgErro())) {
                numErros++;
            }
        }

        relatorio.append("<td><b>Erros: </b></td>\n");
        relatorio.append("<td>").append(numErros);
        relatorio.append("</td>\n</tr>\n");
        relatorio.append("</table>\n");

        relatorio.append("<h1>Testes Executados</h1>\n<table>\n");

        for (Expressao item : exp) {
            relatorio.append("<tr>\n");
            relatorio.append("<th>Expressão</th>\n");
            relatorio.append("<th>Esperado</th>\n");
            relatorio.append("<th>Obtido</th>\n");
            relatorio.append("<th></th>\n");
            relatorio.append("</tr>\n<tr>\n");
            relatorio.append("<td>");
            relatorio.append(item.getEquacao());
            relatorio.append("</td>\n");
            relatorio.append("<td>");
            relatorio.append(item.getResEsperado());
            relatorio.append("</td>\n");
            relatorio.append("<td>");
            relatorio.append(item.getResObtido());
            relatorio.append("</td>\n");
            relatorio.append("<td>");
            relatorio.append(item.getMsgErro());
            relatorio.append("</td>\n");
            relatorio.append("</tr>\n");
        }

        relatorio.append("</table>\n</body>\n</html>");

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
     * @throws IOException Falha de entrada e saída
     */
    public static void gerarJSON(final long[] duracaoTestes,
            final long memInicial, final long memFinal, final Expressao[] exp)
            throws FileNotFoundException, IOException {
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
     * @throws IOException Falha de entrada e saída
     */
    private static void gerarArquivo(final StringBuilder conteudo,
            final boolean isHTML) throws FileNotFoundException, IOException {
        if (isHTML) {
            File file = new File("relatorio.html");
            try (Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF8"))) {
                out.append(conteudo.toString());
                out.flush();
            }
        } else {
            try (PrintWriter out = new PrintWriter("relatorio.json")) {
                out.println(conteudo.toString());
            }
        }
    }
}
