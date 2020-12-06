{
    const canvas = document.getElementById('canvas');
    const context = canvas.getContext('2d');
    context.imageSmoothingEnabled = false;
    let stompClient = null;
    document.onload = function () {
        var playerId = document.getElementById("clientId").value;
    }
    let clientMousePos_x;
    let clientMousePos_y;
    let clientLeftMouseDown;
    let clientLastpressedKey;
    var player;
    const playerId = document.getElementById("clientId").innerHTML;


    document.onkeydown = function (event) {
        clientLastpressedKey = event.key;
    }
    document.onkeyup = function (event) {
        if (clientLastpressedKey == event.key) {
            clientLastpressedKey = null;
        }
    }
    document.onmousedown = function (event) {
        clientLeftMouseDown = true;
    }
    canvas.addEventListener('mousemove', (event) => {
        clientMousePos_x = event.clientX - (canvas.offsetLeft - window.pageXOffset);
        clientMousePos_y = event.clientY - (canvas.offsetTop - window.pageYOffset);
    })


    class gameObject {
        sprite;
        width;
        height;
        x;
        y;
        angle;

        constructor(sprite_location, position_x, position_y, height, width, angle) {
            this.sprite = new Image();
            this.sprite.src = sprite_location;
            this.height = height;
            this.width = width;
            this.x = position_x;
            this.y = position_y;
            this.angle = angle;
            this.draw = function () {
                context.save();
                context.translate(this.x-(width/2),this.y-((height/2)));
                context.rotate((this.angle-90) * Math.PI/180.0);
                console.log(-this.angle * Math.PI/180.0)
                context.translate(-this.x-(width/2), -this.y-(height/2));
                context.drawImage(this.sprite,this.x,this.y,this.width, this.height);
                context.restore();
            }
        }
    }

    function connect() {
        if (stompClient == null) {
            console.log("attempting connection with client id : " + playerId);
            let socket = new SockJS("endpointOne");
            stompClient = Stomp.over(socket); //streaming text oriented messagin protocol (STOMP)
            stompClient.connect({}, function (frame) { // callback hvis connection virker || {} er et javascript object
                console.log("connection succefull");
                stompClient.debug = null;
                stompClient.send("/createPlayer", {}, JSON.stringify({message: playerId})); // tells the server tht a new player a connected
                stompClient.subscribe("/topic/update", function (GameInfo) { // subscriber to the path we defined in home controller

                    let objects = JSON.parse(GameInfo.body).gameObjects;
                    let gameObjects = [];
                    objects.forEach((object) => {
                        let gameComponent = new gameObject(object.sprite_location, object.x, object.y, object.height, object.width, object.angle);
                        if (object.playerId == playerId) {
                            player = gameComponent;
                        }
                        gameObjects.push(gameComponent);
                    });

                    update(gameObjects);
                })
            })
        }
    }

    function sendInputs() {
        let moveDir = [0, 0]; // player movement direction
        let playerDir = 0; // player direction in degrees
        playerDir = 0;

        if (clientLastpressedKey != null) {

            switch (clientLastpressedKey.toLowerCase()) {
                case "w": {
                    moveDir[1] = -1;
                    break
                }
                case "s": {
                    moveDir[1] = 1;
                    break
                }
                case "a": {
                    moveDir[0] = -1;
                    break
                }
                case "d": {
                    moveDir[0] = 1;
                    break
                }
                default: {
                    break
                }
            }
        }


        stompClient.send("/input", {}, JSON.stringify(
            {
                moveDirection: moveDir,
                isMouseDown: clientLeftMouseDown,
                playerDirection: getAngleDegrees(player.x, player.y, clientMousePos_x, clientMousePos_y),
                playerId: playerId,
            }
        ))
        clientLeftMouseDown = false;
    }

    function update(gameObjects) {
        context.clearRect(0, 0, canvas.width, canvas.height)

        gameObjects.forEach((object) => {
            object.draw()
        })

        sendInputs()
        // todo send inputs
    }


    function getAngleDegrees(fromX, fromY, toX, toY, force360 = true) {
        let deltaX = fromX - toX;
        let deltaY = fromY - toY; // reverse
        let radians = Math.atan2(deltaY, deltaX)
        let degrees = (radians * 180) / Math.PI - 90; // rotate
        if (force360) {
            while (degrees >= 360) degrees -= 360;
            while (degrees < 0) degrees += 360;
        }
        console.log("my x = "+fromX+"   their x="+toX)
        console.log(degrees)
        return degrees;
    }

}