import React from "react";
import "./Biens.css";

const biens = [
  {
    id: 1,
    nom: "Royal Residence",
    ville: "Riyadh, Saudi Arabia",
    prix: "22000",
    image: "/images/maison1.jpg",
  },
  {
    id: 2,
    nom: "Appartement",
    ville: "Kalaban coura",
    prix: "50000",
    image: "/images/salle à manger.jpg",
  },
  {
    id: 3,
    nom: "Pearl Villa",
    ville: "Dammam, Saudi Arabia",
    prix: "80000",
    image: "/images/maison3.jpg",
  },
  {
    id: 4,
    nom: "Emerald Heights",
    ville: "Medina, Saudi Arabia",
    prix: "62000",
    image: "/images/maison4.jpg",
  },
];

function Biens() {
  return (
    <div className="biens-container">
      <h2 className="section-title">🏡 Nearby to you</h2>

      <div className="biens-list">
        {biens.map((bien) => (
          <div className="bien-card" key={bien.id}>
            <img src={bien.image} alt={bien.nom} className="bien-image" />
            <div className="bien-info">
              <h3>{bien.nom}</h3>
              <p className="ville">{bien.ville}</p>
              <p className="prix">
                {bien.prix}
                <span>FCFA / mois</span>
              </p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Biens;