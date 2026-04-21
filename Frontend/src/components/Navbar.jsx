import { useState, useRef, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import toast from "react-hot-toast";

export default function Navbar() {
  const navigate = useNavigate();
  const username = localStorage.getItem("username") || "User";

  const [openMenu, setOpenMenu] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const menuRef = useRef();

  const [form, setForm] = useState({
    username: username,
    password: "",
  });

  // ✅ Close dropdown on outside click
  useEffect(() => {
    const handleClickOutside = (e) => {
      if (menuRef.current && !menuRef.current.contains(e.target)) {
        setOpenMenu(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    toast.success("Logged out successfully");
    navigate("/");
  };

  const handleUpdate = async () => {
    try {
      const token = localStorage.getItem("token");

      await axios.put(
        "/User",
        {
          username: form.username,
          password: form.password,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      localStorage.setItem("username", form.username);
      setShowModal(false);
      toast.success("Credentials updated");
    } catch (err) {
      toast.error("Update failed");
    }
  };

  const initial = username.charAt(0).toUpperCase();

  return (
    <div className="bg-white shadow-md px-6 py-4 flex justify-between items-center relative">

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

        {/* Avatar + Dropdown */}
        <div className="relative" ref={menuRef}>

          <div
            onClick={() => setOpenMenu(!openMenu)}
            className="w-10 h-10 flex items-center justify-center rounded-full bg-blue-600 text-white font-bold cursor-pointer hover:scale-105 transition"
          >
            {initial}
          </div>

          {openMenu && (
            <div className="absolute right-0 mt-3 w-48 bg-white border shadow-xl rounded-xl overflow-hidden z-50">

              <div className="px-4 py-3 border-b bg-gray-50">
                <p className="text-sm text-gray-500">Signed in as</p>
                <p className="font-semibold text-gray-800 truncate">
                  {username}
                </p>
              </div>

              <button
                onClick={() => {
                  setShowModal(true);
                  setOpenMenu(false);
                }}
                className="w-full text-left px-4 py-2 hover:bg-gray-100"
              >
                ⚙️ Update Credentials
              </button>

              <button
                onClick={handleLogout}
                className="w-full text-left px-4 py-2 hover:bg-red-100 text-red-500"
              >
                🚪 Logout
              </button>
            </div>
          )}
        </div>
      </div>

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex justify-center items-center z-50">

          <div className="bg-white p-6 rounded-xl w-80 shadow-lg">

            <h2 className="text-lg font-bold mb-4">
              Update Credentials
            </h2>

            <input
              type="text"
              placeholder="Username"
              className="w-full border p-2 mb-3 rounded"
              value={form.username}
              onChange={(e) =>
                setForm({ ...form, username: e.target.value })
              }
            />

            <input
              type="password"
              placeholder="Password"
              className="w-full border p-2 mb-4 rounded"
              value={form.password}
              onChange={(e) =>
                setForm({ ...form, password: e.target.value })
              }
            />

            <div className="flex justify-end gap-2">

              <button
                onClick={() => setShowModal(false)}
                className="px-3 py-1 bg-gray-300 rounded"
              >
                Cancel
              </button>

              <button
                onClick={handleUpdate}
                className="px-3 py-1 bg-blue-600 text-white rounded"
              >
                Update
              </button>

            </div>

          </div>
        </div>
      )}
    </div>
  );
}