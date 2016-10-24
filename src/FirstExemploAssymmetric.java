import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

//int x = 2
//x<<1;
public class FirstExemploAssymmetric {
	public static final String PATH_CHAVE_PUBLICA = "C:\\Users\\fabri\\Desktop\\chaves\\public.key";
	public static final String PATH_CHAVE_PRIVADA = "C:\\Users\\fabri\\Desktop\\chaves\\private.key";
	
	public static void main(String[] args) {
		
		try {
			if(!verificaSeExisteChaveNoSO()){
				geraChave();
			}
			
			String textoOriginal = JOptionPane.showInputDialog("Digite a mensagem:");
			//criptografar
			FileInputStream fInputPub = new FileInputStream(PATH_CHAVE_PUBLICA);
			ObjectInputStream objInputPub = new ObjectInputStream(fInputPub);
			PublicKey pubKey = (PublicKey) objInputPub.readObject();
			byte[] textoCriptografado = criptografa(textoOriginal, pubKey);
			JOptionPane.showMessageDialog(null,  new String(textoCriptografado));
			
			//descriptografa
			fInputPub = new FileInputStream(PATH_CHAVE_PRIVADA);
			objInputPub = new ObjectInputStream(fInputPub);
			PrivateKey privateKey = (PrivateKey) objInputPub.readObject();
			String textoDecriptado = descriptografa(textoCriptografado, privateKey);
			JOptionPane.showMessageDialog(null, textoDecriptado);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static String descriptografa (byte[] textoCriptografado, PrivateKey key){
		byte[] textoDescriptado = null;
				
			Cipher cipher;
			try {
				cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, key);
				textoDescriptado = cipher.doFinal(textoCriptografado);
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
				e1.printStackTrace();
			}catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
			 return new String(textoDescriptado);
	}
	
	
	public static byte[] criptografa(String texto, PublicKey key){
		byte[] textoCriptografado = null;
		
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			textoCriptografado = cipher.doFinal(texto.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {//criptografia - blocos - fluxo
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return textoCriptografado;
	}
	
	public static boolean verificaSeExisteChaveNoSO(){
		File fileChavePublica = new File(PATH_CHAVE_PUBLICA);
		File fileChavePrivada = new File(PATH_CHAVE_PRIVADA);
		
		if(fileChavePublica.exists() && fileChavePrivada.exists()){
			return true;
		}
		return false;
	}
	
	public static void geraChave(){
		//java.security
		try {
			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
			keyGenerator.initialize(1024);
			
			KeyPair keyPar = keyGenerator.generateKeyPair();
			
			File fileChavePublica = new File(PATH_CHAVE_PUBLICA);
			File fileChavePrivada = new File(PATH_CHAVE_PRIVADA);
			
			if(fileChavePublica.getParentFile() != null){
				fileChavePublica.getParentFile().mkdirs(); //cria extrutura de pastas no diretório colocado
			}
			fileChavePublica.createNewFile();
			
			if(fileChavePrivada.getParentFile() != null){
				fileChavePrivada.getParentFile().mkdirs(); //cria extrutura de pastas no diretório colocado
			}
			fileChavePrivada.createNewFile();
			
			//salva a chave no path
			FileOutputStream fOs = new FileOutputStream(fileChavePublica);
			ObjectOutputStream oStream = new ObjectOutputStream(fOs);
			oStream.writeObject(keyPar.getPublic());
			oStream.close();
			
			fOs = new FileOutputStream(fileChavePrivada);
			oStream = new ObjectOutputStream(fOs);
			oStream.writeObject(keyPar.getPrivate());
			oStream.close();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
