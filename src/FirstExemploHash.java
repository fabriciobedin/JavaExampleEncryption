import java.security.MessageDigest;

import javax.swing.JOptionPane;

public class FirstExemploHash {

	public static void main(String[] args) {
		MessageDigest algoritmo; //algoritmo de criptografia
		
		try {
			String textoPuro = JOptionPane.showInputDialog("Digite Algo:");
			
			//forma mais simples... somente hash, porém não é muito usado
			//algoritmo = MessageDigest.getInstance("MD5"); MD5 - menos segurança
			algoritmo = MessageDigest.getInstance("SHA-256"); //SHA-256 - mais segurança
			algoritmo.digest(textoPuro.getBytes("UTF-8"));
			byte[] textoHash = algoritmo.digest(textoPuro.getBytes("UTF-8"));
			JOptionPane.showMessageDialog(null,  new String(textoHash));
									
			//String textoHashHexa = new String();//não mutavel
			StringBuffer textoHashHexa = new StringBuffer(); //mutável - mais correto usar essa
			for(byte b : textoHash){
				textoHashHexa.append(String.format("%02X", 0xff & b)); //o format vai pegar o byte e transformar pra hexadecimal na 0xff
			}
			
			JOptionPane.showMessageDialog(null,  new String(textoHashHexa));
						
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
