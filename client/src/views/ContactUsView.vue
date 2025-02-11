<template>
  <div class="main-layout">

    <!-- Hero Banner Section -->
    <section class="hero">
      <div class="hero-content">

        <!-- Logo -->
        <img src="/Logo img.png" alt="Logo" class="hero-logo" />
        <p>Your go-to place for finding country dancing across the country!</p>

        <!-- Navbar component -->
        <Navbar />
        
      </div>
    </section>

    <!-- Contact Form Section -->
    <section class="contact-form">
      <h2>Contact Us</h2>
      <h3>Have a question? Got a favorite dance hall you think we should feature?</h3>
      <h3>We'd love to hear your recommendations!</h3>

      <form @submit.prevent="submitForm" method="POST">
        <div class="form-group">
          <label for="name">Your Name:</label>
          <input type="text" id="name" v-model="form.name" required placeholder="Enter your name" />
        </div>

        <div class="form-group">
          <label for="email">Your Email:</label>
          <input type="email" id="email" v-model="form.email" required placeholder="Enter your email" />
        </div>

        <div class="form-group">
          <label for="message">Your Message:</label>
          <textarea id="message" v-model="form.message" required placeholder="Enter your message"></textarea>
        </div>

        <button type="submit" class="cta-button">Submit</button>
      </form>
    </section>

    <!-- Footer Component -->
    <Footer />

  </div>
</template>

<script>
import Navbar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';

export default {
  name: 'ContactUsView',
  components: {
    Navbar,
    Footer,
  },
  data() {
    return {
      form: {
        name: '',
        email: '',
        message: '',
      },
    };
  },
  methods: {
    submitForm() {
      console.log('Form submitted:', this.form);
      this.form.name = '';
      this.form.email = '';
      this.form.message = '';
      alert('Thank you for contacting us!');
    },
  },
};
</script>

<style scoped>
.main-layout {
  font-family: Arial, sans-serif;
  padding-top: 200px; /* Adds space to prevent content from being covered by the fixed hero */
}

/* Hero section */
.hero {
  background-color: #ab663f;
  color: white;
  text-align: center;
  padding: 5px 0;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
  overflow: hidden; 
}

.hero .hero-logo {
  width: 200px; /* Adjust size of the logo */
  margin-bottom: 5px;
  display: block; 
  margin-left: auto;
  margin-right: auto;
}

.hero .hero-content {
  padding-top: 0px; 
}

.hero p {
  font-size: 1.25rem;
  margin-bottom: 3px;
}

/* Contact Form Section */
.contact-form {
  max-width: 400px; 
  margin: 0 auto; 
  background: #F7F1E1;
  padding: 40px 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-top: 120px; 
  margin-bottom: 10px;
}

.contact-form h2 {
  font-size: 1.5rem;
  margin-bottom: 10px; 
  text-align: center;
}

.contact-form h3 {
  font-size: 1.1rem;
  margin-bottom: 20px;
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-size: 1.1rem;
  margin-bottom: 5px;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-group textarea {
  height: 150px;
  resize: vertical;
}

/* Button */
.cta-button {
  width: 100%;
  padding: 10px;
  background-color: #fff;
  color: #ab663f;
  border-radius: 5px;
  font-size: 1.2rem;
  cursor: pointer;
  border: none;
}

.cta-button:hover {
  background-color: #d1b49f;
}

/* Success or error message */
.message {
  margin-top: 20px;
  font-size: 1.1rem;
  color: #28a745; /* Green for success */
}

/* Media Queries for responsive layout */
@media(max-width: 1024px) {
  .dashboard-lists {
    flex-direction: column; 
  }

  .user-list, .dancehall-list {
    margin-bottom: 20px; 
  }
}

@media(max-width: 768px) {
  .hero {
    height: 100vh;
    overflow-y: auto;
    position: relative;
  }

  .hero .hero-content {
    padding-top: 20px;
  }

  .hero-logo {
    width: 150px; /* Slightly smaller logo size on mobile */
  }

  .main-layout {
    padding-top: 0;
  }

  .cta-button {
    font-size: 0.9rem; /* Adjust button size on smaller screens */
    padding: 8px 16px;
  }
}

@media(max-width: 480px) {
  .hero-logo {
    width: 120px; /* Adjust logo size even smaller for mobile */
  }

  .cta-button {
    font-size: 0.85rem; /* Further reduce font size on very small screens */
    padding: 6px 12px;
  }

}

</style>
