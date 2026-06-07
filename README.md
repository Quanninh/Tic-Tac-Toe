# Tic-Tac-Toe

## Compile Server and Client
```bash
jar cfe MultiUserServer.jar vgu.pe2026.ttt.basic.MultiUserServer -C target/classes .
```

```bash
jar cfe HumanClient.jar vgu.pe2026.ttt.basic.HumanClient -C target/classes .
```

## Change the shared secret key for Client and Server
```bash
TTT_HMAC_SECRET=my-secret-key java -jar SecureStatelessServer.jar
```

```bash
TTT_HMAC_SECRET=my-secret-key java -jar SecureStatelessClient.jar
```


