# Use node:16 as the build stage
FROM node:22.14 as build

# Set the working directory for the frontend
WORKDIR /code/frontend/

# Copy package.json and package-lock.json to the correct location
COPY . /code/frontend/

# Install dependencies
RUN npm install


# Build the frontend
RUN npm run build

# Start a new stage with nginx:1.19
# FROM nginx:1.19

# # Copy the build files from the previous stage to nginx's web root
# COPY --from=build /code/frontend/build /usr/share/nginx/html

# # Create an 'nginx' directory inside the 'scripts' directory
# RUN mkdir -p /scripts/nginx

# # Optionally, copy nginx configuration files into the 'nginx' directory
# COPY ./scripts/ngnix/nginx-config.conf /scripts/nginx/

# 
# FROM nginx:stable-alpine

# Copy the built frontend files from the previous stage to Nginx's web root
# COPY --from=build /code/frontend/build /usr/share/nginx/html

# Copy your SSL certificate and key
# COPY --from=build /code/frontend/scripts/HTTPS_Certificate/ssl/cert.pem /etc/nginx/ssl/cert.pem
# COPY --from=build /code/frontend/scripts/HTTPS_Certificate/ssl/key.pem /etc/nginx/ssl/key.pem

# Copy your Nginx configuration file
# COPY --from=build /code/frontend/scripts/nginx/nginx-config.conf /etc/nginx/conf.d/default.conf

# Expose port 80 (HTTP)
# EXPOSE 443


# CMD ["nginx", "-g", "daemon off;"]
