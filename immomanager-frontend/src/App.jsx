import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Home from "./pages/Home";
import Biens from "./pages/Biens";
import Clients from "./pages/Clients";
import Reservation from "./pages/Reservation";
import Inscription from "./pages/S'inscrire";

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/biens" element={<Biens />} />
        <Route path="/clients" element={<Clients />} />
        <Route path="/reservation" element={<Reservation />} />
        <Route path="/inscription" element={<Inscription />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;