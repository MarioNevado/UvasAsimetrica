package psp.fruits.tools;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import javax.crypto.SecretKey;
import psp.fruits.gui.SimmetricKeyFactory;
import psp.fruits.gui.AsimmetricKeyFactory;

/**
 *
 * @author dev
 */
public class Utils {


    public static byte[] fileToByteArray(String origin) throws Exception {
        File file = new File(origin);
        byte[] byteArray = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(byteArray);
        } catch (Exception e) {
            throw e;
        }
        return byteArray;
    }

    public static void byteArrayToFile(String goal, byte[] byteArray) throws Exception {
        File fichero = new File(goal);
        try (FileOutputStream fos = new FileOutputStream(fichero)) {
            fos.write(byteArray);
            fos.flush();
        } catch (Exception e) {
            throw e;
        }
    }

    public static byte[] getBytes(File file) {
        byte[] buffer;
        long bytes;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            bytes = file.length();
            buffer = new byte[(int) bytes];
            int pointer, counter = 0;
            while ((pointer = fis.read()) != -1) {
                buffer[counter] = (byte) pointer;
                counter++;
            }
            return buffer;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                System.err.println("ERROR al cerrar el inputstream");
            }
        }
        return null;
    }

    public static String getHash(String algorythm, File file) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorythm);
            md.update(getBytes(file));
            return hex(md.digest());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String hex(byte[] resumen) {
        String hex = "", h;
        for (int i = 0; i < resumen.length; i++) {
            h = Integer.toHexString(resumen[i] & 0xFF) + ":";
            if (h.length() == 1) {
                hex += "0";
            }
            hex += h;
        }
        return hex;
    }

    public static void saveEncryptedFile(byte[] data) {
        File encryptedFile;
        BufferedOutputStream bos = null;
        try {
            encryptedFile = new File(Configuration.ENCRYPTED_FILENAME);
            bos = new BufferedOutputStream(new FileOutputStream(encryptedFile));
            bos.write(data);
            bos.flush();

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado");
        } catch (IOException ex) {
            System.out.println("Error I/O");

        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                System.out.println("Error I/O");
            }
        }
    }

    public static void saveDecryptedData(byte[] data) {
        File decryptedFile;
        BufferedOutputStream bos = null;
        try {
            decryptedFile = new File(Configuration.DECRYPTED_FILENAME);
            bos = new BufferedOutputStream(new FileOutputStream(decryptedFile));
            bos.write(data);
            bos.flush();
        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado");
        } catch (IOException ex) {
            System.out.println("Error I/O");
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                System.out.println("Error I/O");
            }
        }
    }

    public static SecretKey getKey(String route) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(route))) {
            SimmetricKeyFactory keygen = (SimmetricKeyFactory) ois.readObject();
            return keygen.getClave();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] cifrarClaveSimetrica(byte[] data, SecretKey key) {
        Cipher c;
        try {
            c = Cipher.getInstance(Configuration.SIMMETRIC_TRANSFORMATION);
            c.init(Cipher.ENCRYPT_MODE, key);
            return c.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] descifrarClaveSimetrica(byte[] data, SecretKey key) {
        Cipher c;
        try {
            c = Cipher.getInstance(Configuration.SIMMETRIC_TRANSFORMATION);
            c.init(Cipher.DECRYPT_MODE, key);
            return c.doFinal(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] encryptDataPrivateKey(byte[] data, PrivateKey clavePrivada) {
        byte[] encryptedData;
        try {
            Cipher c = Cipher.getInstance(Configuration.ASSIMMETRIC_ALGORYTHM);
            c.init(Cipher.ENCRYPT_MODE, clavePrivada);
            encryptedData = c.doFinal(data);
            saveEncryptedFile(encryptedData);
            return encryptedData;
        } catch (InvalidKeyException ex) {
            System.out.println("Clave no valida");
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static byte[] decryptDataPublicKey(byte[] encryptedData, PublicKey publicKey) {
        File encryptedFile;
        byte[] data, decryptedData;
        try {
            encryptedFile = new File(Configuration.ENCRYPTED_FILENAME);
            Cipher c = Cipher.getInstance(Configuration.ASSIMMETRIC_ALGORYTHM);
            c.init(Cipher.DECRYPT_MODE, publicKey);
            data = getBytes(encryptedFile);
            decryptedData = c.doFinal(data);
            saveDecryptedData(decryptedData);
            return decryptedData;
        } catch (InvalidKeyException ex) {
            System.out.println("Clave no valida");
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            System.out.println(ex.getMessage());
        } 
        return null;
    }
    
    public static PublicKey getPublicKey(String route) throws Exception {
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(route))) {
            AsimmetricKeyFactory key = (AsimmetricKeyFactory) oos.readObject();
            return key.getPublicKey();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static PrivateKey getPrivateKey(String route) throws Exception {
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(route))) {
            AsimmetricKeyFactory key = (AsimmetricKeyFactory) oos.readObject();
            return key.getPrivateKey();
        } catch (Exception ex) {
            throw ex;
        }
    }

}
