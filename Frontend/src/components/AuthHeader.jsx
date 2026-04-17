import { useNavigate } from "react-router-dom";

export default function AuthHeader() {
  const navigate = useNavigate();

  return (
    <div className="absolute top-0 left-0 w-full p-4">
      <h1
        onClick={() => navigate("/")}
        className="text-2xl font-bold text-white cursor-pointer"
      >
        TaskFlow 🚀
      </h1>
    </div>
  );
}