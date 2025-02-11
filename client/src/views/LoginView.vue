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

    <!-- Login Form Section -->
    <section class="login-form">
      <form @submit.prevent="login">
        <div class="form-fields">
          <label for="username">Username</label>
          <input type="text" id="username" placeholder="Username" v-model="user.username" required autofocus/>
          
          <label for="password">Password</label>
          <input type="password" id="password" placeholder="Password" v-model="user.password" required/>
        </div>

        <button type="submit" class="cta-button">Sign in</button>
        <hr />
        <p>
          Need an account? <router-link to="/register" class="link-register">Register!</router-link>
        </p>
      </form>
    </section>
    
     <!-- Footer Component -->
     <Footer />
     
  </div>
</template>

<script>
import authService from "../services/AuthService";
import Navbar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';
import axios from 'axios';


export default {
  name: 'LoginPageView',
  components:{
    Navbar,
    Footer
  },
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;
          if (!response) {
            alert(error);
          } else if (response.status === 401) {
            alert("Invalid username and password!");
          } else {
            alert(response.message);
          }
        });
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

/* Form styling */
.login-form {
  max-width: 400px;
  margin: 0 auto;
  background: #F7F1E1;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-top: 140px;
  margin-bottom: 20px;
}

.form-fields {
  margin-bottom: 20px;
}

.form-fields label {
  display: block;
  margin-bottom: 5px;
  font-size: 1rem;
  color: #333;
}

.form-fields input {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border-radius: 5px;
  border: 1px solid #ccc;
  margin-bottom: 15px;
}

.cta-button {
  width: 100%;
  padding: 10px;
  background-color: #fff;
  color: #ab663f;
  border-radius: 5px;
  font-size: 1.2rem;
  cursor: pointer;
  border: none;
}

.cta-button:hover {
  background-color: #d1b49f;
}

hr {
  border: 0;
  border-top: 1px solid #ccc;
  margin: 20px 0;
}

.link-register {
  color: #ab663f;
  text-decoration: none;
}

.link-register:hover {
  color: #d1b49f;
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

  
}

@media(max-width: 480px) {
  .hero {
    height: 100vh; 
  }

  .hero-logo {
    width: 120px; /* Adjust logo size even smaller for mobile */
  }

  
}


</style>
