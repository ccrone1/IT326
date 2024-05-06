document.addEventListener("DOMContentLoaded", function () {
  const slides = document.querySelectorAll(".slide");
  let currentSlide = 0;

  function showSlide() {
    slides.forEach(slide => {
      slide.style.display = "none";
    });
    slides[currentSlide].style.display = "block";
    currentSlide++;
    if (currentSlide >= slides.length) {
      currentSlide = 0;
    }
    setTimeout(showSlide, 3000);
  }

  showSlide();
});

