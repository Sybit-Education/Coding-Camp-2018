let Box = require('./Box');

function harbour() {

    let x = 150;
    let y = 100;
    canvas.rect(20, 20, x, y);
    ctx.stroke();
//var habour = "";
    for (let i = 0; i < 10; i++) {

        ctx.rect(20, 20, 150, 100);
        ctx.stroke();
        ctx.x = ctx.x + x;
    }

}


module.exports = class Gamefield {

    constructor(posX, posY, boxPixel, boxXCount, boxYCount, color, field) {
        //TODO sinnvolle Attribute �berlegen
        this.initBoxes();
    }

    initBoxes() {
        let posX = this.posX;
        let posY = this.posY;
        let color = this.color;
        let field = this.field;
        let boxPixel = this.boxPixel;
        //TODO boxen erzeugen
    }

    getBoxes() {
        return this.boxes;
    }

    convertToJSON() {
        let boxJsonArray = [];
        for (let box of this.boxes) {
            let boxJson = box.toJSON();
            boxJsonArray.push(boxJson);
        }
        return "{\"gameField\":[" + boxJsonArray + "]}";
    }

    static convertToNumberingScheme(number) {
        let baseChar = ("A").charCodeAt(0),
            letters = "";
        do {
            letters = String.fromCharCode(baseChar + (number % 26)) + letters;
            number = (number / 26) >> 0; // quick `floor`
        } while (number > 0);
        return letters;
    }
};