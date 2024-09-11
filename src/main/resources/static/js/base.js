window.onscroll = function() { changeNavbarBackground(); };

function changeNavbarBackground() {
    const navbar = document.querySelector('nav');
    const scrollPosition = window.scrollY || window.pageYOffset;

    // Change this value to when you want the background to change
    const changePoint = 50;

    if (scrollPosition > changePoint) {
        navbar.style.backgroundColor = '#ffaaff'; // Change to your desired background color
        navbar.style.padding="1rem";
    } else {
        navbar.style.backgroundColor = 'transparent'; // Reset to initial background color
    }
}
