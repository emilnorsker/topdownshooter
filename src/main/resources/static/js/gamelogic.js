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

    var player_x;
    var player_y;


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

        clientMousePos_x = event.clientX;
        clientMousePos_y = event.clientY;
        console.log(event.clientX + "  " + event.clientY)
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
                context.save()
                let rad = this.angle * Math.PI / 180;
                console.log(this.angle)
                context.translate(this.x, this.y);
                context.rotate(rad);
                context.drawImage(this.sprite, this.x, this.y, this.height, this.width)
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
                            player_x = object.x;
                            player_y = object.y;
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
        playerDir = clientMousePos_x;

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
                playerDirection: getAngleDegrees(player_x, player_y, clientMousePos_x, clientMousePos_y, true),
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
        console.log(degrees)
        return degrees;
    }



    stylePaddingLeft = parseInt(document.defaultView.getComputedStyle(canvas, null)['paddingLeft'], 10)      || 0;
    stylePaddingTop  = parseInt(document.defaultView.getComputedStyle(canvas, null)['paddingTop'], 10)       || 0;
    styleBorderLeft  = parseInt(document.defaultView.getComputedStyle(canvas, null)['borderLeftWidth'], 10)  || 0;
    styleBorderTop   = parseInt(document.defaultView.getComputedStyle(canvas, null)['borderTopWidth'], 10)   || 0;
    // Some pages have fixed-position bars (like the stumbleupon bar) at the top or left of the page
    // They will mess up mouse coordinates and this fixes that
    var html = document.body.parentNode;
    htmlTop = html.offsetTop;
    htmlLeft = html.offsetLeft;

    function getMouse(e, canvas) {
    var element = canvas, offsetX = 0, offsetY = 0, mx, my;

    // Compute the total offset. It's possible to cache this if you want
    if (element.offsetParent !== undefined) {
        do {
            offsetX += element.offsetLeft;
            offsetY += element.offsetTop;
        } while ((element = element.offsetParent));
    }

    // Add padding and border style widths to offset
    // Also add the <html> offsets in case there's a position:fixed bar (like the stumbleupon bar)
    // This part is not strictly necessary, it depends on your styling
    offsetX += stylePaddingLeft + styleBorderLeft + htmlLeft;
    offsetY += stylePaddingTop + styleBorderTop + htmlTop;

    mx = e.pageX - offsetX;
    my = e.pageY - offsetY;

    // We return a simple javascript object with x and y defined
    return {x: mx, y: my};
    }
}