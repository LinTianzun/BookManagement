<template>
  <div id="app">
    <keep-alive include="Admin">

      <router-view />
    </keep-alive>
  </div>
</template>

<script>

  export default {
    name: 'App'
  }
</script>

<style>
  /* * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: "Inter", sans-serif;
  } */
</style>