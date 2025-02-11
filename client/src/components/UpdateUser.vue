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

    <!-- Update User Form Section -->
    <section class="form-container">
      <h1>Update User</h1>
      <form @submit.prevent="updateUser">

        <!-- Display the current user's username -->
        <section class="user-info">
          <p>Updating user: <strong>{{ user.username }}</strong></p> <!-- Changed to username -->
        </section>

        <!-- Update Username input -->
        <div class="form-group">
          <label for="username">Username </label>
          <input type="text" id="username" v-model="user.username" placeholder="Username" required />
        </div>

        <!-- Update Email input -->
        <div class="form-group">
          <label for="email">Email  </label>
          <input type="email" id="email" v-model="user.email" placeholder="Email" required />
        </div>

        <!-- Update Role input -->
        <div class="form-group">
          <label for="role">Role</label>
          <select id="role" v-model="user.role" required>
            <option value="ROLE_USER">User</option>
            <option value="ROLE_ADMIN">Admin</option>
          </select>
        </div>

        <!-- Update Password input -->
        <div class="form-group">
          <label for="password">Password </label>
          <input type="password" id="password" v-model="user.password" placeholder="Password" required />
        </div>

        <!-- Update User ID input (Optional for Admin) -->
        <div class="form-group">
          <label for="userId">User ID </label>
          <input type="number" id="userId" v-model="user.userId" placeholder="User ID" disabled />
        </div>

        <button type="submit" class="cta-button">Update User</button>
      </form>

      <!-- Button to go back to the admin page -->
      <button @click="goBackToAdminPage" class="cta-button">Go Back to Admin Page</button>
    </section>

    <!-- Footer Component -->
    <Footer />

  </div>

  <div v-else>
    <p>You do not have permission to update this user.</p>
  </div>
</template>

<script>
import axios from 'axios';
import Navbar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';

export default {
  name: 'UpdateUser',
  components: {
    Navbar,
    Footer,
  },
  data() {
    return {
      user: {
        name: '',
        email: '',
        role: 'ROLE_USER', // Default role can be USER
        password: '',
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
    const userId = this.$route.params.id; // Get the ID from the route
    this.fetchUserDetails(userId);
  },
  methods: {
    // Fetch current details of the user
    async fetchUserDetails(userId) {
      try {
        const response = await axios.get(`http://localhost:9000/users/${userId}`);
        this.user = response.data;
      } catch (error) {
        console.error('Error fetching user details:', error);
      }
    },

    // Submit the updated user data
    async updateUser() {
      try {
        const userId = this.$route.params.id;
        const response = await axios.put(
          `http://localhost:9000/users/${userId}`,
          this.user,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('token')}`, // Use the token to authorize
            }
          }
        );
        console.log('User Updated:', response.data);
        this.$router.push('/admin'); // Redirect after success
      } catch (error) {
        console.error('Error updating user:', error);
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


/* Update User Form Section */
.form-container {
  max-width: 400px;
  margin: 0 auto;
  background: #f5f5f5;
  padding: 40px 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-top: 350px; /* To ensure it's not under the hero section */
  margin-bottom: 20px;
}

select {
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

  </style>
  