<template>
  <nav class="navbar">
    <div class="navbar-left">
      <router-link to="/" class="nav-button">Home</router-link>
      <router-link to="/dancehalls" class="nav-button">Explore Dance Halls</router-link>
      <router-link v-if="isAdmin" to="/admin" class="nav-button">Admin Dashboard</router-link>
      <router-link v-if="isManager" to="/manager" class="nav-button">Manager</router-link>
      <router-link v-if="isUser" to="/user-dashboard" class="nav-button">User Dashboard</router-link>
      <router-link to= "/about-us" class="nav-button">About Us</router-link>
      <router-link to= "/contact-us" class="nav-button">Contact Us</router-link>
    </div>

    <div class="navbar-right">
      <form @submit.prevent="handleSearch" class="search-form">
        <input v-model="searchQuery" type="text" placeholder="Search..." class="search-input" />
        <button type="submit" class="search-button">Go</button>
      </form>

      <div class="auth-buttons">
        <router-link v-if="!isLoggedIn" to="/login" class="nav-button">Login</router-link>
        <router-link v-if="isLoggedIn" to="/logout" @click.prevent="handleLogout" class="nav-button">Logout</router-link>
      </div>
    </div>

    <!-- Hamburger Icon -->
    <div class="hamburger" @click="toggleMenu">
      <div class="bar"></div>
      <div class="bar"></div>
      <div class="bar"></div>
    </div>

    <!-- Mobile Menu -->
    <div v-if="menuOpen" class="mobile-menu">
      <router-link to="/" class="nav-button">Home</router-link>
      <router-link to="/dancehalls" class="nav-button">Explore Dance Halls</router-link>
      <router-link v-if="isAdmin" to="/admin" class="nav-button">Admin Dashboard</router-link>
      <router-link v-if="isManager" to="/manager" class="nav-button">Manager</router-link>
      <router-link v-if="isUser" to="/user-dashboard" class="nav-button">User Dashboard</router-link>
      <router-link to= "/about-us" class="nav-button">About Us</router-link>
      <router-link to= "/contact-us" class="nav-button">Contact Us</router-link>
      <router-link v-if="!isLoggedIn" to="/login" class="nav-button">Login</router-link>
      <router-link v-if="isLoggedIn" to="/logout" @click.prevent="handleLogout" class="nav-button">Logout</router-link>

    </div>
  </nav>
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {
      searchQuery: "",
      menuOpen: false, // Track if the menu is open
    };
  },
  computed: {
    isAdmin() {
      const user = JSON.parse(localStorage.getItem('user'));
      return user && user.role === 'ROLE_ADMIN';
    },
    isManager() {
      const user = JSON.parse(localStorage.getItem('user'));
      return user && user.role === 'ROLE_MANAGER';
    },
    isUser() {
      const user = JSON.parse(localStorage.getItem('user'));
      return user && user.role === 'ROLE_USER';  // Check for regular user role
    },
    isLoggedIn() {
      return !!localStorage.getItem('token');
    },
  },
  methods: {
    handleSearch() {
      if (this.searchQuery.trim()) {
        this.$router.push({ path: '/dancehalls', query: { search: this.searchQuery } });
      }
    },
    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      this.$router.push('/login');
    },
    toggleMenu() {
      this.menuOpen = !this.menuOpen; // Toggle the mobile menu
    }
  },
};
</script>

<style scoped>
/* Navbar container styling */
.navbar {
  display: flex;
  justify-content: space-between;
  padding: 10px 20px;
  background-color: #ab663f;
  position: relative;
}

/* Left section (navigation links) */
.navbar-left {
  display: flex;
  gap: 10px;
}

/* Right section (search bar and auth buttons) */
.navbar-right {
  display: flex;
  align-items: center;
  gap: 10px;
}

/* Styling for navigation buttons */
.nav-button {
  padding: 10px 20px;
  background-color: #fff;
  color: #ab663f;
  text-decoration: none;
  border-radius: 5px;
  font-size: 1rem;
}

.nav-button:hover {
  background-color: #d1b49f;
}

/* Styling for the search bar */
.search-form {
  display: flex;
  align-items: center;
}

.search-input {
  padding: 5px 10px;
  font-size: 1rem;
  border-radius: 5px;
  border: none;
  margin-right: 10px;
}


/* Button*/
.search-button {
  padding: 5px 10px;
  background-color: #fff;
  color: #ab663f;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-button:hover {
  background-color: #d1b49f;
}

/* Hamburger Menu Icon */
.hamburger {
  display: none;
  flex-direction: column;
  justify-content: space-between;
  width: 30px;
  height: 20px;
  cursor: pointer;
}

.hamburger .bar {
  width: 100%;
  height: 3px;
  background-color: #fff;
}

/* Mobile Menu */
.mobile-menu {
  display: flex;
  flex-direction: column;
  gap: 10px;
  position: absolute;
  top: 60px; 
  left: 0;
  width: 100%;
  background-color: #ab663f;
  padding: 20px;
}

.mobile-menu .nav-button {
  text-align: left;
}

/* Media Queries for responsive layout */
@media(max-width: 1024px) {
  .navbar-left {
    display: none; /* Hide left side navbar items */
  }
  
  .hamburger {
    display: flex; /* Show hamburger menu */
  }

  .navbar-right {
    display: none; /* Hide right side navbar items */
  }
  
  .mobile-menu {
    display: flex;
  }
}
</style>
