function rowClickHard(value, pageNo) {
        location.href = "/hardwares/read/" + value;
    }

function rowClickNotifi(value, pageNo) {
            location.href = "/awizacje/read/" + value+ "?pageNo="+ pageNo;
        }
function rowClickJob(value, pageNo) {

            location.href = "/jobs/update/" + value + "?pageNo="+ pageNo;
        }

