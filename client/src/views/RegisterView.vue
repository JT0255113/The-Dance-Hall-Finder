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

    <!-- Register Form Section -->
    <section class="register-form">
      <form v-on:submit.prevent="register">
        <div class="form-fields">
          <label for="username">Username</label>
          <input type="text" id="username" placeholder="Username" v-model="user.username" required autofocus/>

          <label for="name">Name</label>
          <input type="text" id="name" placeholder="Name" v-model="user.name" required/>

          <label for="password">Password</label>
          <input type="password" id="password" placeholder="Password" v-model="user.password" required />

          <label for="confirmPassword">Confirm Password</label>
          <input type="password" id="confirmPassword" placeholder="Confirm Password" v-model="user.confirmPassword" required/>

          <label for="address">Address</label>
          <input type="text" id="address" placeholder="Address" v-model="user.address"/>

          <label for="city">City</label>
          <input type="text" id="city" placeholder="City" v-model="user.city"/>

          <label for="state">State</label>
          <input type="text" id="state" placeholder="State" v-model="user.stateCode" maxlength="2" required/>

          <label for="zip">ZIP</label>
          <input type="number" id="zip" placeholder="ZIP" v-model="user.zip" required minlength="5" maxlength="5"/>
        </div>

        <button type="submit" class="cta-button">Create Account</button>
        <hr />
        <p>
          Already have an account? <router-link to="/login" class="link-register">Sign in</router-link>
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
  name: 'RegisterView',
  components: {
    Navbar,
    Footer
  },
  data() {
    return {
      user: {
        username: "",
        name: "",
        password: "",
        confirmPassword: "",
        address: "",
        city: "",
        stateCode: "",
        zip: "",
        role: "user",
      },
    };
  },
  methods: {
    error(msg) {
      alert(msg);
    },
    success(msg) {
      alert(msg);
    },
    register() {
      if (this.user.password !== this.user.confirmPassword) {
        this.error("Password & Confirm Password do not match");
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.success("Thank you for registering, please sign in.");
              this.$router.push({
                path: "/login",
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            if (!response) {
              this.error(error);
            } else if (response.status === 400) {
              if (response.data.errors) {
                // Show the validation errors
                let msg = "Validation error: ";
                for (let err of response.data.errors) {
                  msg += `'${err.field}':${err.defaultMessage}. `;
                }
                this.error(msg);
              } else {
                this.error(response.data.message);
              }
            } else {
              this.error(response.data.message);
            }
          });
      }
    },
  },
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


/* Form styling */
.register-form {
  max-width: 400px;
  margin: 0 auto;
  background: #F7F1E1;
  padding: 40px 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-top: 350px;
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

/* button */
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

}

@media(max-width: 480px) {
  .hero-logo {
    width: 120px; /* Adjust logo size even smaller for mobile */
  }

  .cta-button {
    font-size: 0.85rem; /* Further reduce font size on very small screens */
    padding: 6px 12px;
  }

}

</style>
