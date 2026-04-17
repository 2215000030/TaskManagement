import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();

  const username = localStorage.getItem("username");

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    navigate("/");
  };

  return (
    <div className="bg-white shadow-md px-6 py-4 flex justify-between items-center">
      
      {/* Left */}
      <h1
        className="text-xl font-bold text-blue-600 cursor-pointer"
        onClick={() => navigate("/dashboard")}
      >
        TaskFlow 🚀
      </h1>

      {/* Right */}
      <div className="flex items-center gap-6">
        <Link to="/dashboard" className="hover:text-blue-600">
          Dashboard
        </Link>
        <Link to="/tasks" className="hover:text-blue-600">
          Tasks
        </Link>
        <Link to="/notifications" className="hover:text-blue-600">
          🔔
        </Link>

        {/* 👤 Username */}
        <div className="flex items-center gap-2 bg-gray-100 px-3 py-1 rounded-lg">
          <span className="text-gray-600">👤</span>
          <span className="font-semibold text-gray-800">
            {username || "User"}
          </span>
        </div>

        {/* Logout */}
        <button
          onClick={handleLogout}
          className="bg-red-500 hover:bg-red-600 text-white px-4 py-1 rounded-lg"
        >
          Logout
        </button>
      </div>
    </div>
  );
}