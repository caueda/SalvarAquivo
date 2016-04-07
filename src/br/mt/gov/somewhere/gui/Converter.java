/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mt.gov.somewhere.gui;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author c8757550
 */
public class Converter {
    private static void close(Closeable c){
        try {
            if(c != null){
                c.close();
            }
        } catch(Exception e){
            
        }
    }
    public static void converterParaArquivo(File pasta, String nomeArquivo, String conteudo, final JLabel jLabelTamanho) throws Exception {
        String conteudoTratado = null;
        try {
            conteudoTratado = conteudo.replaceAll("<[^>]*>", "");
        } catch(Exception e){
            conteudoTratado = conteudo;
        }
        byte[] bytes = Base64.decodeBase64(conteudoTratado);
        
        FileOutputStream fous = null;
        File file = null;
        try{
            file = new File(pasta, nomeArquivo);
            if(file.exists()){
                file.delete();
                file.createNewFile();
            }
            fous = new FileOutputStream(file);
            jLabelTamanho.setText(String.valueOf(bytes.length));
            fous.write(bytes);
            fous.flush();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro na conversão", JOptionPane.ERROR_MESSAGE);
        } finally{            
            close(fous);            
        }
    }
    
    public static String converterParaString(File arquivo, final JLabel jLabelTamanho){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(arquivo);
            byte[] bytes = new byte[fis.available()];
            jLabelTamanho.setText(String.valueOf(bytes.length));
            fis.read(bytes);
            return Base64.encodeBase64String(bytes);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro na conversão", JOptionPane.ERROR_MESSAGE);
        } finally{
            close(fis);
        }
        return "Erro na conversão";
    }
}
