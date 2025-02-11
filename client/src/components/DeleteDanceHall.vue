<template>
    <div v-if="isAdmin">
      <button @click="deleteDanceHall">Delete Dance Hall</button>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    computed: {
      isAdmin() {
        const user = JSON.parse(localStorage.getItem('user'));
        return user && user.role === 'ADMIN';
      }
    },
    methods: {
      async deleteDanceHall() {
        const danceHallId = this.$route.params.id;
        try {
          await axios.delete(`http://localhost:9000/dancehalls/${danceHallId}`, {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('token')}`
            }
          });
          console.log('Dance Hall Deleted');
          this.$router.push('/dancehalls');
        } catch (error) {
          console.error('Error deleting dance hall:', error);
        }
      }
    }
  };
  </script>
  