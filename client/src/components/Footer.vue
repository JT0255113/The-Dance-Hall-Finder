<template>
    <footer class="footer">
      <p>&copy; 2025 Dance Hall Finder. All rights reserved.</p>
    </footer>
  </template>
  
  <script>
  export default {
    name: "Footer"
  };
  </script>
  
  <style scoped>
  .footer {
    background-color: #333;
    color: white;
    text-align: center;
    padding: 1px;
    width: 100%;
    position: relative;
    bottom: 0;
  }
  
  .landing-page {
    display: flex;
    flex-direction: column;
    min-height: 100vh; 
  }
  
  .hero,
  .featured-dancehalls,
  .featured-events {
    flex: 1; 
  }
  </style>
  