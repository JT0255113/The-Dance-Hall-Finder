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
  
      <!-- About Us Section -->
      <section class="about-us">
        <h2>About Us</h2>
        <h3>Learn more about our mission and journey!</h3>

        <div class="about-content">
          <p>
            We’re a couple of passionate country dancers who, during our visit to Nashville, the Country Music Capital of the World,
            were surprised by how hard it was to find a place to dance. Despite the city's rich country music scene,
            it wasn’t as easy as we thought to discover the best spots for two-stepping, line dancing, and other country dances. 
            That’s when we decided to create a dedicated platform to help people like us easily find the best dance halls across the country.
          </p>
          <p>
            Our mission is simple: to connect country dancers with the best dance halls, making it easy to discover new places to dance,
            whether you're at home or traveling. Whether you're a seasoned dancer or just getting started,
            we want to help you find a welcoming place to enjoy the tradition of country dancing.
          </p>
          <p>
            Join us and become part of our growing community of dance enthusiasts! When you join our community,
            you'll not only find amazing places to dance, but you’ll also have the chance to share your experiences! 
            Add reviews to help other dancers discover the best places, 
            and be part of a growing network of people who love country dancing as much as you do.
          </p>

          <router-link to="/register" type= "submit" class="cta-button">Register!</router-link>
          
        </div>
      </section>
  
      <!-- Featured Dance Halls Section -->
      <section class="featured-dancehalls">
        <h2>Featured Dance Halls</h2>
        <div class="dancehall-list">
          <div v-for="hall in danceHalls" :key="hall.danceHallId" class="dancehall-item">
            <img :src="hall.imageUrl || 'https://placehold.co/600x200'" alt="Dance Hall Image">
            <h3>{{ hall.name }}</h3>
            <p>{{ hall.city }}, {{ hall.state }}</p>
            <router-link :to="'/dancehalls/' + hall.danceHallId" class="cta-button">View Details</router-link>
          </div>
        </div>
      </section>
  
      <!-- Footer Component -->
      <Footer />
    </div>
  </template>
  
  <script>
  import Navbar from '../components/NavBar.vue';
  import Footer from '../components/Footer.vue';
  import axios from 'axios';
  
  export default {
    name: 'AboutUsView',
    components: {
      Navbar,
      Footer
    },
    data() {
      return {
        danceHalls: [], // Stores list of dance halls
      };
    },
    mounted() {
      // Fetch dynamic data when the component is mounted
      this.fetchDanceHalls();
    },
    methods: {
      // Fetch list of dance halls from the backend
      async fetchDanceHalls() {
        try {
          const response = await axios.get('http://localhost:9000/dancehalls');
          this.danceHalls = response.data.sort((a, b) => b.danceHallId - a.danceHallId).slice(0, 3);
        } catch (error) {
          console.error('Error fetching dance halls:', error);
        }
      }
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

/* main content section buttons */
.cta-button {
  padding: 10px 20px;
  background-color: #fff;
  color: #ab663f;
  text-decoration: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.cta-button:hover {
  background-color: #d1b49f;
}

/* main content section*/
.about-us {
  padding: 40px 20px;
  margin-top: 80px; /* Adds margin to create space from the fixed hero section */
}

.about-content {
  max-width: 900px;
  margin: 0 auto;
  text-align: center;
  background: #F7F1E1;
  padding: 20px;
  border-radius: 10px;
}

.about-us h3 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  text-align: center;
}

.about-us p {
  font-size: 1.25rem;
  margin-bottom: 15px;
  line-height: 1.6;
}

.featured-dancehalls {
  padding: 40px 20px;
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
  .hero-logo {
    width: 120px; /* Adjust logo size even smaller for mobile */
  }

  .dancehall-item {
    width: 100%; /* 1 item per row on even smaller screens */
  }
}

  </style>
  