window.jobs.forEach(job => {
    if (job.status === "Zgłoszono") {
        let audio = document.getElementById("audio");
        audio.play();
    }
});