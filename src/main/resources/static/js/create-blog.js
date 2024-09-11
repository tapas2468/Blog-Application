const fileInput = document.getElementById('banner'); // Selects an element by its id from html file.

// console.log(fileInput);
// console.dir(fileInput);


const bannerPreview = document.querySelector('.banner-preview'); //  Selects the first element that matches a CSS selector.

// console.log(bannerPreview);


fileInput.addEventListener('change', onFileChange); // event register

function onFileChange(event) {
    const file = event.target.files[0];

    
    const url = URL.createObjectURL(file);
    // console.log(file);

    // const img = document.createElement('img');
    // img.setAttribute('src', url);

    // document.body.appendChild(img);

    bannerPreview.innerHTML = `<img src = "${url}">`;



}