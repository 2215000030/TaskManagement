import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import API from "../api/axios";

export default function Dashboard() {
  const navigate = useNavigate();

  const [counts, setCounts] = useState({
    total: 0,
    completed: 0,
    incomplete: 0,
    notifications: 0,
    overdue: 0, // ✅ ADDED
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      // Fetch tasks
      const taskRes = await API.get("/Task/tasks");
      const tasks = taskRes.data.data || [];

      const total = tasks.length;
      const completed = tasks.filter(
        (t) => t.status === "COMPLETED"
      ).length;
      const incomplete = tasks.filter(
        (t) => t.status === "INCOMPLETE"
      ).length;

      // Fetch notifications
      const notifRes = await API.get("/Notification/all");
      const notifications = notifRes.data.count || 0;

      // ✅ FETCH OVERDUE FROM BACKEND
      const overdueRes = await API.get("/Task/overdue");
      const overdueTasks = overdueRes.data.data || [];

      setCounts({
        total,
        completed,
        incomplete,
        notifications,
        overdue: overdueTasks.length, // ✅ ADDED
      });
    } catch (err) {
      console.log("Error fetching dashboard data");
    }
  };

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold mb-8">Dashboard 🚀</h1>

      <div className="grid md:grid-cols-5 gap-6">

        {/* All Tasks */}
        <div
          onClick={() => navigate("/tasks")}
          className="bg-white p-6 rounded-xl shadow hover:shadow-lg cursor-pointer transition"
        >
          <h2 className="text-xl font-semibold">📋 All Tasks</h2>
          <p className="text-3xl font-bold mt-2 text-blue-600">
            {counts.total}
          </p>
        </div>

        {/* Notifications */}
        <div
          onClick={() => navigate("/notifications")}
          className="bg-white p-6 rounded-xl shadow hover:shadow-lg cursor-pointer transition"
        >
          <h2 className="text-xl font-semibold">🔔 Notifications</h2>
          <p className="text-3xl font-bold mt-2 text-yellow-500">
            {counts.notifications}
          </p>
        </div>

        {/* Completed */}
        <div
          onClick={() => navigate("/tasks?status=COMPLETED")}
          className="bg-green-100 p-6 rounded-xl shadow hover:shadow-lg cursor-pointer transition border-l-4 border-green-500"
        >
          <h2 className="text-xl font-semibold text-green-700">
            🟢 Completed Tasks
          </h2>
          <p className="text-3xl font-bold mt-2 text-green-700">
            {counts.completed}
          </p>
        </div>

        {/* Incomplete */}
        <div
          onClick={() => navigate("/tasks?status=INCOMPLETE")}
          className="bg-red-100 p-6 rounded-xl shadow hover:shadow-lg cursor-pointer transition border-l-4 border-red-500"
        >
          <h2 className="text-xl font-semibold text-red-700">
            🔴 Incomplete Tasks
          </h2>
          <p className="text-3xl font-bold mt-2 text-red-700">
            {counts.incomplete}
          </p>
        </div>

        {/* 🔥 OVERDUE (NEW CARD) */}
        <div
          onClick={() => navigate("/tasks?status=OVERDUE")}
          className="bg-red-200 p-6 rounded-xl shadow hover:shadow-lg cursor-pointer transition border-l-4 border-red-700"
        >
          <h2 className="text-xl font-semibold text-red-800">
            🚨 Overdue Tasks
          </h2>
          <p className="text-3xl font-bold mt-2 text-red-800">
            {counts.overdue}
          </p>
        </div>

      </div>
    </div>
  );
}