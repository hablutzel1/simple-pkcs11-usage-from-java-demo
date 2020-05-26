# simple-protectserver-pkcs11-usage-from-java-demo

## Dependencies

Last time tested with java version "1.8.0_251".

## Demos

Modify and run the demos which are expected to produce output like the shown below.

### org.example.ProtectServerJavaDemo

```
SunPKCS11 provider installed (should be done only once for the entire application).
Listing current entries in SunPKCS11 KeyStore:
- 9f5afac1-7dda-48fd-9757-8014eadfb233
- signKey
- testKey
- 116f3716-d6a4-4948-834a-4f960e624758
Generating a new secp256r1 EC key with alias 67662f68-ded1-47e6-bbbe-6a1b38110483...
Generating self-signed dummy certificate (required by Java APIs)...
Storing self-signed dummy certificate...
Generating sample SHA256withECDSA signature over the following bytes: 73616D706C652064617461 ...
Signature: 3045022100E74BE7D4865670B9E2ADA377FC0C088EF36A03F5BFFFFCF778082DEB1FD0614602203FB16E20B02601B0107FD55FADC0387A906A066F897FADBF15163BA393175DAD
Verifying signature...
Signature verified correctly.
Done.
```

### org.example.ProtectServerConnectionRecoveryDemo

First, set the following PTKC configuration items (e.g. by setting them as environment variables):

- ET_PTKC_GENERAL_LIBRARY_MODE=HA
- ET_PTKC_WLD_SLOT_0=slot0
- ET_PTKC_HA_RECOVER_DELAY=1
- ET_PTKC_HA_RECOVER_WAIT=YES

```
SunPKCS11 provider installed (should be done only once for the entire application).
Tue May 26 11:41:33 COT 2020, signature: [48, 70, 2, 33, 0, -11, -26, 125, -123, 45, 68, -121, -78, 75, -28, 43, 4, -45, 59, -9, -82, -6, 1, 57, 36, 100, -114, -57, 102, -116, 45, -31, -109, -38, 79, 55, -91, 2, 33, 0, -85, 101, -79, -80, 119, 69, -11, 105, -117, -11, 44, 106, -116, 73, -78, -125, 54, -74, -110, 102, -80, -24, 93, 50, 32, 4, 4, -123, 108, -83, -21, 102]
java.io.IOException: load failed
	at sun.security.pkcs11.P11KeyStore.engineLoad(P11KeyStore.java:778)
	at java.security.KeyStore.load(KeyStore.java:1445)
	at org.example.ProtectServerConnectionRecoveryDemo$1.run(ProtectServerConnectionRecoveryDemo.java:23)
	at java.util.TimerThread.mainLoop(Timer.java:555)
	at java.util.TimerThread.run(Timer.java:505)
Caused by: sun.security.pkcs11.wrapper.PKCS11Exception: CKR_TOKEN_NOT_PRESENT
	at sun.security.pkcs11.wrapper.PKCS11.C_GetAttributeValue(Native Method)
	at sun.security.pkcs11.P11KeyStore.mapLabels(P11KeyStore.java:2324)
	at sun.security.pkcs11.P11KeyStore.engineLoad(P11KeyStore.java:770)
	... 4 more
Tue May 26 12:02:18 COT 2020, signature: [48, 69, 2, 32, 13, -50, 36, -20, 58, -24, -74, -45, -73, 120, -101, 21, 97, -45, 79, 120, -44, 22, 77, -110, -6, -111, -43, -27, -88, 47, -77, -93, 35, 108, 62, -27, 2, 33, 0, -100, 105, 32, 2, 67, -104, 103, -95, 119, 94, 99, 114, -66, -66, -76, -52, 34, -38, 118, 63, 94, -37, -40, -13, 30, 26, -56, 105, -91, -107, 63, 84]
Tue May 26 12:02:41 COT 2020, signature: [48, 70, 2, 33, 0, -105, -69, -32, 5, 106, -80, 109, -111, 19, 30, -15, -32, -10, -32, 121, -22, 29, -32, -28, -118, -100, 26, -125, -103, -125, -8, 70, -72, 77, -71, 23, -29, 2, 33, 0, -51, -38, -83, 77, 2, 5, -99, 94, -31, 16, 84, -40, -122, 96, 120, -5, 72, -64, 119, 102, 79, -18, 113, -19, -73, -47, 16, -90, -12, -107, -60, 25]
Tue May 26 12:03:04 COT 2020, signature: [48, 68, 2, 32, 61, 89, 94, -92, -23, -122, -71, 116, -58, 126, -6, 35, 29, 56, -88, -107, 9, -87, 13, 117, -7, 118, 33, -116, -19, 91, 54, 49, 15, 35, 4, -7, 2, 32, 15, 23, -47, 93, -28, -74, -48, 19, -111, 114, 101, -26, 91, -13, -102, 92, 5, -48, 43, -6, 14, 38, -16, -123, 108, -8, -71, 91, -123, 23, -4, -55]
```