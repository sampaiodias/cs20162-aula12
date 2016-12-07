/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.cs.sampaiodias.qp;

import com.github.kyriosdata.parser.Lexer;
import com.github.kyriosdata.parser.Parser;
import com.github.kyriosdata.parser.Token;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String arquivoLocal = "C:/Users/lucas/Documents/teste.txt"; //TEST
        String arquivoRemoto = "https://google.com";                //TEST
        String[] linhas = tratarArgs(new String[]{arquivoLocal});   //TEST - MUDAR PARA ARGS
        Expressao[] expressoes;
        expressoes = new Expressao[linhas.length];
        long tempoInicial;
        long tempoFinal;
        long duracaoEmNanoS;
        long tempoMedioEmNanoS;
        long memInicial;
        long memFinal;

        if (linhas[0].contains("ERROR ARGS")) {
            System.out.println("Erro: Argumentos inválidos");
            System.exit(1);
        }

        memInicial = memoriaJVM();

        tempoInicial = System.nanoTime();

        for (int i = 0; i < linhas.length; i++) {
            try {
                expressoes[i] = gerarExpressao(linhas[i]);
            } catch (Exception e) {
                System.out.println("Erro: Linha inválida no arquivo");
                System.exit(i);
            }
            try {
                expressoes[i].resultadoObtido
                        = executarParser(expressoes[i]);
                System.out.println(expressoes[i].resultadoObtido);
            } catch (Exception e) {
                expressoes[i].mensagemErro = ("Erro: Parse não pode encontrar "
                        + "um resultado para " + expressoes[i].equacao);;
            }
            if ("".equals(expressoes[i].mensagemErro)) {
                if (!expressoes[i].testeAcertou()) {
                    expressoes[i].mensagemErro = ("Erro: Resultado esperado não"
                            + " é igual a resposta recebida pelo Parser");
                }
            }
        }

        memFinal = memoriaJVM();

        tempoFinal = System.nanoTime();
        duracaoEmNanoS = (tempoFinal - tempoInicial);
        tempoMedioEmNanoS = duracaoEmNanoS / expressoes.length;

        long[] tempos = new long[4];
        tempos[0] = tempoInicial;
        tempos[1] = tempoFinal;
        tempos[2] = duracaoEmNanoS;
        tempos[3] = tempoMedioEmNanoS;

        try {
            if (args.length > 1 && args[1].toLowerCase().contains("-h")) {
                Relatorio.gerarHTML(tempos, memInicial, memFinal, expressoes);
            } else {
                Relatorio.gerarJSON(tempos, memInicial, memFinal, expressoes);
            }
        } catch (Exception e) {
            System.out.println("Erro: Falha ao gerar o arquivo de relatório.");
        }
    }
    /**
     * Verifica se os parâmetros iniciais são válidos e formata-os em linhas.
     *
     * @param args Argumentos enviados para execução
     * @return Operações contidas no arquivo enviado ou {"ERROR ARGS"}
     */
    public static String[] tratarArgs(final String[] args) {
        String[] comandos = new String[0];
        if (args.length == 0) {
            System.out.println("Uso: java jar qp.jar enderecoDoArquivo");
            System.out.println("Você pode usar -h ao final para exportar em "
                    + "HTML ao invés de JSON.");
            return new String[]{"ERROR ARGS"};
        } else {
            int i = 0;
            try {
                ArrayList<String> linhas = LeitorArquivo.ler(args[0]);
                comandos = new String[linhas.size()];
                for (String item : linhas) {
                    comandos[i] = item.replace(" ", "");;
                    i++;
                }
                return comandos;
            } catch (Exception e) {
                System.out.println("Erro: Arquivo não pode ser carregado/"
                        + "encontrado.");
                return new String[]{"ERROR ARGS"};
            }
        }
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
    /**
     * Envia a expressão como objeto para o Parser.
     * @param exp Objeto do tipo Expressão com os dados de uma linha do arquivo
     * @return Resultado recebido pelo Parser
     */
    public static float executarParser(final Expressao exp) {
        float resultado = 0;
        if (exp.varsNome.length == 0) {
            List<Token> tokens = new Lexer(exp.equacao).tokenize();
            Parser parser = new Parser(tokens);
            resultado = parser.expressao().valor();
        } else {
            Map<String, Float> ctx = new HashMap<>();
            for (int i = 0; i < exp.varsNome.length; i++) {
                ctx.put(exp.varsNome[i], exp.varsValores[i]);
            }
            List<Token> tokens = new Lexer(exp.equacao).tokenize();
            Parser parser = new Parser(tokens);
            resultado = parser.expressao().valor(ctx);
        }
        return resultado;
    }

    /**
     * Separa cada informação de uma linha.
     *
     * @param linha linha contida no arquivo txt (já em String)
     * @return objeto Expressão com os dados da linha enviada
     */
    public static Expressao gerarExpressao(final String linha) {
        String[] secao = linha.split(";");
        String[] varNome = new String[0];
        Float[] varValor = new Float[0];

        if (!secao[1].isEmpty()) {
            String[] varSeparadas = secao[1].split(",");
            varNome = new String[varSeparadas.length];
            varValor = new Float[varSeparadas.length];
            for (int i = 0; i < varSeparadas.length; i++) { //
                String[] parte = varSeparadas[i].split("=");
                varNome[i] = parte[0];
                varValor[i] = Float.parseFloat(parte[1]);
            }
        }

        return new Expressao(secao[0], Float.parseFloat(secao[2]),
                varNome, varValor);
    }
    /**
     * Calcula quanta memória a Java Virtual Machine está usando (estimativa).
     * @return quantidade de memória empregada pela JVM no momento.
     */
    public static long memoriaJVM() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
