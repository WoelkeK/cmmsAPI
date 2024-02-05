function rowClicked(value) {
        location.href = "/hardwares/read/" + value;
    }

function rowClickNotifi(value) {
            location.href = "/awizacje/read/" + value;
        }
function rowClickJob(value, pageNo) {

//            console.log("redirect:" + value + "/" + pageNo);

            location.href = "/jobs/update/" + value + "?pageNo="+ pageNo;
        }

