/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.cs.sampaiodias.qp;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Classe que gera relatórios em HTML e JSON de testes de qualidade de Parser.
 * @author lucas
 */
public final class Relatorio {
    
    /**
     * Restringe criação de instância.
     */
    private Relatorio() {
        
    }
    /**
     * Em caso de erro, subtrai-se X chars para retirar a última vírgula (JSON).
     */
     private static final int LIMITE_VIRGULA = 3;
    /**
     * Gera arquivo HTML indicando a duração e o tempo médio de cada teste.
     * 
     * @param duracaoTestes duração em ms de cada teste executado
     * @param memInicial memória inicial empregada pela JVM, antes dos testes
     * @param memFinal memória empregadada pela JVM ao terminar os testes
     * @param erros mensagem de erro de cada teste, caso houver
     * @throws java.io.FileNotFoundException
     */
    public static void gerarHTML(final float[] duracaoTestes, 
            final float memInicial, final float memFinal, final String[] erros) 
            throws FileNotFoundException{
        StringBuilder relatorio = new StringBuilder();
        
        
        
        gerarArquivo(relatorio, true);
    }
    /**
     * Gera arquivo JSON indicando a duração e o tempo médio de cada teste.
     * 
     * @param duracaoTestes duração em ms de cada teste executado
     * @param memInicial memória inicial empregadada pela JVM, antes dos testes
     * @param memFinal memória empregadada pela JVM ao terminar os testes
     * @param erros mensagem de erro de cada teste, caso houver
     * @throws java.io.FileNotFoundException
     */
    public static void gerarJSON(final float[] duracaoTestes, 
            final float memInicial, final float memFinal, final String[] erros) 
            throws FileNotFoundException{
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("{\n");
        
        relatorio.append("  \"testsDuration\" : {\n");
        for(int i = 0; i < duracaoTestes.length; i++){
            relatorio.append("      \"Test ").append(i).append("\" : ");
            relatorio.append(duracaoTestes[i]);
            if (i != duracaoTestes.length - 1){
                relatorio.append(", \n");
            }
        }
        relatorio.append("\n  },\n");
        
        relatorio.append("  \"memoryStart\" : ");
        relatorio.append(memInicial).append(",\n");
        relatorio.append("  \"memoryEnd\" : ");
        relatorio.append(memFinal).append(",\n");
        
        relatorio.append("  \"errors\" : {\n");
        boolean erroEncontrado = false;
        for (int i = 0; i < erros.length; i++) {
            if (erros[i] != null){
                erroEncontrado = true;
                relatorio.append("      \"Test ").append(i).append("\" : \"");
                relatorio.append(erros[i]);
                relatorio.append("\", \n");
            }
        }
        if (erroEncontrado) {
            relatorio.delete(relatorio.length() - LIMITE_VIRGULA, 
                    relatorio.length());
        }
        
        relatorio.append("\n  }\n}");
        gerarArquivo(relatorio, false);
    }
    /**
     * Gera o arquivo final de acordo com o tipo pedido.
     * @param conteudo conteúdo a ser colocado no arquivo
     * @param isHTML se o arquivo é HTML ou JSON
     * @throws FileNotFoundException 
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
