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

    <!-- Dance Hall Details Section -->
    <div class="dancehall-detail">
      <h1>{{ danceHall.name }}</h1>
      <img :src="danceHall.imageUrl || 'https://placehold.co/600x400'" alt="Dance Hall Image">
      <p><strong>Address:</strong> 
        {{ danceHall.streetAddress }}, {{ danceHall.city }}, {{ danceHall.state }} {{ danceHall.zipCode }}</p>
      <p><strong>Dancing Options Available:</strong> {{ danceHall.danceStyle }}</p>
      <p v-if="danceHall.websiteUrl">
          <strong>Website:</strong> 
          <a :href="danceHall.websiteUrl" target="_blank">{{ danceHall.websiteUrl }}</a>
      </p>


       <!-- Add Review Button -->
       <div v-if="isAuthenticated">
        <button @click="addReview" class="cta-button">Add Review</button>
      </div>

      <!-- Reviews Section -->
      <div v-if="reviews && reviews.length > 0">
        <h2>Reviews :</h2>
        
        <div v-for="review in reviews" :key="review.reviewId" class="review">
          <p><strong>Rating:</strong> {{ review.eventRating }} / 5</p>
          <p><strong>Review:</strong> {{ review.reviewText }}</p>
          
          <!-- Buttons for update and delete, visible only to the admin or the user who made the review -->
          <div v-if="isAuthenticated">
            <button v-if="canEditOrDeleteReview(review)" @click="updateReview(review)" class="cta-button">Update</button>
            <button v-if="canEditOrDeleteReview(review)" @click="deleteReview(review.reviewId)" class="cta-button">Delete</button>
          </div>
        </div>
      </div>
      <div v-else>
        <p>No reviews available for this dance hall yet.</p>
      </div>
    </div>

    <!-- Footer -->
    <Footer />

  </div>
</template>


<script>
import Navbar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';
import axios from 'axios';

export default {
  name: 'DanceHallDetailsView',
  components: {
    Navbar,  
    Footer,  
  },
  data() {
    return {
      danceHall: {},  // Dance hall details
      reviews: [],  // Reviews for the dance hall
      currentUser: JSON.parse(localStorage.getItem('user')),  // Get current user from localStorage
    };
  },
  computed: {
    // Check if the user is authenticated
    isAuthenticated() {
      return !!this.currentUser;
    },
    // Check if the user is the admin or the user who created the review
    canEditOrDeleteReview() {
      return (review) => {
        return this.currentUser && (this.currentUser.role === 'ROLE_ADMIN' || this.currentUser.id === review.userId);
      };
    }
  },
  mounted() {
    const danceHallId = this.$route.params.id; // Get the ID from the route parameter
    this.fetchDanceHallDetails(danceHallId);
  },
  methods: {
    // Fetch dance hall details and associated reviews
    async fetchDanceHallDetails(id) {
      try {
        // Fetch dance hall details
        const danceHallResponse = await axios.get(`http://localhost:9000/dancehalls/${id}`);
        this.danceHall = danceHallResponse.data;

        // Fetch reviews for the dance hall 
        const reviewsResponse = await axios.get(`http://localhost:9000/reviews/dancehall/${id}`);
        this.reviews = reviewsResponse.data; // Store reviews fetched for the dance hall

      } catch (error) {
        console.error('Error fetching dance hall details or reviews:', error);
      }
    },

    // Handle the update action
    updateReview(review) {
      this.$router.push(`/reviews/update/${review.reviewId}`);
    },

    // Handle the delete action
    async deleteReview(reviewId) {
      try {
        // Send a request to delete the review
        await axios.delete(`http://localhost:9000/reviews/${reviewId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        // After successful deletion, remove the review from the list
        this.reviews = this.reviews.filter(review => review.reviewId !== reviewId);
        alert('Review deleted successfully.');
      } catch (error) {
        console.error('Error deleting review:', error);
        alert('There was an error deleting the review.');
      }
    },

    // Add review handler
    addReview() {
      this.$router.push(`/add-review/${this.danceHall.id}`);
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
}

.hero .hero-logo {
  width: 200px; 
  margin-bottom: 5px;
  display: block; 
  margin-left: auto;
  margin-right: auto;
}

.hero .hero-content {
  padding-top: 0px; 
}

.hero h1 {
  font-size: 3rem;
  margin-bottom: 20px;
}

.hero p {
  font-size: 1.25rem;
  margin-bottom: 3px;
}


/* main content section buttons */
.cta-button {
  padding: 10px 20px;
  background-color: white;
  color: ab663f;
  text-decoration: none;
  border-radius: 5px;
  font-size: 1rem;
}

.cta-button:hover {
  background-color: #d1b49f;
}

/* main content section*/
.dancehall-detail {
  padding: 40px 20px;
  margin-top: 80px; /* Adds margin to create space from the fixed hero section */
}

.dancehall-detail img {
  width: 100%;
  max-width: 600px;
  height: auto;
  border-radius: 10px;
  margin-bottom: 20px;
}

.dancehall-detail h1 {
  font-size: 2rem;
  margin-bottom: 20px;
}

.dancehall-detail p {
  font-size: 1.1rem;
  margin-bottom: 15px;
}

/* Reviews section */
.review {
  padding: 15px;
  border: 1px solid #ddd;
  margin-bottom: 10px;
  border-radius: 5px;
}

.review p {
  font-size: 1rem;
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

  .dashboard-lists {
    flex-direction: column; 
  }

  .user-item, .dancehall-item {
    width: 100%; 
    margin-bottom: 10px; 
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

  .user-item, .dancehall-item {
    width: 100%; 
    padding: 12px;
  }
}
</style>
