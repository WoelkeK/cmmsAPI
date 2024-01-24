$(document).ready(function() {
    setInterval(function() {
        $.get('/dashboards', function(data) {
            console.log(data);
            window.location.reload();

        });
    }, 100000);
    window.jobs.forEach(job => {
        if (job.status === "zgłoszenie") {
            let audio = document.getElementById("audio");
            audio.play();
        }
    });
});