function urlValidate(){
    let urls = document.getElementById("url_control").value;
    if(urls==="") {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Lütfen en az bir tane Url Girerek İşlem yapınız.!'
        })
    }
    else{
        emptyTable();
        fetch('http://localhost:8080/urlvalidate', {
            method: 'POST',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "POST, GET",
            },
            body: JSON.stringify(urls)
        }).then(res => res.json())
            .then(data => {
                const resultValidate = document.getElementById("resultValidate");
                let html = `<table class="table caption-top table-hover table-bordered" style="border: black 2px solid !important;"> 
                      <thead><tr>
                      <th>S.NO</th>
                      <th>Url</th>
                      <th>Durumu</th>
                      <th>Hatalar</th></tr></thead><tbody>`;
                for(url of data.urlValidates){
                    if(url.isvalid === true && url.errors==null){
                        html+= `<tr class="alert-success">
                        <td>${url.id}</td>
                       <td>${url.urlString}</td>
                       <td>Doğru</td>
                       <td></td></tr>`;
                    }
                    else if(url.isvalid===false && url.errors==null){
                        html+= `<tr class="alert-danger">
                        <td>${url.id}</td>
                       <td>${url.urlString}</td>
                       <td>Yanlış</td>
                       <td></td>
                       </tr>`;
                    }
                    else{
                        html+= `<tr class="alert-danger">
                        <td>${url.id}</td>
                       <td>${url.urlString}</td>
                       <td>Yanlış</td>
                       <td><ul>${url.errors.map(error => `<li>${error}</li>`)}</ul></td>
                       </tr>`;
                    }

                }
                html += `</tbody></table>`;
                resultValidate.innerHTML=html;
            }).catch(error => {
                console.log(error);
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Hata Oluştu!'
            })
        })
    }

}


function fixUrls(){
    let txtUrls = document.getElementById("url_control").value;
    const result = txtUrls.split(/\r?\n/);
    let urls = result.filter(url => url.trim().length > 0).map(url => url.trim());
    const fixed = urls.join("\n");
    document.getElementById("url_control").value=fixed;
}

function emptyTable(){
    const resultValidate = document.getElementById("resultValidate");
    if(resultValidate.firstElementChild!=null) {
        resultValidate.removeChild(resultValidate.firstElementChild);
    }
}