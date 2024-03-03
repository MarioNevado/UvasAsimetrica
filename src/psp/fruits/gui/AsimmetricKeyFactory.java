package psp.fruits.gui;




import java.io.*;
import java.security.*;
import psp.fruits.tools.Configuration;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author mario
 */
public class AsimmetricKeyFactory implements Serializable {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
    
    

    public static void main(String[] args) {
        ObjectOutputStream oos = null;
        File publicFile, privateFile;
        AsimmetricKeyFactory privateKey;
        AsimmetricKeyFactory publicKey;
        KeyPairGenerator keyGen;
        KeyPair par;
        try {
            privateKey = publicKey = new AsimmetricKeyFactory();
            keyGen = KeyPairGenerator.getInstance(Configuration.ASSIMMETRIC_ALGORYTHM);
            keyGen.initialize(2048);
            par = keyGen.generateKeyPair();
            privateKey.setPrivateKey(par.getPrivate());
            publicKey.setPublicKey(par.getPublic());
            publicFile = new File(Configuration.PUBLIC_KEY_FILE);
            oos = new ObjectOutputStream(new FileOutputStream(publicFile));
            oos.writeObject(publicKey);
            oos.close();
            privateFile = new File(Configuration.PRIVATE_KEY_FILE);
            oos = new ObjectOutputStream(new FileOutputStream(privateFile));
            oos.writeObject(privateKey);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage() + "a");
        } catch (IOException ex) {
            System.out.println(ex.getMessage() + "b");
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
