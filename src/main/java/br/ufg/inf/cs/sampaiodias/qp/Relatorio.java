/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.cs.sampaiodias.qp;

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
     * Gera arquivo HTML indicando a duração e o tempo médio de cada teste.
     * 
     * @param duracaoTestes duração em ms de cada teste executado
     * @param memInicial memória inicial empregada pela JVM, antes dos testes
     * @param memFinal memória empregadada pela JVM ao terminar os testes
     * @param erros mensagem de erro de cada teste, caso houver
     */
    public static void gerarHTML(final int[] duracaoTestes, 
            final float memInicial, final float memFinal, final String[] erros){
        
    }
    /**
     * Gera arquivo JSON indicando a duração e o tempo médio de cada teste.
     * 
     * @param duracaoTestes duração em ms de cada teste executado
     * @param memInicial memória inicial empregadada pela JVM, antes dos testes
     * @param memFinal memória empregadada pela JVM ao terminar os testes
     * @param erros mensagem de erro de cada teste, caso houver
     */
    public static void gerarJSON(final int[] duracaoTestes, 
            final float memInicial, final float memFinal, final String[] erros){
        
    }
}
