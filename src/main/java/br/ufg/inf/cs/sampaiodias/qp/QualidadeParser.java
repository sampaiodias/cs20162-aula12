/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.cs.sampaiodias.qp;

import com.github.kyriosdata.parser.Lexer;
import com.github.kyriosdata.parser.Parser;
import com.github.kyriosdata.parser.Token;
import java.io.FileNotFoundException;
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

        String arquivoLocal = "C:/Users/lucas/Documents/teste.txt";
        String arquivoRemoto = "https://google.com";
        String[] linhas = tratarArgs(new String[]{arquivoLocal});
        Expressao[] expressoes;
        expressoes = new Expressao[linhas.length];

        try {
            if (linhas[0].contains("ERROR ARGS")) {
                System.out.println("Error: Invalid arguments");
                System.exit(1);
            }
            for (int i = 0; i < linhas.length; i++) {
                try {
                    expressoes[i] = gerarExpressao(linhas[i]);
                } catch (Exception e) {
                    System.out.println("Error: Invalid line!");
                }

                //expressoes[i].resultadoObtido = executarParser(expressoes[i]);
            }
            //System.out.println(executarParser(entrada));
        } catch (Exception e) {
            System.out.println("Error: Parser error!");
        }
//*********************
//        float[] teste = new float[2];
//        teste[0] = 1.2f;
//        teste[1] = 0.1f;
//        String[] testeErro = new String[3];
//        testeErro[0] = null;
//        testeErro[1] = "Error #12";
//        testeErro[2] = "Error #45";
//        try {
//            Relatorio.gerarJSON(teste, 0, 0, testeErro);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(QualidadeParser.class.getName()).log(
//                    Level.SEVERE, null, ex);
//        }
//*******************
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
            System.out.println("Usage: java jar qp.jar fileAddress");
            System.out.println("You may use -h at the end to export in HTML"
                    + " instead of JSON.");
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
                System.out.println("Error: file couldn't be loaded");
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
}
