/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.ing.cs.sampaiodias.qp;

import br.ufg.inf.cs.sampaiodias.qp.QualidadeParser;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author lucas
 */
public class QualidadeParserTest {

    @Test
    public void calcular2soma2() {
        String[] test = {"2", "+", "2"};
        assertEquals("4.0", QualidadeParser.executarParser(test));
    }

    @Test
    public void calcular9div3() {
        String[] test = {"9", "/", "3"};
        assertEquals("3.0", QualidadeParser.executarParser(test));
    }

    @Test
    public void calcularA() {
        String[] test = {"A"};
        assertEquals("0.0", QualidadeParser.executarParser(test));
    }

    @Test
    public void calcularExpressaoParentesesSomaMult() {
        String[] test = {"(", "2", "+", "2", ")", "*", "3"};
        assertEquals("12.0", QualidadeParser.executarParser(test));
    }

    @Test
    public void calcularExpressaoParentesesIncognitas() {
        String[] test = {"(", "x", "+", "y", ")", "*", "a"};
        assertEquals("0.0", QualidadeParser.executarParser(test));
    }

    @Test
    public void calcularExpressaoIncognitaE() {
        String[] test = {"aaa", "&", "bbb"};
        assertEquals("0.0", QualidadeParser.executarParser(test));
    }

    @Test
    public void calcularExpressaoIncognitaOU() {
        String[] test = {"aaa", "|", "bbb"};
        assertEquals("0.0", QualidadeParser.executarParser(test));
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcularDoisOperadoresSemParenteses() {
        String[] test = {"3", "+", "2", "*", "4"};
        QualidadeParser.executarParser(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcularApenasOperador() {
        String[] test = {"+"};
        QualidadeParser.executarParser(test);
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
}
