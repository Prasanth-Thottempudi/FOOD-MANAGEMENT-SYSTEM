import { environment } from 'src/environments/environment';

const BASE_URL = environment.apiBaseUrl;

export const ApiEndpoints = {
  AUTH: {
    LOGIN: `${BASE_URL}/auth/login`,
    REGISTER: `${BASE_URL}/auth/register`,
    LOGOUT: `${BASE_URL}/auth/logout`,
  },
  USER: {
    GET_PROFILE: `${BASE_URL}/users/profile`,
    UPDATE_PROFILE: `${BASE_URL}/users/update`,
    ALL_USERS: `${BASE_URL}/users`,
  },
  FOOD: {
    ALL_ITEMS: `${BASE_URL}/food/items`,
    ADD_ITEM: `${BASE_URL}/food/add`,
    ITEM_BY_ID: (id: number) => `${BASE_URL}/food/${id}`,
  },
  CART: {
    GET_CART: (userId: number) => `${BASE_URL}/cart/${userId}`,
    ADD_TO_CART: `${BASE_URL}/cart/add`,
    REMOVE_ITEM: `${BASE_URL}/cart/remove`,
  },
  ORDER: {
    CREATE_ORDER: `${BASE_URL}/orders/create`,
    USER_ORDERS: (userId: number) => `${BASE_URL}/orders/user/${userId}`,
  },
};
