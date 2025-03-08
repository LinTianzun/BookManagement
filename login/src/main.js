import Vue from 'vue'
import App from './App.vue'
import { createPinia } from 'pinia'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router/index'
import VueRouter from 'vue-router'

Vue.config.productionTip = false

const pinia = createPinia()

Vue.use(ElementUI)
Vue.use(VueRouter)

new Vue({
  render: h => h(App),
  beforeCreate() {
    Vue.prototype.$bus = this
  },
  plugins: [pinia],
  router
}).$mount('#app')
