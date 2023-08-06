<template>
  <table>
    <tr  >
      <v-btn v-for="tecla in teclas[0]">
      {{ tecla }}
      </v-btn>
    </tr>
    <tr>
      <v-btn v-for="tecla in teclas[1]">
      {{ tecla }}
      </v-btn>
    </tr>
    <tr>
      <v-btn v-for="tecla in teclas[2]">
      {{ tecla }}
      </v-btn>
    </tr>
    <tr>
      <v-btn v-for="tecla in teclas[3]">
      {{ tecla }}
      </v-btn>
    </tr>
  </table>

  </template>
  <script setup lang="ts">
    var teclas:string [][]= [['7','8','9'],['4','5','6'],['1','2','3'],['','0','']]

  </script>