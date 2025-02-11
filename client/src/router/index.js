import { createRouter, createWebHistory } from 'vue-router';
import { useStore } from 'vuex';

// Import components for views
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import LandingPageView from '../views/LandingPageView.vue';
import DanceHallDetailsView from '../views/DanceHallDetailsView.vue';
import DanceHallsListView from '../views/DanceHallsListView.vue';
import AddDanceHall from '../components/AddDanceHall.vue';
import UpdateDanceHall from '../components/UpdateDanceHall.vue';
import DeleteDanceHall from '../components/DeleteDanceHall.vue';
import AdminDashboard from '../views/AdminDashboard.vue';
import UpdateUser from '../components/UpdateUser.vue';
import AboutUsView from '../views/AboutUsView.vue';
import ContactUsView from '../views/ContactUsView.vue';
import UserDashboardView from '../views/UserDashboardView.vue';
import AddReview from '../components/AddReview.vue';
import UserDetailsView from '../views/UserDetailsView.vue';

/**
 * Vue Router Setup
 * This file configures all the routes for the application.
 * It defines which component should be loaded for each URL path and handles route protection (authentication/authorization).
 */

const routes = [
  // ------------------------------
  // Public Routes (No Authentication Required)
  // ------------------------------
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false,  // No authentication needed
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false,  // No authentication needed
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false,  // No authentication needed
    }
  },
  {
    path: '/',
    name: 'LandingPage',
    component: LandingPageView,
    meta: {
      requiresAuth: false,  // No authentication needed
    }
  },
  {
    path: '/about-us',
    name: 'AboutUs',
    component: AboutUsView,
    meta: {
      requiresAuth: false,  // No authentication needed
    }
  },
  {
    path:'/contact-us',
    name: 'ContactUs',
    component: ContactUsView,
    meta: {
      requiresAuth: false,  // No authentication needed
    }
  },

  // ------------------------------
  // User Routes (Requires Authentication)
  // ------------------------------
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: true,  // Requires authentication
    }
  },
  {
    path: '/user-dashboard',
    name: 'UserDashboard',
    component: UserDashboardView,
    meta: {
      requiresAuth: true,  // Requires authentication
    }
  },
  {
    path: '/users/:id',
    name: 'user-details',
    component: UserDetailsView,
    meta: {
      requiresAuth: true,  // Requires authentication
    }
  },
  {
    path: '/users/:id/update',
    name: 'UpdateUser',
    component: UpdateUser,
    meta: {
      requiresAuth: true,  // Requires authentication
    }
  },

  // ------------------------------
  // Dance Hall Routes (Requires Authentication or Admin Role)
  // ------------------------------
  {
    path: '/dancehalls/:id',
    name: 'dancehall-details',
    component: DanceHallDetailsView,
    meta: {
      requiresAuth: false,  // No authentication needed for viewing details
    }
  },
  {
    path: '/dancehalls',
    name: 'dancehalls-list',
    component: DanceHallsListView,
    meta: {
      requiresAuth: false,  // No authentication needed
    }
  },
  {
    path: '/dancehalls/add',
    name: 'addDancehall',
    component: AddDanceHall,
    meta: {
      requiresAdmin: true,  // Requires admin role
    }
  },
  {
    path: '/dancehalls/:id/update',
    name: 'update-dancehall',
    component: UpdateDanceHall,
    meta: {
      requiresAdmin: true,  // Requires admin role
    }
  },
  {
    path: '/dancehalls/delete',
    name: 'delete-dancehall',
    component: DeleteDanceHall,
    meta: {
      requiresAdmin: true,  // Requires admin role
    }
  },

  // ------------------------------
  // Review Routes (Requires Authentication)
  // ------------------------------
  {
    path: '/add-review/:danceHallId',
    name: 'add-review',
    component: AddReview,
    meta: {
      requiresAuth: true,  // Requires authentication
    }
  },

  // ------------------------------
  // Admin Routes (Requires Admin Role)
  // ------------------------------
  {
    path: '/admin',
    name: 'admin-dashboard',
    component: AdminDashboard,
    meta: {
      requiresAdmin: true,  // Requires admin role
    }
  }
];

// Create the router instance
const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Global route guard for authentication and authorization checks
router.beforeEach((to) => {
  const store = useStore();

  // Check if the route requires authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
  
  // Check if the route requires admin privileges
  const requiresAdmin = to.matched.some(x => x.meta.requiresAdmin);

  // If the route requires authentication and the user is not logged in, redirect to login
  if (requiresAuth && !store.state.token) {
    return { name: 'login' };
  }

  // If the route requires admin role and the user is not an admin, redirect to login
  if (requiresAdmin && store.state.user?.role !== 'ROLE_ADMIN') {
    return { name: 'login' };
  }
});

// Export the router
export default router;
