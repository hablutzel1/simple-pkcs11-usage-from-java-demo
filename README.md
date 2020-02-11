# Usage

First install prerequisites:
- Java 9+
- Maven

Then compile the project with:

```
mvn clean package
```

Then run the demo with:

```
java -jar target\simple-pkcs11-usage-from-java-demo-1.0-SNAPSHOT-jar-with-dependencies.jar "C:\Program Files\SafeNet\Protect Toolkit 5\Protect Toolkit C RT\cryptoki.dll" <SLOT_ID> <SLOT_PASSWORD>
```

With a sample output like the following:

```
Listing current key pairs:
- 9f5afac1-7dda-48fd-9757-8014eadfb233
- signKey
- testKey
- 116f3716-d6a4-4948-834a-4f960e624758
Generating a new prime256v1 EC key with alias dd503010-bd73-4463-84f5-06565f2a522f...
Generating self-signed dummy certificate (required by Java APIs)...
Storing self-signed dummy certificate...
Generating sample SHA256withECDSA signature over the following bytes: 73616D706C652064617461
Signature: 30440220580C139EA35C91E53B782E8EFDE0038051C12B5529C1E41908D824A0E4CBF29C0220510BD92CD37E8B694298BABD6ED578DE1859952F0BB31AC01AD6A94BF2D4C254
Done.
```