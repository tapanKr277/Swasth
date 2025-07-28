let env = "dev";
let API_BASE_URL;
let CHANNEL_URL;
if (env === "docker") {
  // Running inside a Docker container
  API_BASE_URL = "http://localhost:5173";
  CHANNEL_URL = "localhost:5173";
} else if (env === "prod") {
  // Production environment
  // API_BASE_URL = "http://49.50.85.157:5173";
  // CHANNEL_URL = "49.50.85.157:5173";
  API_BASE_URL = "http://127.0.0.1:5173";
  CHANNEL_URL = "127.0.0.1:5173";
} else if (env === "dev") {
  // Development environment
  API_BASE_URL = "http://127.0.0.1:5173";
  CHANNEL_URL = "127.0.0.1:5173";
}
export { API_BASE_URL, CHANNEL_URL };
