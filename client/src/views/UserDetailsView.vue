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

    <!-- User Details Section -->
    <div class="user-detail">
      <h1>User Details</h1>
      <div v-if="user">
        <p><strong>Username:</strong> {{ user.username }}</p>
        <p><strong>Email:</strong> {{ user.email }}</p>
        <p><strong>Role:</strong> {{ user.role }}</p>
        <p><strong>Reviews:</strong></p>

        <!-- Update Button (only for admin) -->
        <div v-if="isAdmin">
          <router-link :to="'/users/' + user.id + '/update'">
            <button class="cta-button">Update User</button>
          </router-link>
          <button @click="deleteUser(user.id)" class="cta-button">Delete User</button>
        </div>
      </div>

      <div v-else>
        <p>User details not found.</p>
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
  name: 'UserDetailsView',
  components: {
    Navbar,
    Footer,
  },
  data() {
    return {
      user: null,  // User details
      isLoading: false,
      errorMessage: ''
    };
  },
  computed: {
    // Check if the current user is an admin
    isAdmin() {
      const currentUser = JSON.parse(localStorage.getItem('user'));
      return currentUser && currentUser.role === 'ROLE_ADMIN';
    }
  },
  mounted() {
    const userId = this.$route.params.id;  // Get the user ID from the route parameter
    this.fetchUserDetails(userId);
  },
  methods: {
    // Fetch user details from the backend
    async fetchUserDetails(id) {
      try {
        this.isLoading = true;
        const response = await axios.get(`http://localhost:9000/users/${id}`);
        this.user = response.data;
      } catch (error) {
        this.errorMessage = 'Error fetching user details.';
        console.error(error);
      } finally {
        this.isLoading = false;
      }
    },

    // Handle user deletion (only available to admins)
    async deleteUser(userId) {
      if (confirm('Are you sure you want to delete this user?')) {
        try {
          await axios.delete(`http://localhost:9000/users/${userId}`);
          this.$router.push('/admin');  // Redirect to the admin page after deletion
          alert('User deleted successfully!');
        } catch (error) {
          this.errorMessage = 'Error deleting the user.';
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
  padding-top: 200px; /* Prevent content from being covered by the fixed hero */
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

/* User Detail Section */
.user-detail {
  padding: 40px 20px;
  margin-top: 80px;
}

.user-detail h1 {
  font-size: 2rem;
  margin-bottom: 20px;
}

.user-detail p {
  font-size: 1.1rem;
  margin-bottom: 15px;
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

/* Media Queries for responsive layout */
@media(max-width: 1024px) {
  .user-detail {
    flex-direction: column; 
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
    width: 150px; 
  }

  .main-layout {
    padding-top: 0;
  }

  .cta-button {
    font-size: 0.9rem; 
    padding: 8px 16px;
  }

  .user-detail {
    flex-direction: column; 
  }
}

@media(max-width: 480px) {
  .hero-logo {
    width: 120px;
  }

  .cta-button {
    font-size: 0.85rem;
    padding: 6px 12px;
  }
}
</style>
