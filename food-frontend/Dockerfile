# Stage 1: Build Angular app
FROM node:18 AS build
WORKDIR /app

# Copy package files and install dependencies
COPY ./food-frontend/package*.json ./
RUN npm install

# Copy the remaining frontend source code
COPY ./food-frontend .

# Build the Angular app
RUN npm run build --prod

# Stage 2: Serve with Nginx
FROM nginx:alpine

# Copy the custom Nginx config
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

# Copy the Angular build output (IMPORTANT: correct dist subfolder)
COPY --from=build /app/dist/food-frontend /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
