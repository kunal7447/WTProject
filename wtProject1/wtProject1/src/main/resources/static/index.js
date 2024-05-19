const search = () => {
    // Remove or comment out console.log statements
    // console.log("Searching......");
    let query = $("#search-input").val();

    if (query === ''){
        $(".search-result").hide();
    } else {
        // search
        console.log(query);
        // sending request to the server
        let url = window.location.protocol + "//" + window.location.host +`/search/${query}`;
        // modern js used promise no need to use ajax
        fetch(url).then((response) =>{
            return response.json();
        } ).then((data)=>{
            // data
            console.log(data);
            // show data in html
            let text = `<div class='list-group'>`;
            data.forEach((contact)=>{
                console.log(contact.name);
                text += `<a href="http://localhost:9191/image/${contact.name}/" style="background: whitesmoke" class="list-group-item list-group-item-action">${contact.name}</a>`;
            });
            text += `</div>`
            // show search result using jquery
            $(".search-result").html(text).show();
        });
    }
}
