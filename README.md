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