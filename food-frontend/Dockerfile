# -------------------------
# Stage 1: Build Angular App
# -------------------------
FROM node:18 AS build

# Set working directory
WORKDIR /app

# Copy package files and install dependencies
COPY ./food-frontend/package*.json ./
RUN npm ci --legacy-peer-deps

# Copy the rest of the Angular project
COPY ./food-frontend .

# Increase Node memory for large builds (recommended)
ENV NODE_OPTIONS="--max-old-space-size=4096"

# Build the Angular app in production mode
RUN npm run build -- --configuration production

# -------------------------
# Stage 2: Serve with Nginx
# -------------------------
FROM nginx:alpine

# Copy Angular build output
# 👇 Use the correct path from angular.json
COPY --from=build /app/dist/application /usr/share/nginx/html

# Copy custom Nginx config (optional)
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
