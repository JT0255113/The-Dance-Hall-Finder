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

    <div v-if="isAdmin">
      <div v-if="isAdmin" class="admin-panel">
        <h2>Welcome to the Admin Panel</h2>
        <p>Manage dance halls and users below:</p>


         <!-- Add User Button -->
         <router-link to="/register">
          <button class="cta-button">Add User</button>
        </router-link>

        <!-- Add Dance Hall Button -->
        <router-link to="/dancehalls/add">
          <button class="cta-button">Add Dance Hall</button>
        </router-link>

        
      </div>

      <!-- Admin Content -->
      <div class="dashboard-lists">
        
        <!-- User List Section -->
        <div class="user-list">
          <h2>Users</h2>
          <div v-if="isLoading">Loading...</div>
          <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
          <div v-for="user in users" :key="user.id" class="user-item">
            <h3>{{ user.username }}</h3>
            <p>Role: {{ user.role }}</p>

            <!-- View Details Button -->
            <router-link :to="'/users/' + user.id">
              <button class="cta-button">View Details</button>
            </router-link>

            <!-- Update Button -->
            <router-link :to="'/users/' + user.id + '/update'">
              <button class="cta-button">Update</button>
            </router-link>

            <!-- Delete Button -->
            <button @click="deleteUser(user.id)" class="cta-button">Delete</button>
          </div>
        </div>

        <!-- Dance Hall List Section -->
        <div class="dancehall-list">
          <h2>Dance Halls</h2>
          <div v-if="isLoading">Deleting...</div>
          <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
          <div v-for="hall in danceHalls" :key="hall.danceHallId" class="dancehall-item">
            <h3>{{ hall.name }}</h3>
            <p>{{ hall.city }}, {{ hall.state }}</p>
          
            <!-- View Details Button -->
            <router-link :to="'/dancehalls/' + hall.danceHallId">
              <button class="cta-button">View Details</button>
            </router-link>
            
            <!-- Update Button -->
            <router-link :to="'/dancehalls/' + hall.danceHallId + '/update'">
              <button class="cta-button">Update</button>
                          
            <!-- Delete Button -->
            </router-link>
            <button @click="deleteDanceHall(hall.danceHallId)" class="cta-button">Delete</button>
          </div>
        </div>
      </div>
    </div>

    <!-- If not an admin, show permission message -->
    <div v-else>
      <p>You do not have permission to view this page.</p>
      <router-link to="/" class="cta-button">Go to Home</router-link>
    </div>

    <!-- Footer Component -->
    <Footer />
    
  </div>
</template>

<script>
import Navbar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';
import axios from 'axios';

export default {
  name: 'AdminDashboard',
  components: {
    Navbar,
    Footer
  },
  data() {
    return {
      users: [],
      danceHalls: [],
      isLoading: false,
      errorMessage: ''
    };
  },
  computed: {
    isAdmin() {
      const user = JSON.parse(localStorage.getItem('user'));
      return user && user.role === 'ROLE_ADMIN';
    }
  },
  mounted() {
    this.fetchUsers();
    this.fetchDanceHalls();
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get('http://localhost:9000/users');
        this.users = response.data;
      } catch (error) {
        this.errorMessage = 'Error fetching users.';
        console.error(error);
      }
    },
    async deleteUser(userId) {
      if (confirm('Are you sure you want to delete this user?')) {
        this.isLoading = true;
        try {
          await axios.delete(`http://localhost:9000/users/${userId}`);
          this.fetchUsers();
          alert('User deleted successfully!');
        } catch (error) {
          this.errorMessage = 'There was an error deleting the user.';
          console.error(error);
        } finally {
          this.isLoading = false;
        }
      }
    },
    async fetchDanceHalls() {
      try {
        const response = await axios.get('http://localhost:9000/dancehalls');
        this.danceHalls = response.data;
      } catch (error) {
        this.errorMessage = 'Error fetching dance halls.';
        console.error(error);
      }
    },
    async deleteDanceHall(danceHallId) {
      if (confirm('Are you sure you want to delete this dance hall?')) {
        this.isLoading = true;
        try {
          await axios.delete(`http://localhost:9000/dancehalls/${danceHallId}`);
          this.fetchDanceHalls();
          alert('Dance hall deleted successfully!');
        } catch (error) {
          this.errorMessage = 'There was an error deleting the dance hall.';
          console.error(error);
        } finally {
          this.isLoading = false;
        }
      }
    }
  }
};
</script>

<style scoped>
.main-layout {
  font-family: Arial, sans-serif;
  padding-top: 300px; /* Adds space to prevent content from being covered by the fixed hero */
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

/* Admin Panel section */
.admin-panel {
  align-items: center;  
  justify-content: center;  
  text-align: center; 
  padding: 20px;
}

.admin-panel h2 {
}

.admin-panel p {
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

/* main content section buttons */
.cta-button:hover {
  background-color: #d1b49f;
}

/* Main content section for the admin dashboard */
.dashboard-lists {
  display: flex;
  justify-content: space-between;
  gap: 30px;
}

.user-list, .dancehall-list {
  flex: 1;
}

.user-item, .dancehall-item {
  padding: 15px;
  margin: 10px 0;
  background-color: #F7F1E1;
  border-radius: 5px;
}

.error-message {
  color: red;
  margin-top: 20px;
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
    width: 100%; /* Ensure items take full width */
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
    width: 100%; /* Full width for each item on very small screens */
    padding: 12px;
  }
}

</style>
