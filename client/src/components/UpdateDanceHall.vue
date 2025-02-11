<template>
  <div v-if="isAdmin" class="main-layout">

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

    <!-- Update a Dance Hall Form Section -->
    <section class="form-container">
      <h1>Update Dance Hall</h1>
      <form @submit.prevent="updateDanceHall">

        <!-- Update Dance Hall Name input -->
        <div class="form-group">
          <label for="name">Dance Hall Name</label>
          <input type="text" id="name"
            v-model="danceHall.name" placeholder="Name" required/>
        </div>

        <!-- Update Street Address input -->
        <div class="form-group">
          <label for="streetAddress">Street Address</label>
          <input type="text" id="streetAddress" v-model="danceHall.streetAddress" placeholder="Street Address" required/>
        </div>

        <!-- Update City input -->
        <div class="form-group">
          <label for="city">City</label>
          <input type="text" id="city" v-model="danceHall.city" placeholder="City" required/>
        </div>

        <!-- Update State input -->
        <div class="form-group">
          <label for="state">State</label>
          <input type="text" id="state" v-model="danceHall.state" placeholder="State" required/>
        </div>

        <!-- Update ZipCode input -->
        <div class="form-group">
          <label for="zipCode">Zip Code</label>
          <input type="text" id="zipCode" v-model="danceHall.zipCode" placeholder="Zip Code" required/>
        </div>

        <!-- Update Image URL input -->
        <div class="form-group">
          <label for="imageUrl">Image URL</label>
          <input type="url" id="imageUrl" v-model="danceHall.imageUrl" placeholder="Image URL"/>
        </div>

        <!-- Update Website URL input -->
        <div class="form-group">
          <label for="websiteUrl">Website URL</label>
          <input type="url" id="websiteUrl" v-model="danceHall.websiteUrl" placeholder="Website URL"/>
        </div>

        <!-- Update Dance style input-->
        <div class="form-group">
          <label for="danceStyle">Dance Style</label>
          <input type="text" id="danceStyle" v-model="danceHall.danceStyle" placeholder="Dance Style" required/>
        </div>


        <!-- Update userId input field, prefilled with logged-in user id --- needs correction-->
        <div class="form-group">
          <label for="userId">User ID (Enter 2 for Admin)</label>
          <input type="number" id="userId" v-model="danceHall.userId" placeholder="User ID"/>
        </div>

        <button type="submit" class="cta-button">Update Dance Hall</button>
      </form>

      <!-- Button to go back to the admin page -->
      <button @click="goBackToAdminPage" class="cta-button">Go Back to Admin Page</button>

    </section>
    
    <!-- Footer Component -->
    <Footer />

  </div>

  <div v-else>
    <p>You do not have permission to update this dance hall.</p>
  </div>
</template>

<script>
import axios from 'axios';
import Navbar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';

export default {
  name: 'UpdateDanceHall',
  components: {
    Navbar,
    Footer,
  },
  data() {
    return {
      danceHall: {
      name: '',
      streetAddress: '', 
      city: '',
      state: '',
      zipCode: '',  
      imageUrl: '',
      websiteUrl: '',
      danceStyle: '', 
      userId: null,
    }
    };
  },
  computed: {
    isAdmin() {
      const user = JSON.parse(localStorage.getItem('user'));
      return user && user.role === 'ROLE_ADMIN';
    }
  },
  mounted() {
    const danceHallId = this.$route.params.id; // Get the ID from the route
    this.fetchDanceHallDetails(danceHallId);
  },
  methods: {
    // Fetch current details of the dance hall
    async fetchDanceHallDetails(danceHallId) {
      try {
        const response = await axios.get(`http://localhost:9000/dancehalls/${danceHallId}`);
        this.danceHall = response.data;
      } catch (error) {
        console.error('Error fetching dance hall details:', error);
      }
    },

    // Submit the updated dance hall data
    async updateDanceHall() {
      try {
        const danceHallId = this.$route.params.id;
        const response = await axios.put(
          `http://localhost:9000/dancehalls/${danceHallId}`,
          this.danceHall,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('token')}`, // Use the token to authorize
            }
          }
        );
        console.log('Dance Hall Updated:', response.data);
        this.$router.push('/dancehalls'); // Redirect after success
      } catch (error) {
        console.error('Error updating dance hall:', error);
      }
    },
      // Method to go back to the admin page
      goBackToAdminPage() {
      this.$router.push('/admin');  
    },
  }
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

/* Form container */
.form-container {
  max-width: 400px;
  margin: 0 auto;
  background: #f5f5f5;
  padding: 40px 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-top: 100px; /* Creates space from the fixed hero section */
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

input {
  width: 100%;
  padding: 8px;
  margin: 10px 0;
  box-sizing: border-box;
  border: 1px solid #ddd;
  border-radius: 5px;
}

/* Buttons */
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
    width: 150px; /* Slightly smaller logo size on mobile */
  }

  .main-layout {
    padding-top: 0;
  }

  .form-container {
    margin-top: 150px; /* Adjust space for mobile screens */
  }

  button {
    width: 100%;
  }
}
</style>
