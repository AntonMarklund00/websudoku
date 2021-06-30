let board;

function start(){

    document.getElementById("solve").addEventListener("click", solution);
    axios.get('/get/initial/board')
        .then((response) => {
            console.log(response.data);
            startBoard = response.data;

            displayData();
        });
}

function displayData() {
    for (let i = 0; i < startBoard.length; i++) {
        for (let j = 0; j < startBoard[i].length; j++) {
            document.getElementById("board").innerHTML += "<li><span>"+startBoard[i][j]+"</span></li>"
        } 
    }
}

function solution(){
    axios.post('/get/solved/board', startBoard)
        .then((response) => {
            console.log(response)
            startBoard = response.data;
            document.getElementById("board").innerHTML = "";
            displayData();
        });
}

window.addEventListener("load", start, false);
