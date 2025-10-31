# -------------------------
# Stage 1: Build Angular App
# -------------------------
FROM node:18 AS build

# Set working directory
WORKDIR /app

# Copy dependency files
COPY ./food-frontend/package*.json ./
RUN npm ci --legacy-peer-deps

# Copy the rest of the Angular project
COPY ./food-frontend .

# Allow Angular more memory (prevents OOM)
ENV NODE_OPTIONS="--max-old-space-size=4096"

# ✅ Build Angular for production
# Try new Angular build syntax; fallback to old one if project uses older CLI
RUN npm run build -- --configuration production || npm run build --prod

# -------------------------
# Stage 2: Serve with Nginx
# -------------------------
FROM nginx:alpine

# ✅ Correct dist path from your angular.json ("dist/application")
COPY --from=build /app/dist/application /usr/share/nginx/html

# Optional: custom Nginx config
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
