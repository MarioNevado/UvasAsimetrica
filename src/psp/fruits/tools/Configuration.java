package psp.fruits.tools;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author dev
 */
public class Configuration {
    public final static String HOST = "localhost",
            DOWNLOAD_ROUTE = "/home/dev/NetBeansProjects/Criptografía/Descifrado_", 
            HASH_ALGORYTHM = "SHA-512", 
            SIMMETRIC_KEY_FILE = "simmetricKey.key",
            PRIVATE_KEY_FILE = "private.key",
            PUBLIC_KEY_FILE = "public.key",
            ENCRYPTED_FILENAME = "Cifrado_file.txt",
            DECRYPTED_FILENAME = "Descifrado_file.txt",
            SIMMETRIC_TRANSFORMATION ="AES/ECB/PKCS5Padding",
            ASSIMETRIC_TRANSFORMATION = "RSA/ECB/PKCS1Padding",
            SIMMETRIC_ALGORYTHM = "AES",
            ASSIMMETRIC_ALGORYTHM = "RSA";
    public final static int SEND_PORT = 6666;
    public final static int RECIEVE_PORT = 6667;
    public final static int SEND_STATS = 6670;
    public final static int RECIEVE_STATS = 6669;
}
