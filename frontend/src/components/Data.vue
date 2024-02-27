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
        <div class="text-subtitle-1 text-medium-emphasis">Data</div>

        <div class="text-subtitle-1 text-medium-emphasis">Revolutions</div>

        <BarChart
          :dataset=data
          :lables=labels
        />
  
      </v-card>
    </div>
  </template>
  <script lang="ts" setup>
    import mqtt from "mqtt";
import { ref } from "vue";
import BarChart from './charts/BarChart.vue';

    const visible = ref(false);
    const mqserver = mqtt.connect({ port: 1883, host: '127.0.0.1', keepalive: 10000});
    const labels = ['test1','test2'];
    const data = [5,10];

    var username:string = '';
    var password:string = '';
    let credentials = [{user:'1',password:'1'}];

    mqserver.on('connect',() => {
      console.log("Connected to mqtt server");
    });

</script>