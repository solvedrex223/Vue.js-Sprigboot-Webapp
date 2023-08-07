<template>
  <v-snackbar v-model="alert">
    Order completed. Total: ${{ orderTotal }}
    <template v-slot:actions>
      <v-btn color="red" variant="text" @click="alert = false">
        Close
      </v-btn>
    </template>
  </v-snackbar>
  <div>
    <v-container>
      <v-row>
        <v-col cols="12" md="6" lg="4" v-for="item in items" :key="item.id">
          <v-card>
            <v-img :src="item.img" alt="Product Image" style="height:75px"></v-img>
            <v-card-title>{{ item.name }}</v-card-title>
            <v-card-subtitle>${{ item.price.toFixed(2) }}</v-card-subtitle>
            <v-btn @click="addToCart(item.id)" color="orange">Add to List</v-btn>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <v-container>
      <div name="Search">
        <v-text-field v-model="searchId" label="Enter an ID" style="margin: 50px;"></v-text-field>
        <v-btn @click="addToCart(searchId)" color="orange" style="margin-left: 50px">Add by ID to List</v-btn>
      </div>

      <div name="Lista de Compras">
        <v-card class="mx-auto" max-width="200">
          <v-navigation-drawer app>
            <v-list height="95%">
              <v-list-item v-for="cartItem in cartItems" :key="cartItem.id">
                <v-list-item-title>{{ cartItem.name }}</v-list-item-title>
                <v-list-item-subtitle>${{ cartItem.price.toFixed(2) }}</v-list-item-subtitle>
              </v-list-item>
            </v-list>
            <v-btn @click="buyCart(cartItems)">
              Buy
            </v-btn>
          </v-navigation-drawer>
        </v-card>
      </div>
    </v-container>
  </div>
</template>

<script lang="ts" async setup>
import { Order, DefaultService as api } from '@/generated/index'
import { ref } from 'vue';

var searchId: '';
const cart: { name?: string | undefined, id?: number | undefined, price?: number | undefined, img?: string | undefined }[] = [];
const cartItems = ref(cart);
const items = await getItems();
var orderTotal: number = 0;
const alert = ref(false);

function addToCart(itemId: number) {
  const selectedItem = items.find(item => item.id === itemId);
  if (selectedItem) {
    cart.push(selectedItem);
    cartItems.value = cart;
  }
}

async function getItems() {
  const res = await api.getAllItems();
  return Array.from(res.values());
}

async function buyCart(cartItems: { name?: string | undefined, id?: number | undefined, price?: number | undefined, img?: string | undefined }[]) {
  var items: number[] = [];
  cartItems.forEach(item => {
    items.push(item.price!);
  });
  const order: Order = { prices: items };
  const res = await api.calcOrder(order);
  orderTotal = res;
  alert.value = true;
}
</script>