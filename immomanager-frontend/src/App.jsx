import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Home from "./pages/Home";
import Biens from "./pages/Biens";
import Clients from "./pages/Clients";
import Reservation from "./pages/Reservation";
import Inscription from "./pages/S'inscrire";
import Login from "./pages/Login";
import SuperAdminDashboard from "./pages/SuperAdminDashboard";
import AdminDashboard from "./pages/AdminDashboard";
import "./App.css";

function App() {
  return (
    <BrowserRouter>
      <Header />
      <div style={{ paddingTop: "72px" }}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/biens" element={<Biens />} />
          <Route path="/clients" element={<Clients />} />
          <Route path="/reservation" element={<Reservation />} />
          <Route path="/inscription" element={<Inscription />} />
          <Route path="/connexion" element={<Login />} />
          <Route path="/super-admin/dashboard" element={<SuperAdminDashboard />} />
          <Route path="/admin/dashboard" element={<AdminDashboard />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;