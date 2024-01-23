document.addEventListener("DOMContentLoaded", function() {
    var liElements = document.querySelectorAll("li");

    liElements.forEach(function(liElement) {
        var jobJson = liElement.getAttribute("data-job");
        if(jobJson) {
            var job = JSON.parse(jobJson);
            // Now 'job' is an object, you can pass it to your function:
            createJob(job);
        }
    });
});

function createJob(job) {
    console.log("Post Create()")
    fetch('/api/jobs/create', {

        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(job)
    })
        .then(response => response.json())
        .then(data => {
            //Możesz tutaj zaktualizować swoją stronę, na przykład dodać nowe zadanie do listy
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}