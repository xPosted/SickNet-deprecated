/**
 * Created by root on 07.10.16.
 */
var socket;
$(function() {
    window.onload = init;
    socket = new WebSocket("ws://localhost:8080/Sors_JSFDeveloping-1.0-SNAPSHOT/webSocketHandler");
    socket.onmessage = onMessage;
});


function onMessage(event) {
    var inputMsg = JSON.parse(event.data);
    if (inputMsg.action === "getId") {
        var hiddenInput = document.getElementById("sessionId");
        var jsonSessionId = {
            id: hiddenInput.value
        };
        socket.send(JSON.stringify(jsonSessionId));
    }
    if (inputMsg.action === "update") {
        if (inputMsg.target === "subnet") {
            console.log("update subnet");
            $(".updateSubnetInfoBtn").click();
        }
        if (inputMsg.target === "ip") {
            console.log("update ip");
            $(".updateIpInfoBtn").click();
        }
    }


}

function init() {

}