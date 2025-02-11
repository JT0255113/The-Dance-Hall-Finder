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

    <!-- Add Review Form Section -->
    <section class="add-review-form">
      <h2>Add a Review</h2>
      <form @submit.prevent="submitReview">
        <div class="form-group">
          <label for="reviewText">Review:</label>
          <textarea
            id="reviewText"
            v-model="review.reviewText"
            placeholder="Write your review here..."
            required
            maxlength="255"
          ></textarea>
        </div>

        <div class="form-group">
          <label for="eventRating">Rating:</label>
          <select v-model="review.eventRating" required>
            <option disabled value="">Select a rating</option>
            <option v-for="rating in ratings" :key="rating" :value="rating">{{ rating }}</option>
          </select>
        </div>

     <!-- Dance Hall Name Display -->
     <div class="form-group">
      <label for="danceHallName">Dance Hall:</label>
      <span id="danceHallName">{{ danceHall.name }}</span>
    </div>

        <button type="submit" class="cta-button">Submit Review</button>
      </form>

      <!-- Success or error message -->
      <div v-if="message" class="message">{{ message }}</div>
    </section>

    <!-- Footer Component -->
    <Footer />
  </div>
</template>

<script>
import axios from 'axios';
import Navbar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';

export default {
  name: 'AddReview',
  components: {
    Navbar,
    Footer,
  },
  data() {
    return {
      review: {
        reviewText: '',
        eventRating: '',
        danceHallId: '',  // This will hold the ID to submit
      },
      danceHall: {}, // To hold the fetched dance hall details
      message: '',
      ratings: [1, 2, 3, 4, 5],
    };
  },
  mounted() {
    // Retrieve the dance hall ID from the route params and prefill the form
    const danceHallId = this.$route.params.danceHallId;
    if (danceHallId) {
      this.fetchDanceHallDetails(danceHallId);
    }
  },
  methods: {
    // Fetch the dance hall details
    async fetchDanceHallDetails(danceHallId) {
      try {
        const response = await axios.get(`http://localhost:9000/dancehalls/${danceHallId}`);
        this.danceHall = response.data; // Store the fetched dance hall
        this.review.danceHallId = danceHallId; // Set the dance hall ID in review
      } catch (error) {
        console.error('Error fetching dance hall details:', error);
      }
    },

    // Handle the form submission
    async submitReview() {
      try {
        const response = await axios.post('http://localhost:9000/reviews', {
          reviewText: this.review.reviewText,
          eventRating: this.review.eventRating,
          danceHallId: this.review.danceHallId,
        });

        // Success message
        this.message = "Your review has been submitted successfully!";
        this.review.reviewText = '';
        this.review.eventRating = '';
      } catch (error) {
        console.error('Error submitting review:', error);
        this.message = "There was an error submitting your review. Please try again.";
      }
    },
  },
};
</script>


<style scoped>
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

/* Add Review Form Section */
.add-review-form {
  max-width: 400px;
  margin: 0 auto;
  background: #f5f5f5;
  padding: 40px 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-top: 350px; /* To ensure it's not under the hero section */
  margin-bottom: 20px;
}

.add-review-form h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  text-align: center;
}

.add-review-form .form-group {
  margin-bottom: 15px;
}

.add-review-form label {
  display: block;
  margin-bottom: 5px;
  font-size: 1rem;
  color: #333;
}

.add-review-form textarea,
.add-review-form select,
.add-review-form input {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border-radius: 5px;
  border: 1px solid #ccc;
}


/* Buttons */
.add-review-form button {
  width: 100%;
  padding: 10px;
  background-color: white;
  color: #ab663f;
  border-radius: 5px;
  font-size: 1.2rem;
  cursor: pointer;
  border: none;
  margin-top: 10px;
}

.add-review-form button:hover {
  background-color: #d1b49f;
}

.add-review-form .message {
  margin-top: 20px;
  font-size: 1.1rem;
  color: #28a745; /* Green for success */
}

.cta-button {
  width: 100%;
  padding: 10px;
  background-color: #ab663f;
  color: white;
  border-radius: 5px;
  font-size: 1.2rem;
  cursor: pointer;
  border: none;
}

.cta-button:hover {
  background-color: #d1b49f;
}

/* Media Queries for mobile responsiveness */
@media(max-width: 768px) {
  .hero .hero-logo {
    width: 150px; /* Slightly smaller logo size on mobile */
  }

  .add-review-form {
    margin-top: 200px; /* Adjust space for mobile screens */
  }
}
</style>
