import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Home from "./pages/Home";
import Biens from "./pages/Biens";
import Clients from "./pages/Clients";
import Reservation from "./pages/Reservation";

function App() {
  return (
    <BrowserRouter>
      <Header /> {/* Barre de navigation globale */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/biens" element={<Biens />} />
        <Route path="/clients" element={<Clients />} />
        <Route path="/reservation" element={<Reservation />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;