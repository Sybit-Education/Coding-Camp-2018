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