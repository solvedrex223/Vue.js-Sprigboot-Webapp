<template>
    <div>
      <!--<v-img
        class="mx-auto my-6"
        max-width="228"
        src="https://cdn.vuetifyjs.com/docs/images/logos/vuetify-logo-v3-slim-text-light.svg"
      ></v-img>-->
      <br/><br/>
  
      <v-card
        class="mx-auto pa-12 pb-8"
        elevation="8"
        max-width="448"
        rounded="lg"
      >
        <div class="text-subtitle-1 text-medium-emphasis">Account</div>
  
        <v-text-field
          id="user"
          density="compact"
          placeholder="Email address"
          prepend-inner-icon="mdi-email-outline"
          variant="outlined"
          @update:model-value="username = $event"
        ></v-text-field>
  
        <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          Password
        </div>
  
        <v-text-field
          id="password"
          :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="visible ? 'text' : 'password'"
          density="compact"
          placeholder="Enter your password"
          prepend-inner-icon="mdi-lock-outline"
          variant="outlined"
          @click:append-inner="visible = !visible"
          @update:model-value="password = $event"
        ></v-text-field>
  
        <v-btn
          block
          class="mb-8"
          color="orange"
          size="large"
          variant="tonal"
          @click="checkLogin([username,password])"
        >
          Log In
        </v-btn>
  
      </v-card>
    </div>
  </template>
  <script lang="ts" setup>
    import { ref } from "vue";
    import {LoginCredentials, DefaultService as api} from '@/generated/index' 
    import router from "@/router";

    const visible = ref(false);
    var username:string = '';
    var password:string = '';

    function checkLogin(params:string[]) {
          var login:LoginCredentials = {username:params[0],password:params[1]};
          try {
            api.authLogin(login);
            router.push('/terminal');
          } catch (error) {
            console.log('Invalid Login');
          }
    }
</script>