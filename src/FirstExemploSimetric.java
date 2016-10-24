import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class FirstExemploSimetric {
	
	//javax.crypto
	//JCP - Java Community Process - cria uma JSR - Dinheiro social
	public static void main(String[] args) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); //Algoritmo para criptografar e decriptografar
			String textoOriginal = JOptionPane.showInputDialog("Digite a mensagem:");
			byte[] chave = "12345678qwertyui".getBytes(); //128 bits
			
			//criptografia - origem
			cipher.init(cipher.ENCRYPT_MODE, new SecretKeySpec(chave, "AES"));
			byte[] textoCriptografado = cipher.doFinal(textoOriginal.getBytes());
			JOptionPane.showMessageDialog(null,  new String(textoCriptografado));
			
			//decriptografia - destino
			cipher.init(cipher.DECRYPT_MODE, new SecretKeySpec(chave, "AES"));
			byte[] textoDecriptografado = cipher.doFinal(textoCriptografado);
			JOptionPane.showMessageDialog(null,  new String(textoDecriptografado));
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		//CHAVE PRIVADA

	}

}
