<template>
    <div v-if="isAdmin" class="main-layout">
  
      <!-- Hero Banner Section -->
      <section class="hero">
        <div class="hero-content">
          <!-- Logo -->
          <img src="/Logo img.png" alt="Logo" class="hero-logo" />
          <p>Update Reviews for Dance Halls Across the Country!</p>
  
          <!-- Navbar component -->
          <Navbar />
        </div>
      </section>
  
      <!-- Update Review Form Section -->
      <section class="form-container">
        <h1>Update Review</h1>
        <form @submit.prevent="updateReview">
  
          <!-- Review Text input -->
          <div class="form-group">
            <label for="reviewText">Review Text</label>
            <textarea id="reviewText" v-model="review.reviewText" placeholder="Write your review here..." required></textarea>
          </div>
  
          <!-- Rating input -->
          <div class="form-group">
            <label for="rating">Rating</label>
            <input type="number" id="rating" v-model="review.rating" min="1" max="5" placeholder="Rating (1-5)" required/>
          </div>
  
          <!-- Dance Hall selection -->
          <div class="form-group">
            <label for="danceHallId">Select Dance Hall</label>
            <select id="danceHallId" v-model="review.danceHallId" required>
              <option v-for="hall in danceHalls" :key="hall.danceHallId" :value="hall.danceHallId">
                {{ hall.name }} - {{ hall.city }}, {{ hall.state }}
              </option>
            </select>
          </div>
  
          <!-- User ID input -->
          <div class="form-group">
            <label for="userId">User ID (Enter 2 for Admin)</label>
            <input type="number" id="userId" v-model="review.userId" placeholder="User ID"/>
          </div>
  
          <button type="submit" class="cta-button">Update Review</button>
        </form>
  
        <!-- Button to go back to the admin page -->
        <button @click="goBackToAdminPage" class="cta-button">Go Back to Admin Page</button>
  
      </section>
  
      <!-- Footer Component -->
      <Footer />
    </div>
  
    <div v-else>
      <p>You do not have permission to update this review.</p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import Navbar from '../components/NavBar.vue';
  import Footer from '../components/Footer.vue';
  
  export default {
    name: 'UpdateReview',
    components: {
      Navbar,
      Footer,
    },
    data() {
      return {
        review: {
          reviewText: '',
          rating: null,
          danceHallId: null,
          userId: null,
        },
        danceHalls: [], // Add this to store dance halls data
      };
    },
    computed: {
      isAdmin() {
        const user = JSON.parse(localStorage.getItem('user'));
        return user && user.role === 'ROLE_ADMIN';
      }
    },
    mounted() {
      const reviewId = this.$route.params.id; // Get the Review ID from the route
      this.fetchReviewDetails(reviewId);
      this.fetchDanceHalls(); // Fetch dance halls
    },
    methods: {
      // Fetch current review details
      async fetchReviewDetails(reviewId) {
        try {
          const response = await axios.get(`http://localhost:9000/reviews/${reviewId}`);
          this.review = response.data;
        } catch (error) {
          console.error('Error fetching review details:', error);
        }
      },
  
      // Fetch the list of dance halls
      async fetchDanceHalls() {
        try {
          const response = await axios.get('http://localhost:9000/dancehalls');
          this.danceHalls = response.data;
        } catch (error) {
          console.error('Error fetching dance halls:', error);
        }
      },
  
      // Submit the updated review data
      async updateReview() {
        try {
          const reviewId = this.$route.params.id;
          const response = await axios.put(
            `http://localhost:9000/reviews/${reviewId}`,
            this.review,
            {
              headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`, // Use the token to authorize
              }
            }
          );
          console.log('Review Updated:', response.data);
          this.$router.push('/reviews'); // Redirect after success
        } catch (error) {
          console.error('Error updating review:', error);
        }
      },
  
      // Method to go back to the admin page
      goBackToAdminPage() {
        this.$router.push('/admin');  
      }
    }
  };
  </script>
  
  
  <style scoped>
  .main-layout {
    font-family: Arial, sans-serif;
    padding-top: 200px;
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
  
  .hero-logo {
    width: 200px;
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
  
  /* Form container */
  .form-container {
    max-width: 400px;
    margin: 0 auto;
    background: #f5f5f5;
    padding: 40px 20px;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    margin-top: 100px;
  }
  
  h1 {
    font-size: 2rem;
    margin-bottom: 20px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  label {
    font-weight: bold;
    font-size: 1rem;
  }
  
  input, textarea {
    width: 100%;
    padding: 8px;
    margin: 10px 0;
    box-sizing: border-box;
    border: 1px solid #ddd;
    border-radius: 5px;
  }

  /* Buttons*/  
  button {
    padding: 10px 20px;
    background-color: #ab663f;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 1rem;
    width: 100%;
  }
  
  button:hover {
    background-color: #d1b49f;
  }
  
  .cta-button {
    padding: 10px 20px;
    background-color: #fff;
    color: #ab663f;
    text-decoration: none;
    border-radius: 5px;
    font-size: 1rem;
  }
  
  .cta-button:hover {
    background-color: #d1b49f;
  }
  
  /* Media Queries for responsive layout */
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
      width: 150px;
    }
  
    .main-layout {
      padding-top: 0;
    }
  
    .form-container {
      margin-top: 150px;
    }
  
    button {
      width: 100%;
    }
  }
  </style>
  