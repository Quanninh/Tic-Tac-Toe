For the sake of simplicity, assume that the human always starts first.

In this case, "multi-user" means that several users can play simultaneously against the computer.

Don't use threads.

Don't assume that the players never cheat.
Extra game requirement: The player must make his/her move within 10 seconds after having received the board from the sever.

## Description:
Review the code StatelessServer, StatelessClient, SecureStatelessClient and SecureStatelessServer and based on these ideas, implement a new code that:
1. Server sends the initial board; a nonce, which is a random number; and a timestamp, which is the time that a nonce is generated. Server create a HMAC tag based on these 3 values and assigns this hash value to 3 given values before sending them to client
2. Client receives the plaintext of board, nonce, timestamp, and HMAC tag. Then client extracts a current board from received value and makes a move, which is just a number, based on the current board. Finally, client sends the given the plaintext of board, nonce, timestamp, and HMAC tag and a move back to server
3. Server receives the plaintext of board, nonce, timestamp, and HMAC tag and a move from client. Then, server verifies the HMAC tag. If it is valid, server stores a nonce and a timestamp + 10 seconds in a table. If the timestamp + 10 seconds is overdue, the nonce is removed from the table. In the valid case and finished verification, server extracts a board, makes a human move first, then check conditions, and finally makes a computer move
4. After making a computer move, server sends the plaintext of current board, new nonce, timestamp, and HMAC tag created from current board, new nonce and new timestamp to client
5. Repeat those steps.

## Note: Just generate new codes, don't modify old code. Follow OOP and SOLID principles