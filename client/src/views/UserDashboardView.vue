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
  
      <!-- User Info and Dashboard -->
      <div v-if="user">
        <h2>Welcome, {{ user.username }}</h2>
        
        <!-- Update User Info Form -->
        <div class="update-user-info">
          <h3>Update Your Information</h3>
          <form @submit.prevent="updateUserInfo">
            <label for="name">Name</label>
            <input type="text" id="name" v-model="user.name" required />
            
            <label for="address">Address</label>
            <input type="text" id="address" v-model="user.address" required />
            
            <label for="city">City</label>
            <input type="text" id="city" v-model="user.city" required />
            
            <label for="state">State</label>
            <input type="text" id="state" v-model="user.stateCode" required />
            
            <label for="zip">ZIP</label>
            <input type="text" id="zip" v-model="user.zip" required />
            
            <button type="submit" class="cta-button">Update Information</button>
          </form>
        </div>
  
        <!-- Favorite Dance Halls -->
        <div class="favorite-dancehalls">
          <h3>Your Favorite Dance Halls</h3>
          <div v-if="favoriteDanceHalls.length === 0">You don't have any favorite dance halls yet.</div>
          <div v-for="hall in favoriteDanceHalls" :key="hall.danceHallId" class="dancehall-item">
            <h4>{{ hall.name }}</h4>
            <p>{{ hall.city }}, {{ hall.state }}</p>
            <router-link :to="'/dancehalls/' + hall.danceHallId + '/reviews'">
              <button class="cta-button">View Reviews</button>
            </router-link>
          </div>
        </div>
  
        <!-- Reviews -->
        <div class="user-reviews">
          <h3>Your Reviews</h3>
          <div v-if="reviews.length === 0">You haven't left any reviews yet.</div>
          <div v-for="review in reviews" :key="review.id" class="review-item">
            <p>{{ review.text }}</p>
            <button @click="updateReview(review.id)" class="cta-button">Update</button>
            <button @click="deleteReview(review.id)" class="cta-button">Delete</button>
          </div>
        </div>
      </div>
  
      <!-- Footer Component -->
      <Footer />
      
    </div>
  </template>
  
  <script>
  import Navbar from "../components/NavBar.vue";
  import Footer from "../components/Footer.vue";
  import axios from "axios";
  
  export default {
    name: "UserDashboard",
    components: {
      Navbar,
      Footer
    },
    data() {
      return {
        user: null,
        favoriteDanceHalls: [],
        reviews: [],
        isLoading: false,
        errorMessage: ''
      };
    },
    computed: {
      userId() {
        const user = JSON.parse(localStorage.getItem('user'));
        return user ? user.id : null;
      }
    },
    mounted() {
      if (this.userId) {
        this.fetchUserData();
        this.fetchFavoriteDanceHalls();
        this.fetchUserReviews();
      }
    },
    methods: {
      // Fetch logged-in user's data
      async fetchUserData() {
        try {
          const response = await axios.get(`http://localhost:9000/users/${this.userId}`);
          this.user = response.data;
        } catch (error) {
          this.errorMessage = "Error fetching user data.";
          console.error(error);
        }
      },
  
      // Fetch user's favorite dance halls
      async fetchFavoriteDanceHalls() {
        try {
          const response = await axios.get(`http://localhost:9000/users/${this.userId}/favorites`);
          this.favoriteDanceHalls = response.data;
        } catch (error) {
          this.errorMessage = "Error fetching favorite dance halls.";
          console.error(error);
        }
      },
  
      // Fetch reviews left by the user
      async fetchUserReviews() {
        try {
          const response = await axios.get(`http://localhost:9000/users/${this.userId}/reviews`);
          this.reviews = response.data;
        } catch (error) {
          this.errorMessage = "Error fetching reviews.";
          console.error(error);
        }
      },
  
      // Update user's personal information
      async updateUserInfo() {
        try {
          const response = await axios.put(`http://localhost:9000/users/${this.userId}`, this.user);
          this.user = response.data; // Update the user info after successful update
          alert("Information updated successfully!");
        } catch (error) {
          this.errorMessage = "Error updating user information.";
          console.error(error);
        }
      },
  
      // Update a review
      updateReview(reviewId) {
        // Redirect to update review page
        this.$router.push(`/reviews/${reviewId}/update`);
      },
  
      // Delete a review
      async deleteReview(reviewId) {
        if (confirm('Are you sure you want to delete this review?')) {
          try {
            await axios.delete(`http://localhost:9000/reviews/${reviewId}`);
            this.fetchUserReviews(); // Refresh the list of reviews after deletion
            alert("Review deleted successfully!");
          } catch (error) {
            this.errorMessage = "Error deleting review.";
            console.error(error);
          }
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .main-layout {
    font-family: Arial, sans-serif;
    padding-top: 100px; /* Adjust space to prevent content being covered by the fixed hero */
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
  
  /* Update User Info */
  .update-user-info {
    max-width: 600px;
    margin: 20px auto;
    background-color: #f9f9f9;
    padding: 20px;
    border-radius: 8px;
  }
  
  .update-user-info input {
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
  }
  
  /* Favorite Dance Halls */
  .favorite-dancehalls {
    margin-top: 20px;
  }
  
  .dancehall-item {
    padding: 15px;
    margin: 10px 0;
    background-color: #F7F1E1;
    border-radius: 5px;
  }
  
  /* User Reviews */
  .user-reviews {
    margin-top: 30px;
  }
  
  .review-item {
    padding: 15px;
    margin: 10px 0;
    background-color: #F7F1E1;
    border-radius: 5px;
  }
  
  .cta-button {
    padding: 10px 20px;
    background-color: #fff;
    color: #ab663f;
    text-decoration: none;
    border-radius: 5px;
    font-size: 1rem;
    margin: 10px;
  }
  
  .cta-button:hover {
    background-color: #d1b49f;
  }
  
  .error-message {
    color: red;
    margin-top: 20px;
  }
  </style>
  