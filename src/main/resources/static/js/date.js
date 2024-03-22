function updateClock() {
    var now = new Date();
    var hours = now.getHours().toString().padStart(2, "0");
    var minutes = now.getMinutes().toString().padStart(2, "0");
    var seconds = now.getSeconds().toString().padStart(2, "0");

    document.getElementById('time').innerHTML = hours + ":" + minutes + ":" + seconds;

    var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    document.getElementById('date').innerHTML = now.toLocaleDateString('pl-PL', options);
}

updateClock();
setInterval(updateClock, 1000); // Update every second