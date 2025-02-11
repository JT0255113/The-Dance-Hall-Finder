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

    <!-- Featured Dance Halls Section -->
    <section class="featured-dancehalls">
      <h2>Featured Dance Halls</h2>
      <div class="dancehall-list">
        <div v-for="hall in danceHalls" :key="hall.danceHallId" class="dancehall-item">
          <img :src="hall.imageUrl || 'https://placehold.co/600x200'" alt="Dance Hall Image">
          <h3>{{ hall.name }}</h3>
          <p>{{ hall.city }} , {{ hall.state }}</p>
          <router-link :to="'/dancehalls/' + hall.danceHallId" class="cta-button">View Details</router-link>
        </div>
      </div>
    </section>

    <!-- Featured Events Section 
    <section class="featured-events">
      <h2>Featured Events</h2>
      <div class="event-list">
        <div v-for="event in events" :key="event.event_id" class="event-item">
          <img :src="event.imageUrl || 'https://placehold.co/600x200'" alt="Event Image">
          <h3>{{ event.eventName }}</h3>
          <p>{{ event.eventDate }}</p>
          <router-link :to="'/events/' + event.event_id" class="cta-button">View Event</router-link>
        </div>
      </div>
    </section>
    -->

    <!-- Footer Component -->
    <Footer />
  </div>
</template>

<script>
import Navbar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';
import axios from 'axios';

export default {
  name: 'LandingPageView',
  components: {
    Navbar,
    Footer
  },
  data() {
    return {
      danceHalls: [], // Stores list of dance halls
      events: []      // Stores list of events
    };
  },
  computed: {
    // Check if the user is an admin based on localStorage or other state management
    isAdmin() {
      const user = JSON.parse(localStorage.getItem('user')); // Adjust as necessary
      return user && user.role === 'ROLE_ADMIN';
    }
  },
  mounted() {
    // Fetch dynamic data when the component is mounted
    this.fetchDanceHalls();
    this.fetchEvents();
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
    },

    // Fetch list of events from the backend
    async fetchEvents() {
      try {
        const response = await axios.get('http://localhost:9000/events');
        this.events = response.data.sort((a, b) => b.event_id - a.event_id).slice(0, 3);
      } catch (error) {
        console.error('Error fetching events:', error);
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
}

.cta-button:hover {
  background-color: #d1b49f;
}


/* main content section*/
.featured-dancehalls,
.featured-events {
  padding: 40px 20px;
  margin-top: 80px; /* Adds margin to create space from the fixed hero section */
}

.dancehall-list,
.event-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.dancehall-item,
.event-item {
  background: #F7F1E1;
  padding: 20px;
  margin: 10px;
  width: calc(33% - 20px);
  box-sizing: border-box;
  border-radius: 10px;
  text-align: center;
}

.dancehall-item img,
.event-item img {
  width: 100%;
  height: auto;
  border-radius: 10px;
}

/* Media Queries for responsive layout */
@media(max-width: 1024px) {
  .dancehall-item,
  .event-item {
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

  .dancehall-item,
  .event-item {
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

  .dancehall-item,
  .event-item {
    width: 100%; /* 1 item per row on even smaller screens */
  }
}

</style>
