 package org.example;

 import java.security.KeyStore;
 import java.security.PrivateKey;
 import java.security.Signature;
 import java.util.Arrays;
 import java.util.Date;
 import java.util.Timer;
 import java.util.TimerTask;

 public class ProtectServerConnectionRecoveryDemo {

     public static void main(String[] args) {
         SunPKCS11Util.installSunPKCS11Provider();
         // Trying to perform a signature every 5 seconds.
         Timer timer = new Timer();
         timer.scheduleAtFixedRate(new TimerTask() {
             @Override
             public void run() {
                 try {
                     KeyStore keyStore = KeyStore.getInstance("PKCS11");
                     keyStore.load(null, "12345678".toCharArray());
                     PrivateKey privateKey = (PrivateKey) keyStore.getKey("myeckey", null);
                     Signature signature = Signature.getInstance("SHA256withECDSA");
                     signature.initSign(privateKey);
                     signature.update("data".getBytes());
                     byte[] signedBytes = signature.sign();
                     System.out.println(new Date() + ", signature: " + Arrays.toString(signedBytes));
                 } catch (Exception e) {
                     // NOTE that if we arrive here it will be required to retry the signature operation later.
                     // TODO IMPORTANT: log this full exception stack trace as it will be helpful to diagnose problems when interacting with the HSM.
                     e.printStackTrace();
                 }
             }
         }, 0,5000);
     }
 }
