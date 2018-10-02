/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifrs.edimarmanica.train;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author Administrador
 */
public class TesteViewWebPages {

    public static void main(String args[]) throws Exception {
        JEditorPane website = new JEditorPane("http://smo-gram.tumblr.com/");
        website.setEditable(false);

        JFrame frame = new JFrame("Google");
        frame.add(new JScrollPane(website));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

}
