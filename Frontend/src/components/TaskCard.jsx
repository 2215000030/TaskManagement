import { useState } from "react";
import API from "../api/axios";
import toast from "react-hot-toast";

export default function TaskCard({ task, refreshTasks }) {
  const [showModal, setShowModal] = useState(false);

  const [formData, setFormData] = useState({
    title: task.title,
    description: task.description,
    startDate: task.startDate?.slice(0, 10),
    endDate: task.endDate?.slice(0, 10),
    status: task.status || "INCOMPLETE",
  });

  const handleDelete = async () => {
    try {
      await API.delete(`/Task/task/${task.id}`);
      refreshTasks();
    } catch (err) {
     toast.error("Delete failed");
    }
  };

  const handleUpdate = async () => {
    try {
      await API.put(`/Task/task/${task.id}`, formData);
      setShowModal(false);
      refreshTasks();
    } catch (err) {
      toast.error(err.response?.data?.message);
    }
  };

  // =========================
  // STATUS LOGIC (FIXED)
  // =========================

  const isCompleted = task.status === "COMPLETED";

  const isOverdue =
    task.status !== "COMPLETED" &&
    new Date(task.endDate).setHours(0, 0, 0, 0) <
      new Date().setHours(0, 0, 0, 0);

  const isIncomplete = task.status === "INCOMPLETE" && !isOverdue;

  // =========================
  // STYLING LOGIC
  // =========================

  const cardStyle = isCompleted
    ? "bg-green-100 border-green-500"
    : isOverdue
    ? "bg-red-200 border-red-700"
    : "bg-red-100 border-red-300";

  const blinkClass = !isCompleted ? "animate-pulse" : "";

  return (
    <>
      {/* TASK CARD */}
      <div
        className={`p-5 rounded-xl shadow border-l-4 hover:shadow-lg transition ${cardStyle} ${blinkClass}`}
      >
        <h3 className="text-lg font-bold text-blue-700">
          {task.title}
        </h3>

        <p className="text-gray-700 mt-2">{task.description}</p>

        <div className="flex justify-between mt-4 text-sm text-gray-600">
          <span>{new Date(task.startDate).toLocaleDateString()}</span>
          <span>{new Date(task.endDate).toLocaleDateString()}</span>
        </div>

        {/* STATUS */}
        <div className="mt-3 flex items-center">
          <span
            className={`px-3 py-1 rounded text-sm font-semibold ${
              isCompleted
                ? "bg-green-500 text-white"
                : isOverdue
                ? "bg-red-700 text-white"
                : "bg-red-400 text-white"
            }`}
          >
            {task.status}
          </span>

          {/* OVERDUE LABEL */}
          {isOverdue && (
            <span className="ml-2 text-red-700 font-bold animate-pulse">
              OVERDUE
            </span>
          )}
        </div>

        {/* ACTION BUTTONS */}
        <div className="flex gap-3 mt-3">
          {/* EDIT */}
          <button
            onClick={() => {
              if (task.status !== "COMPLETED") {
                setShowModal(true);
              }
            }}
            disabled={task.status === "COMPLETED"}
            className={`font-medium cursor-pointer ${
              task.status === "COMPLETED"
                ? "text-gray-400 cursor-not-allowed"
                : "text-yellow-600 hover:text-yellow-800"
            }`}
          >
            Edit
          </button>

          {/* DELETE */}
          <button
            onClick={handleDelete}
            className="text-red-600 hover:text-red-800 font-medium cursor-pointer"
          >
            Delete
          </button>
        </div>
      </div>

      {/* MODAL */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-60 flex justify-center items-center z-50">
          <div className="bg-white p-6 rounded-xl w-96 shadow-lg">
            <h2 className="text-xl font-bold mb-4">Edit Task</h2>

            <input
              className="w-full border p-2 mb-3 rounded"
              value={formData.title}
              onChange={(e) =>
                setFormData({ ...formData, title: e.target.value })
              }
              placeholder="Title"
            />

            <textarea
              className="w-full border p-2 mb-3 rounded"
              value={formData.description}
              onChange={(e) =>
                setFormData({ ...formData, description: e.target.value })
              }
              placeholder="Description"
            />

            <input
              type="date"
              className="w-full border p-2 mb-3 rounded"
              value={formData.startDate}
              onChange={(e) =>
                setFormData({ ...formData, startDate: e.target.value })
              }
            />

            <input
              type="date"
              className="w-full border p-2 mb-3 rounded"
              value={formData.endDate}
              onChange={(e) =>
                setFormData({ ...formData, endDate: e.target.value })
              }
            />

            <select
              className="w-full border p-2 mb-4 rounded"
              value={formData.status}
              onChange={(e) =>
                setFormData({ ...formData, status: e.target.value })
              }
            >
              <option value="INCOMPLETE">Incomplete</option>
              <option value="COMPLETED">Completed</option>
            </select>

            <div className="flex justify-end gap-3">
              <button
                onClick={() => setShowModal(false)}
                className="px-4 py-1 bg-gray-300 rounded cursor-pointer"
              >
                Cancel
              </button>

              <button
                onClick={handleUpdate}
                className="px-4 py-1 bg-blue-500 text-white rounded cursor-pointer"
              >
                Save
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}