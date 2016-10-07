/**
 * Created by root on 07.10.16.
 */
window.onload = init;
var socket = new WebSocket("ws://localhost:8080/Sors_JSFDeveloping-1.0-SNAPSHOT/webSocketHandler");
socket.onmessage = onMessage;

function onMessage(event) {
   window.alert("niga");
    var hiddenInput = document.getElementById("sessionId");
    socket.send(hiddenInput.value);
}


function init() {

}