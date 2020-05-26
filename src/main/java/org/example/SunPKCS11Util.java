package org.example;

import sun.security.pkcs11.SunPKCS11;

import java.io.ByteArrayInputStream;
import java.security.Security;

public class SunPKCS11Util {

    static void installSunPKCS11Provider() {
        // Configuring PKCS #11 provider.
        String config = "name = protectserver\n" +
                "library = C:/Windows/System32/cryptoki.dll\n" +
                // "library = /opt/safenet/protecttoolkit5/ptk/lib/libcryptoki.so\n" +
                "slot=0\n" +
                "attributes(generate, CKO_PRIVATE_KEY,*) = {  \n" +
                "       CKA_TOKEN = true  \n" +
                "       CKA_PRIVATE = true  \n" +
                "       CKA_SENSITIVE = true  \n" +
                "       CKA_EXTRACTABLE = false  \n" +
                "       CKA_DECRYPT = false  \n" +
                "       CKA_SIGN = true  \n" +
                "       CKA_UNWRAP = false\n" +
                "    }";
        SunPKCS11 sunPKCS11Provider = new SunPKCS11(new ByteArrayInputStream(config.getBytes()));
        Security.addProvider(sunPKCS11Provider);
        // TODO confirm if the creation of multiple instances of SunPKCS11 is guaranteed to generate memory leaks.
        System.out.println("SunPKCS11 provider installed (should be done only once for the entire application).");
    }

}
