      // JavaScript for image slider
      document.addEventListener("DOMContentLoaded", function () {
        let currentSlide = 0;
        const slides = document.querySelectorAll(".slide");

        function showSlide() {
          slides.forEach((slide) => {
            slide.style.display = "none";
          });
          currentSlide++;
          if (currentSlide >= slides.length) {
            currentSlide = 0;
          }
          slides[currentSlide].style.display = "block";
          setTimeout(showSlide, 3000); // Change slide every 3 seconds
        }

        showSlide();
      });

      // Add animation class when the page loads
      document.addEventListener("DOMContentLoaded", function () {
        document.querySelector(".hero-content").classList.add("animate");
      });