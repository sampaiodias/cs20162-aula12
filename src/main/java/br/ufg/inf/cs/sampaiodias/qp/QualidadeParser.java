/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.cs.sampaiodias.qp;

import com.github.kyriosdata.parser.Lexer;
import com.github.kyriosdata.parser.Parser;
import com.github.kyriosdata.parser.Token;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Programa que gera um relatório contendo a qualidade do Parser.
 *
 */
public final class QualidadeParser {

    /**
     * Restringe criação de instância.
     */
    private QualidadeParser() {
        
    }

    /**
     * Ponto de entrada da aplicação.
     *
     * @param args Argumentos necessários para avaliar a qualidade do Parser.
     */
    public static void main(final String[] args) {
        try {
            String[] entrada = tratarArgs(args);
            if (entrada[0].contains("ERROR ARGS")) {
                System.exit(1);
            }
            System.out.println(executarParser(tratarArgs(args)));
        } catch (Exception e) {
            System.out.println("ERROR! Invalid expression!");
            System.exit(1);
        }
    }

    /**
     * Verifica se os parâmetros iniciais são válidos ou se é 'help'.
     *
     * @param args Argumentos enviados para execução
     * @return Argumentos sem alteração (válidos) ou {"ERROR ARGS"}
     */
    public static String[] tratarArgs(final String[] args) {
        if (args.length == 0 || args[0].toUpperCase().contains("HELP")) {
            System.out.println("Usage: java jar Calcular.jar x | y");
            return new String[]{"ERROR ARGS"};
        }
        return args;
    }

    /**
     * Envia os parâmetros formatados (token) para o Parser.
     *
     * @param args Argumentos enviados para execução
     * @return Resultado recebido pelo Parser
     */
    public static String executarParser(final String[] args) {
        String stringToken = "";
        Map<String, Float> ctx = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            try {
                float test = Float.parseFloat(args[i]);
                stringToken += test + " ";
            } catch (Exception e) {
                stringToken += args[i] + " ";
            }
        }

        List<Token> tokens = new Lexer(stringToken).tokenize();

        Parser parser = new Parser(tokens);
        float resultado = parser.expressao().valor(ctx);

        return "" + resultado;
    }
}
