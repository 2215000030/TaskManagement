import { useState } from "react";
import API from "../api/axios";
import { useNavigate } from "react-router-dom";
import AuthHeader from "../components/AuthHeader";

export default function Signup() {
  const [data, setData] = useState({
    username: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleSignup = async () => {
    try {
      await API.post("/Public/signup", data);
      alert("Signup Successful ✅");
      navigate("/"); // go to login
    } catch (err) {
      alert(err.response?.data?.message || "Signup Failed");
    }
  };

  return (
    <div className="flex justify-center items-center h-screen bg-gradient-to-r from-green-400 to-blue-500">
     <AuthHeader />
      <div className="bg-white p-8 rounded-2xl shadow-xl w-80">
        <h2 className="text-2xl font-bold text-center mb-6">Sign Up 📝</h2>

        <input
          className="w-full border p-2 mb-4 rounded-lg"
          placeholder="Username"
          onChange={(e) => setData({ ...data, username: e.target.value })}
        />

        <input
          type="password"
          className="w-full border p-2 mb-4 rounded-lg"
          placeholder="Password"
          onChange={(e) => setData({ ...data, password: e.target.value })}
        />

        <button
          onClick={handleSignup}
          className="w-full bg-green-500 hover:bg-green-600 text-white py-2 rounded-lg"
        >
          Sign Up
        </button>

        {/* 🔁 Switch to Login */}
        <p className="text-sm text-center mt-4">
          Already have an account?{" "}
          <span
            onClick={() => navigate("/")}
            className="text-blue-600 cursor-pointer font-semibold"
          >
            Login
          </span>
        </p>
      </div>
    </div>
  );
}