import { useState } from "react";
import API from "../api/axios";
import { useNavigate } from "react-router-dom";
import AuthHeader from "../components/AuthHeader";

export default function Login() {
  const [data, setData] = useState({ username: "", password: "" });
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await API.post("/Public/login", data);
      localStorage.setItem("token", res.data.data);
      localStorage.setItem("username", data.username); 
      navigate("/dashboard");
    } catch (err) {
      alert(err.response?.data?.message || "Login Failed");
    }
  };

  return (
    <div className="flex justify-center items-center h-screen bg-gradient-to-r from-blue-400 to-indigo-500">
     <AuthHeader />
     
      <div className="bg-white p-8 rounded-2xl shadow-xl w-80">
        <h2 className="text-2xl font-bold text-center mb-6">Login 🔐</h2>

        <input
          className="w-full border p-2 mb-4 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
          placeholder="Username"
          onChange={(e) => setData({ ...data, username: e.target.value })}
        />

        <input
          type="password"
          className="w-full border p-2 mb-4 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
          placeholder="Password"
          onChange={(e) => setData({ ...data, password: e.target.value })}
        />

        <button
          onClick={handleLogin}
          className="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-lg"
        >
          Login
        </button>

       

<p className="text-sm text-center mt-4">
  Don’t have an account?{" "}
  <span
    onClick={() => navigate("/signup")}
    className="text-blue-600 cursor-pointer font-semibold"
  >
    Sign Up
  </span>
</p>
      </div>
    </div>
  );
}