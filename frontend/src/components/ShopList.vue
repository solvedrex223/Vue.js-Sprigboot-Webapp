<template>
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
        <v-card class="mx-auto" max-width="300">
          <v-navigation-drawer app>
            <v-list>
              <v-list-item v-for="cartItem in cartItems" :key="cartItem.id">
                <v-list-item-title>{{ cartItem.name }}</v-list-item-title>
                <v-list-item-subtitle>${{ cartItem.price.toFixed(2) }}</v-list-item-subtitle>
              </v-list-item>
            </v-list>
          </v-navigation-drawer>
        </v-card>
      </div>
    </v-container>
  </div>
</template>





<script lang="ts">
export default {
  data: () => ({
    items: [
    {
        "id": 1,
        "name": "Milk",
        "price": 2.0,
        "img": "https://static.vecteezy.com/system/resources/previews/017/340/365/original/transparent-glass-of-fresh-milk-png.png"
      },
      {
        "id": 2,
        "name": "Water",
        "price": 1.5,
        "img": "https://clipart-library.com/image_gallery2/Water-Bottle-PNG-Picture.png"
      },
      {
        "id": 3,
        "name": "Apple",
        "price": 0.6,
        "img": "https://static.vecteezy.com/system/resources/previews/008/848/360/original/fresh-apple-fruit-free-png.png"
      },
      {
        "id": 4,
        "name": "Meat",
        "price": 1.0,
        "img": "https://pngimg.com/d/beef_PNG5.png"
      },
      {
        "id": 5,
        "name": "Fish",
        "price": 1.2,
        "img": "https://freepngimg.com/thumb/fish/10-fish-png-image.png"
      },
      {
        "id": 6,
        "name": "Banana",
        "price": 0.5,
        "img": "https://static.vecteezy.com/system/resources/previews/008/848/350/original/fresh-yellow-banana-fruit-free-png.png"
      },
      {
        "id": 7,
        "name": "Pear",
        "price": 0.2,
        "img": "https://pngimg.com/d/pear_PNG3466.png"
      },
      {
        "id": 8,
        "name": "Mango",
        "price": 0.8,
        "img": "https://www.pngall.com/wp-content/uploads/2016/04/Mango-PNG-File.png"
      },
      {
        "id": 9,
        "name": "Chicken",
        "price": 1.4,
        "img": "https://pngimg.com/uploads/fried_chicken/fried_chicken_PNG14109.png"
      },
      {
        "id": 10,
        "name": "Tomato",
        "price": 0.4,
        "img": "https://upload.wikimedia.org/wikipedia/commons/9/9d/Tomato.png"
      },
      {
        "id": 11,
        "name": "Lettuce",
        "price": 0.5,
        "img": "https://i.pinimg.com/originals/60/16/91/6016911336b4930bb9eda15b99ffad36.png"
      },
      {
        "id": 12,
        "name": "Bread",
        "price": 0.8,
        "img": "https://static.vecteezy.com/system/resources/previews/013/643/137/original/bread-with-transparent-background-free-png.png"
      }
],
    searchId: '',
    cartItems: [],
  }),
  methods: {
    addToCart(itemId) {
      const selectedItem = this.items.find(item => item.id === parseInt(itemId));
      if (selectedItem) {
        this.cartItems.push(selectedItem);
      }
    },
  },
}
</script>