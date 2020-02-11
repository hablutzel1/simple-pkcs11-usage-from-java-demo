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