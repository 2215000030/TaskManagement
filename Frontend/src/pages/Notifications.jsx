import { useEffect, useState } from "react";
import API from "../api/axios";

export default function Notifications() {
  const [data, setData] = useState({ count: 0, notifications: [] });

  const fetchNotifications = () => {
    API.get("/Notification/all")
      .then((res) => setData(res.data))
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    fetchNotifications();
  }, []);

  const markAsRead = async (id) => {
    try {
      await API.delete(`/Notification/${id}`);
      fetchNotifications();
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div className="p-8">
      <h2 className="text-2xl font-bold mb-6">
        Notifications ({data.count})
      </h2>

      {data.notifications.length === 0 ? (
        <p>No notifications</p>
      ) : (
        <div className="space-y-4">
          {data.notifications.map((n) => (
            <div
              key={n.id}
              className="p-4 rounded-lg text-white flex justify-between items-center animate-pulse bg-red-500 shadow-lg"
            >
              <p>{n.message}</p>

              <button
                onClick={() => markAsRead(n.id)}
                className="ml-4 bg-white text-red-500 px-3 py-1 rounded hover:bg-gray-200 cursor-pointer"
              >
                Mark as Read
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}