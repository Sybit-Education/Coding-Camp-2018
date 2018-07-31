module.exports = {
    connectToMatch: function () {
        //TODO Abfrage um ein neues spiel zu generieren
        let newMatch = new XMLHttpRequest();
        newMatch.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200){
                window.location.href = "/match/"+this.responseText;
            }
        }
        newMatch.open("GET", "match/newmatch", true);
        newMatch.send();
    },

    saveGamefield: function(matchId, message) {
           //TODO Abfrage um das Spielfeld zu speichern
    }
};
