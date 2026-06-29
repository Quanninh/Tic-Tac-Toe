# Tic-Tac-Toe
## Test Web Server
Test /start endpoint
```bash
curl http://localhost:8080/tttbasic-1.0-SNAPSHOT/start
START
BOARD:0,0,0,0,0,0,0,0,0% 
```
Test /move endpoint
```bash
curl -X POST http://localhost:8080/tttbasic-1.0-SNAPSHOT/move \
-d "3
BOARD:1,2,0,2,1,2,1,2,1"
```
Test /quit endpoint
```bash
curl http://localhost:8080/tttbasic-1.0-SNAPSHOT/quit 
```

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


