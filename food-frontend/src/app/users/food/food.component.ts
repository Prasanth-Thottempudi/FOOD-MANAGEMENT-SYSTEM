import { Component, AfterViewInit, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.css'],
})
export class FoodComponent implements AfterViewInit {
  searchQuery: string = '';

  @ViewChild('offerList') offerList!: ElementRef;

  offers = [
    {
      img: '../assets/off.png',
      title: '50% Off',
    },
    {
      img: '../assets/buyonegetone.png',
      title: 'Buy 1 Get 1',
    },
    {
      img: '../assets/dessert.png',
      title: 'Free Dessert',
    },
  ];

  foods = [
    {
      img: '../assets/fooditems/biryani.png',
      name: 'Hyderabadi Biryani',
      description: 'Aromatic spiced rice with tender meat or vegetables',
      price: 250,
    },
    {
      img: '../assets/fooditems/panneertikkamasala.png',
      name: 'Paneer Butter Masala',
      description: 'Creamy tomato curry with soft paneer cubes',
      price: 220,
    },
    {
      img: '../assets/fooditems/chickentickenmasala.png',
      name: 'Chicken Tikka Masala',
      description: 'Grilled chicken cooked in spicy tomato gravy',
      price: 280,
    },
    {
      img: '../assets/fooditems/masaladosa.png',
      name: 'Masala Dosa',
      description: 'Crispy dosa filled with spiced potato masala',
      price: 120,
    },
    {
      img: '../assets/fooditems/idlisambar.png',
      name: 'Idli with Sambar',
      description: 'Soft steamed rice cakes with lentil stew',
      price: 100,
    },
    {
      img: '../assets/fooditems/chole.png',
      name: 'Chole Bhature',
      description: 'Spicy chickpea curry with fluffy fried bread',
      price: 180,
    },
    {
      img: '../assets/fooditems/Roganjosh.png',
      name: 'Rogan Josh',
      description: 'Kashmiri lamb curry rich in spices and flavor',
      price: 300,
    },
    {
      img: '../assets/fooditems/Dalmakani.png',
      name: 'Dal Makhani',
      description: 'Creamy lentil curry slow-cooked with butter and cream',
      price: 160,
    },
    {
      img: '../assets/fooditems/pavbhaji.png',
      name: 'Pav Bhaji',
      description: 'Spicy mashed vegetables served with buttered bread rolls',
      price: 140,
    },
    {
      img: '../assets/fooditems/alooparatha.png',
      name: 'Aloo Paratha',
      description: 'Stuffed flatbread with spicy potato filling',
      price: 100,
    },
    {
      img: '../assets/fooditems/pannertikka.png',
      name: 'Paneer Tikka',
      description: 'Grilled paneer cubes marinated in yogurt and spices',
      price: 200,
    },
    {
      img: '../assets/fooditems/vegetablepakora.png',
      name: 'Vegetable Pakora',
      description: 'Crispy deep-fried vegetable fritters',
      price: 90,
    },
    {
      img: '../assets/fooditems/samosa.png',
      name: 'Samosa',
      description: 'Fried pastry filled with spicy potatoes and peas',
      price: 60,
    },
    {
      img: '../assets/fooditems/butternan.png',
      name: 'Butter Naan',
      description: 'Soft buttery Indian bread perfect with curries',
      price: 80,
    },
    {
      img: '../assets/fooditems/jeerarice.png',
      name: 'Jeera Rice',
      description: 'Fragrant basmati rice flavored with cumin seeds',
      price: 120,
    },
  ];

  categories = [
    { img: '../assets/biryani.png', name: 'Biryani' },
    { img: '../assets/pizza.png', name: 'Pizza' },
    { img: '../assets/rolls.png', name: 'Rolls' },
    {
      img: '../assets/shawarma.png',
      name: 'Shawarma',
    },
    { img: '../assets/categories/chinese.png', name: 'Chinese' },
    {
      img: '../assets/biryani.png',
      name: 'North Indian',
    },
    {
      img: '../assets/biryani.png',
      name: 'North Indian',
    },
    {
      img: '../assets/biryani.png',
      name: 'North Indian',
    },
    {
      img: '../assets/noodles.png',
      name: 'Noodles',
    },
  ];

  filters = ['All', 'Veg', 'Non-Veg', 'Spicy'];
  sorters = ['Price Low-High', 'Price High-Low', 'Rating'];

  selectedFilter = 'All';
  selectedSort = 'Price Low-High';

  ngAfterViewInit() {
    this.autoScrollOffers();
  }

  autoScrollOffers() {
    let scrollIndex = 0;
    const totalScrolls = this.offers.length;
    setInterval(() => {
      if (this.offerList) {
        const scrollWidth =
          this.offerList.nativeElement.scrollWidth / totalScrolls;
        scrollIndex = (scrollIndex + 1) % totalScrolls;
        this.offerList.nativeElement.scrollTo({
          left: scrollWidth * scrollIndex,
          behavior: 'smooth',
        });
      }
    }, 5000); // scroll every 5 seconds
  }

  restaurants = [
    {
      img: '../assets/restaurents/spicegarden.png',
      name: 'Spice Garden',
      location: 'Banjara Hills, Hyderabad',
      rating: 4.5,
    },
    {
      img: '../assets/restaurents/RoyalBiryanihouse.png',
      name: 'Royal Biryani House',
      location: 'Madhapur, Hyderabad',
      rating: 4.7,
    },
    {
      img: '../assets/restaurents/curryleaf.png',
      name: 'Curry Leaf',
      location: 'Kukatpally, Hyderabad',
      rating: 4.3,
    },
    {
      img: '../assets/restaurents/tandoortales.png',
      name: 'Tandoor Tales',
      location: 'Gachibowli, Hyderabad',
      rating: 4.6,
    },
    {
      img: '../assets/restaurents/tasteofindia.png',
      name: 'Taste of India',
      location: 'Ameerpet, Hyderabad',
      rating: 4.4,
    },
    {
      img: '../assets/restaurents/biryanipoint.png',
      name: 'Biryani Point',
      location: 'Kondapur, Hyderabad',
      rating: 4.8,
    },
  ];
}
