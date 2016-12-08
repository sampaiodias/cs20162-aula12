/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.ing.cs.sampaiodias.qp;

import br.ufg.inf.cs.sampaiodias.qp.Expressao;
import br.ufg.inf.cs.sampaiodias.qp.QualidadeParser;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

/**
 *
 * @author lucas
 */
public class QualidadeParserTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void mainArgsVazio() {
        exit.expectSystemExitWithStatus(1);
        QualidadeParser.main(new String[0]);
    }

    @Test
    public void mainArgsArquivoWeb() {
        exit.expectSystemExitWithStatus(0);
        QualidadeParser.main(new String[]{"http://pastebin.com/raw/DA1CgBRh"});
    }

    @Test
    public void mainArgsArquivoWebHTML() {
        exit.expectSystemExitWithStatus(0);
        QualidadeParser.main(new String[]
            {"http://pastebin.com/raw/DA1CgBRh", "-h"}); 
    }
    
    @Test
    public void mainArgsArquivoWebErroLinha() {
        exit.expectSystemExitWithStatus(1);
        QualidadeParser.main(new String[]{"http://pastebin.com/raw/XjYMZTFm"});
    }
    
    @Test
    public void mainArgsArquivoWebErroResEsperado() {
        exit.expectSystemExitWithStatus(1);
        QualidadeParser.main(new String[]{"http://pastebin.com/raw/8bnYwaaN"});
    }
    
    @Test
    public void mainArgsArgumentoInvalido() {
        exit.expectSystemExitWithStatus(1);
        QualidadeParser.main(new String[]
            {"htxtp://pastebin.com/raw/DA1CgBRh", "-x"}); 
    }

    @Test
    public void calcular2soma2() {
        String[] varNomes = new String[0];
        Float[] varValores = new Float[0];
        Expressao exp = new Expressao("2+2", 4.0f, varNomes, varValores);
        assertEquals(4.0f, QualidadeParser.executarParser(exp), 0.01f);
    }

    @Test
    public void calcular9div3() {
        String[] varNomes = new String[0];
        Float[] varValores = new Float[0];
        Expressao exp = new Expressao("9/3", 3.0f, varNomes, varValores);
        assertEquals(3.0f, QualidadeParser.executarParser(exp), 0.01f);
    }

    @Test
    public void calcularA() {
        String[] varNomes = new String[0];
        Float[] varValores = new Float[0];
        Expressao exp = new Expressao("A", 0f, varNomes, varValores);
        assertEquals(0.0f, QualidadeParser.executarParser(exp), 0.01f);
    }

    @Test
    public void calcularExpressaoParentesesSomaMult() {
        String[] varNomes = new String[0];
        Float[] varValores = new Float[0];
        Expressao exp = new Expressao("(2+2)*3", 12.0f, varNomes, varValores);
        assertEquals(12.0f, QualidadeParser.executarParser(exp), 0.01f);
    }

    @Test
    public void calcularExpressaoParentesesIncognitas() {
        String[] varNomes = new String[]{"x", "y", "a"};
        Float[] varValores = new Float[]{4f, 3f, 2f};
        Expressao exp = new Expressao("(x+y)*a", 14.0f, varNomes, varValores);
        assertEquals(14.0f, QualidadeParser.executarParser(exp), 0.1f);
    }

    @Test
    public void calcularExpressaoIncognitaE() {
        String[] varNomes = new String[0];
        Float[] varValores = new Float[0];
        Expressao exp = new Expressao("a&b", 0.0f, varNomes, varValores);
        assertEquals(0.0f, QualidadeParser.executarParser(exp), 0.01f);
    }

    @Test
    public void calcularExpressaoIncognitaOU() {
        String[] varNomes = new String[0];
        Float[] varValores = new Float[0];
        Expressao exp = new Expressao("a|b", 0.0f, varNomes, varValores);
        assertEquals(0.0f, QualidadeParser.executarParser(exp), 0.01f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcularDoisOperadoresSemParenteses() {
        String[] varNomes = new String[0];
        Float[] varValores = new Float[0];
        Expressao exp = new Expressao("3+2*4", 11.0f, varNomes, varValores);
        QualidadeParser.executarParser(exp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcularApenasOperador() {
        String[] varNomes = new String[0];
        Float[] varValores = new Float[0];
        Expressao exp = new Expressao("+", 11.0f, varNomes, varValores);
        QualidadeParser.executarParser(exp);
    }

    @Test
    public void tratarArgs1() {
        String[] test = {"1"};
        Assert.assertArrayEquals(new String[]{"ERROR ARGS"},
                QualidadeParser.tratarArgs(test));
    }

    @Test
    public void tratarArgsVazia() {
        String[] test = new String[]{};
        Assert.assertArrayEquals(new String[]{"ERROR ARGS"},
                QualidadeParser.tratarArgs(test));
    }

    @Test
    public void tratarArgsArquivoInvalido() {
        String[] test = new String[]{"invalido.ttttxt"};
        Assert.assertArrayEquals(new String[]{"ERROR ARGS"},
                QualidadeParser.tratarArgs(test));
    }

    @Test
    public void tratarArgsArquivoValido() {
        String[] test = new String[]{"http://pastebin.com/raw/DA1CgBRh"};
        String[] linhas = new String[]{"4+2;;6", "5*x;x=3;15", "8/(4-2);;4",
            "3-4;;-1", "x*y;x=9,y=10;90"};
        Assert.assertArrayEquals(linhas,
                QualidadeParser.tratarArgs(test));
    }

    @Test
    public void gerarVetorTempoValido() {
        long[] teste = new long[]{10, 5, 10, 20};
        Assert.assertArrayEquals(teste,
                QualidadeParser.gerarVetorTempos(10, 20, 2));
    }
}
