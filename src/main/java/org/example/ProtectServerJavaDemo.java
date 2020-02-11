package org.example;//import sun.security.pkcs11.SunPKCS11;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.ECGenParameterSpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

public class ProtectServerJavaDemo {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: java -jar target\\simple-pkcs11-usage-from-java-demo-1.0-SNAPSHOT-jar-with-dependencies.jar \"C:\\Program Files\\SafeNet\\Protect Toolkit 5\\Protect Toolkit C RT\\cryptoki.dll\" <SLOT_ID> \"<SLOT_PASSWORD>\"");
            return;
        }
        String pkcs11ModulePath = args[0];
        String slotId = args[1];
        String pin = args[2];

        // Configuring PKCS #11 provider.
        Provider sunPKCS11Provider = Security.getProvider("SunPKCS11");
        pkcs11ModulePath = new File(pkcs11ModulePath).getAbsolutePath().replaceAll("\\\\", "/");
        String inlineConfig = "--name = protectserver\n" +
                "library = \"" + pkcs11ModulePath + "\"\n" +
                "slot=" + slotId + "\n" +
                "attributes(generate, CKO_PRIVATE_KEY,*) = {  \n" +
                "       CKA_TOKEN = true  \n" +
                "       CKA_PRIVATE = true  \n" +
                "       CKA_SENSITIVE = true  \n" +
                "       CKA_EXTRACTABLE = false  \n" +
                "       CKA_DECRYPT = false  \n" +
                "       CKA_SIGN = true  \n" +
                "       CKA_UNWRAP = false\n" +
                "    }";
        sunPKCS11Provider = sunPKCS11Provider.configure(inlineConfig);
        Security.addProvider(sunPKCS11Provider);

        // Loading PKCS #11 based KeyStore
        KeyStore pkcs11Keystore = KeyStore.getInstance("PKCS11", sunPKCS11Provider);
        pkcs11Keystore.load(null, pin.toCharArray());

        System.out.println("Listing current key pairs:");
        Enumeration<String> aliases = pkcs11Keystore.aliases();
        while (aliases.hasMoreElements()) {
            String s = aliases.nextElement();
            System.out.println("- " + s);
        }

        String randomAlias = UUID.randomUUID().toString();
        System.out.println("Generating a new secp256r1 EC key with alias " + randomAlias + "...");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", sunPKCS11Provider);
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
        KeyPair generatedKeyPair = keyPairGenerator.generateKeyPair();

        System.out.println("Generating self-signed dummy certificate (required by Java APIs)...");
        X500Name dn = new X500Name("CN=" + randomAlias + " dummy cert");
        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(dn, BigInteger.ONE, new Date(), new Date(), dn, generatedKeyPair.getPublic());
        X509CertificateHolder generatedCertificate = certBuilder.build(new JcaContentSignerBuilder("SHA256withECDSA").build(generatedKeyPair.getPrivate()));

        System.out.println("Storing self-signed dummy certificate...");
        pkcs11Keystore.setEntry(randomAlias, new KeyStore.PrivateKeyEntry(generatedKeyPair.getPrivate(), new Certificate[]{CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(generatedCertificate.getEncoded()))}), null);

        byte[] tbs = "sample data".getBytes();
        String signatureAlg = "SHA256withECDSA";
        System.out.println("Generating sample " + signatureAlg + " signature over the following bytes: " + bytesToHex(tbs));
        Signature signature = Signature.getInstance(signatureAlg);
        signature.initSign(generatedKeyPair.getPrivate());
        signature.update(tbs);
        byte[] signedBytes = signature.sign();
        System.out.println("Signature: " + bytesToHex(signedBytes));

        System.out.println("Done.");
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
