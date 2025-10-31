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

# Increase Node memory for large builds (optional but recommended)
ENV NODE_OPTIONS="--max-old-space-size=4096"

# Build the Angular app (use --configuration production instead of deprecated --prod)
RUN npm run build -- --configuration production

# -------------------------
# Stage 2: Serve with Nginx
# -------------------------
FROM nginx:alpine

# Copy Angular build output (update path to actual dist folder name)
COPY --from=build /app/dist/food-frontend /usr/share/nginx/html

# Copy custom Nginx config (optional)
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
