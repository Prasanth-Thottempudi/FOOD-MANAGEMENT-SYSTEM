# Stage 1: Build Angular app
FROM node:20 AS build
WORKDIR /app
COPY ./food-frontend/package*.json ./
RUN npm install
COPY ./food-frontend .
RUN npm run build --prod

# Stage 2: Serve with Nginx
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
