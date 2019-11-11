# Tichuana Tichu Messaging Protocol

The Tichuana Tichu Messaging Protocol (TTMP) specifies the messages exchanged among server and client in the Tichuana 
Tichu application. This protocol uses the Javascript Object Notation (JSON) to structure it's message content. 

## Joining the server

The connection between client and server is established by the client by sending a join request. This request contains
the players name and the password for the associated account. Once received, the server will check the accuracy of the
details. It answers with a "conected message" that contains a boolean status. If true, the details were correct and the 
connection has been established. False means the client will have to send another request with proper details.
The client will now wait for instructions from the server.

### Example

Request sent by client

```json
{"msg":"JoinMsg","password":"pw123","playerName":"player1"}
```

Answer sent by server
```json
{"msg":"ConnectedMsg","status":"true"}
```

## Starting the game

Once four players have connected successfully, the server will automatically start the game. It will send a custom 
"Game started message" to each player, containing the players teammate and his opponents.

```json
{"msg":"GameStartedMsg","opponents":["Christian","Philipp"],"teamMate":"Dominik"}
```

This message is followed up by a custom "deal message" for every player. It will deal the first eight every player in 
game. The server will then wait for a response from each client.
 
```json
{"msg":"DealMsg","cards":[{"rank":"nine","suit":"Pagodas"},{"rank":"Queen","suit":"Jade"},{"rank":"three","suit":"Pagodas"},{"rank":"Jack","suit":"Jade"},{"rank":"two","suit":"Jade"},{"rank":"nine","suit":"Stars"},{"rank":"seven","suit":"Jade"},{"rank":"five","suit":"Jade"}]}
```

The client will then have to decide whether he wants to announce Grand Tichu or not. He will declare so by sending a
"TichuMsg" containing a "TichuType" of either "GrandTichu" or "none".
 
```json
{"msg":"TichuMsg","tichuType":"none","playerName":"player4"}
```

The server will broadcast each announced Tichu to the other players.

```json
{"msg":"AnnouncedTichuMsg","tichuType":"none","player":"player4"}
```

Once the server has received and broadcasted all four Tichus it will deal the remaining six cards to all players with 
another "deal message"

```json
{"msg":"DealMsg","cards":[{"rank":"Queen","suit":"Stars"},{"rank":"six","suit":"Pagodas"},{"rank":"Ace","suit":"Pagodas"},{"rank":"four","suit":"Swords"},{"rank":"ten","suit":"Stars"},{"rank":"nine","suit":"Swords"}]}
```

Again the clients all answer with a "tichu message", this time announcing "SmallTichu" or "none".

```json
{"msg":"TichuMsg","tichuType":"SmallTichu","playerName":"player4"}
``` 

The server will broadcast every Tichu and upon receiving all answers will go on to the next stage: schupfen

```json
{"msg":"AnnouncedTichuMsg","tichuType":"SmallTichu","player":"player2"}
``` 

## Schupfen

When all Tichu messages have been recieved, the server will broadcast a request to all clients prompting them to give one
of their cards to the first Player in the row. 

```json
{"msg":"DemandSchupfenMsg","playerName":"player1"}
```

Each player will check if he is the player in question, if so the request can be ignored. Otherwise the player is to 
choose of of his cards and send it back in a "schupfen message". Again specifying the player the card is meant for.

```json
{"msg":"SchupfenMsg","playerName":"player1","card":{"rank":"Queen","suit":"Pagodas"}}
```

When the server has received three messages he will sent the next "demand schupfen message", this time requesting a card
for the next player in the order. This process is repeated until all players have been given their three cards.

## Gameplay

TODO