<template>
  <div id="conBody" :class="{'signup':isSignup}">
    <div class="container">
      <span></span>
      <span></span>
      <span></span>


      <LoginForm />

      <RegisterForm />
    </div>
  </div>
</template>

<script>
  import LoginForm from './LoginForm';
  import RegisterForm from './RegisterForm';



  export default {
    name: 'LoginRegisterForm',
    components: {
      LoginForm,
      RegisterForm
    },
    data() {
      return {
        isSignup: false
      };
    },
    methods: {
      toggleForm() {
        this.isSignup = !this.isSignup;
      }
    },
    mounted() {
      this.$bus.$on('toggleForm', this.toggleForm)
    },
    beforeDestroy() {
      this.$bus.$off('toggleForm')
    },
  }
</script>

<style scoped>
  @import url("../../assets/css/loginRegister.css");
</style>