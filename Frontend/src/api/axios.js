import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080",
});

// Attach token
API.interceptors.request.use((req) => {
  const token = localStorage.getItem("token");

  if (token) {
    req.headers.Authorization = `Bearer ${token}`;
  }

  return req;
});

// 🚨 RESPONSE INTERCEPTOR
API.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      console.log("Token expired");

      // 🔥 Show Toast
      const toast = document.createElement("div");
      toast.innerText = "Session expired, please login again";

      toast.style.position = "fixed";
      toast.style.bottom = "20px";
      toast.style.right = "20px";
      toast.style.background = "#ef4444";
      toast.style.color = "white";
      toast.style.padding = "12px 20px";
      toast.style.borderRadius = "8px";
      toast.style.boxShadow = "0 4px 10px rgba(0,0,0,0.2)";
      toast.style.zIndex = "9999";
      toast.style.fontSize = "14px";

      document.body.appendChild(toast);

      // Remove token
      localStorage.removeItem("token");

      // ⏳ Delay then redirect
      setTimeout(() => {
        window.location.href = "/";
      }, 2000);
    }

    return Promise.reject(error);
  }
);

export default API;