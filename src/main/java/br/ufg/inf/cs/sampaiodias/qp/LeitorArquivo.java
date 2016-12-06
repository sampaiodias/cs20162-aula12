/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.cs.sampaiodias.qp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author lucas
 */
public class LeitorArquivo {
    /**
     * Restringe criação de instância.
     */
    private LeitorArquivo() {
        
    }

    public static ArrayList<String> ler(final String caminho) 
            throws FileNotFoundException, MalformedURLException, IOException {
        try {
            ArrayList<String> linhas = new ArrayList<>();
            Scanner leitor = new Scanner(new FileReader(caminho));
            while (leitor.hasNextLine()) {
                linhas.add(leitor.nextLine());
            }
            //leitor.close();

            return linhas;
        } catch (Exception e) {
            URL oracle = new URL(caminho);
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));) {
                ArrayList<String> tests = new ArrayList();

                String line = br.readLine();

                while (line != null) {
                    tests.add(line);
                    line = br.readLine();
                }

                br.close();
                return tests;
            }
        }
    }
}
