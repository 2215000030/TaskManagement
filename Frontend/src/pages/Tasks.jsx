import { useEffect, useState } from "react";
import API from "../api/axios";
import TaskCard from "../components/TaskCard";
import { useSearchParams } from "react-router-dom";
import toast from "react-hot-toast";

export default function Tasks() {
  const [tasks, setTasks] = useState([]);
  const [filteredTasks, setFilteredTasks] = useState([]);
  const [searchParams] = useSearchParams();

  const [showCreateModal, setShowCreateModal] = useState(false);

  const [newTask, setNewTask] = useState({
    title: "",
    description: "",
    startDate: "",
    endDate: "",
  });

  const [filters, setFilters] = useState({
    title: "",
    description: "",
    startDate: "",
    endDate: "",
    status: "",
  });

  // 🚀 FETCH TASKS (UPDATED FOR OVERDUE)
  const fetchTasks = () => {
    const urlStatus = searchParams.get("status");

    // ✅ OVERDUE → BACKEND API
    if (urlStatus === "OVERDUE") {
      API.get("/Task/overdue").then((res) => {
        const data = res.data.data || [];
        setTasks(data);
        setFilteredTasks(data);
      });
      return;
    }

    // ✅ NORMAL TASKS
    API.get("/Task/tasks").then((res) => {
      const data = res.data.data || [];
      setTasks(data);
      setFilteredTasks(data);
    });
  };

  useEffect(() => {
    fetchTasks();
  }, [searchParams]);

  // 🔍 FILTER LOGIC (UNCHANGED except overdue removed safely)
  useEffect(() => {
    let temp = [...tasks];
    const urlStatus = searchParams.get("status");

    if (filters.title) {
      temp = temp.filter((t) =>
        t.title.toLowerCase().includes(filters.title.toLowerCase())
      );
    }

    if (filters.description) {
      temp = temp.filter((t) =>
        t.description
          ?.toLowerCase()
          .includes(filters.description.toLowerCase())
      );
    }

    if (filters.startDate) {
      temp = temp.filter(
        (t) => t.startDate?.slice(0, 10) === filters.startDate
      );
    }

    if (filters.endDate) {
      temp = temp.filter(
        (t) => t.endDate?.slice(0, 10) === filters.endDate
      );
    }

    if (filters.status) {
      temp = temp.filter((t) => t.status === filters.status);
    }

    // ❌ IMPORTANT FIX (OVERDUE HANDLED BY BACKEND NOW)
    if (urlStatus && urlStatus !== "OVERDUE") {
      temp = temp.filter((t) => t.status === urlStatus);
    }

    setFilteredTasks(temp);
  }, [filters, tasks, searchParams]);

  // ➕ CREATE TASK
  const handleCreate = async () => {
    try {
      await API.post("/Task/new", newTask);

      setShowCreateModal(false);

      setNewTask({
        title: "",
        description: "",
        startDate: "",
        endDate: "",
      });

      fetchTasks();
    } catch (err) {
      const message =
        err?.response?.data?.message || "Task creation failed";

      toast.error(message);
    }
  };

  return (
    <div className="p-8">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold">Your Tasks</h2>

        <button
          onClick={() => setShowCreateModal(true)}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          + Create Task
        </button>
      </div>

      {/* FILTER UI */}
      <div className="bg-white p-4 rounded-xl shadow mb-6 grid md:grid-cols-5 gap-4">
        <input
          className="border p-2 rounded"
          placeholder="Title"
          value={filters.title}
          onChange={(e) =>
            setFilters({ ...filters, title: e.target.value })
          }
        />

        <input
          className="border p-2 rounded"
          placeholder="Description"
          value={filters.description}
          onChange={(e) =>
            setFilters({ ...filters, description: e.target.value })
          }
        />

        <input
          type="date"
          className="border p-2 rounded"
          value={filters.startDate}
          onChange={(e) =>
            setFilters({ ...filters, startDate: e.target.value })
          }
        />

        <input
          type="date"
          className="border p-2 rounded"
          value={filters.endDate}
          onChange={(e) =>
            setFilters({ ...filters, endDate: e.target.value })
          }
        />

        <select
          className="border p-2 rounded"
          value={filters.status}
          onChange={(e) =>
            setFilters({ ...filters, status: e.target.value })
          }
        >
          <option value="">All</option>
          <option value="COMPLETED">Completed</option>
          <option value="INCOMPLETE">Incomplete</option>
        </select>
      </div>

      {/* TASK LIST */}
      {filteredTasks.length === 0 ? (
        <p>No tasks found</p>
      ) : (
        <div className="grid md:grid-cols-2 gap-6">
          {filteredTasks.map((t) => (
            <TaskCard key={t.id} task={t} refreshTasks={fetchTasks} />
          ))}
        </div>
      )}

      {/* CREATE MODAL */}
      {showCreateModal && (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex justify-center items-center">
          <div className="bg-white p-6 rounded-xl w-96 shadow-lg">
            <h2 className="text-xl font-bold mb-4">Create Task</h2>

            <input
              className="w-full border p-2 mb-3 rounded"
              placeholder="Title"
              value={newTask.title}
              onChange={(e) =>
                setNewTask({ ...newTask, title: e.target.value })
              }
            />

            <textarea
              className="w-full border p-2 mb-3 rounded"
              placeholder="Description"
              value={newTask.description}
              onChange={(e) =>
                setNewTask({ ...newTask, description: e.target.value })
              }
            />

            <input
              type="date"
              className="w-full border p-2 mb-3 rounded"
              value={newTask.startDate}
              onChange={(e) =>
                setNewTask({ ...newTask, startDate: e.target.value })
              }
            />

            <input
              type="date"
              className="w-full border p-2 mb-4 rounded"
              value={newTask.endDate}
              onChange={(e) =>
                setNewTask({ ...newTask, endDate: e.target.value })
              }
            />

            <div className="flex justify-end gap-3">
              <button
                onClick={() => setShowCreateModal(false)}
                className="px-4 py-1 bg-gray-300 rounded"
              >
                Cancel
              </button>

              <button
                onClick={handleCreate}
                className="px-4 py-1 bg-blue-500 text-white rounded"
              >
                Create
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}