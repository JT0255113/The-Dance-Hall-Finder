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

    <!-- All Dance Halls Section -->
    <div class="dancehalls-list">
      <h1>All Dance Halls</h1>

      <!-- Display search query -->
      <div v-if="searchQuery">
        <p>Showing results for: "{{ searchQuery }}"</p>
      </div>

      <!-- Dance Halls Grid -->
      <div class="dancehall-list">
        <div v-for="hall in filteredDanceHalls" :key="hall.danceHallId" class="dancehall-item">
          <img :src="hall.imageUrl || 'https://placehold.co/600x200'" alt="Dance Hall Image">
          <h3>{{ hall.name }}</h3>
          <p>{{ hall.city }}, {{ hall.state }}</p>
          <router-link :to="'/dancehalls/' + hall.danceHallId" class="cta-button">View Details</router-link>
        </div>
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
  name: 'DanceHallsListView',
  components: {
    Navbar,
    Footer,
  },
  data() {
    return {
      danceHalls: [],
    };
  },
  computed: {
    searchQuery() {
      // Get the search query from the URL
      return this.$route.query.search || '';
    },
    filteredDanceHalls() {
      // Filter the dance halls based on the search query --- need to update in next stage
      if (!this.searchQuery) return this.danceHalls;
      return this.danceHalls.filter(hall =>
        hall.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        hall.city.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        hall.state.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  mounted() {
    this.fetchDanceHalls();
  },
  methods: {
    async fetchDanceHalls() {
      try {
        const response = await axios.get('http://localhost:9000/dancehalls');
        this.danceHalls = response.data;
      } catch (error) {
        console.error('Error fetching dance halls:', error);
      }
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
  width: 200px; /* Adjust size of the logo */
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

/* buttons */
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

.dancehalls-list {
  padding: 40px 20px;
  margin-top: 80px;
}

.dancehall-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.dancehall-item {
  background: #F7F1E1;
  padding: 20px;
  margin: 10px;
  width: calc(33% - 20px);
  box-sizing: border-box;
  border-radius: 10px;
  text-align: center;
}

.dancehall-item img {
  width: 100%;
  height: auto;
  border-radius: 10px;
}

/* Media Queries for responsive layout */
@media(max-width: 1024px) {
  .dancehall-item {
    width: calc(50% - 20px); /* 2 items per row */
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

  .dancehall-item {
    width: calc(50% - 20px); /* 2 items per row on smaller screens */
  }
}

@media(max-width: 480px) {
  .hero {
    height: 100vh; 
  }

  .hero-logo {
    width: 120px; /* Adjust logo size even smaller for mobile */
  }

  .dancehall-item {
    width: 100%; /* 1 item per row on even smaller screens */
  }
}

</style>
