db = db.getSiblingDB('e22102349'); // création base e22102349

db.createCollection('Manager'); // collection Manager (table)

// données générique
db.Manager.insertMany([
  { id: 1, email: "alice.dupont@example.com", nom: "Dupont", prenom: "Alice" },
  { id: 2, email: "bob.martin@example.com", nom: "Martin", prenom: "Bob" },
  { id: 3, email: "charlie.durand@example.com", nom: "Durand", prenom: "Charlie" },
  { id: 4, email: "diane.leroy@example.com", nom: "Leroy", prenom: "Diane" },
  { id: 5, email: "edouard.perrin@example.com", nom: "Perrin", prenom: "Edouard" },
  { id: 6, email: "francois.bernard@example.com", nom: "Bernard", prenom: "François" },
  { id: 7, email: "gabrielle.moreau@example.com", nom: "Moreau", prenom: "Gabrielle" },
  { id: 8, email: "hugo.renard@example.com", nom: "Renard", prenom: "Hugo" },
  { id: 9, email: "isabelle.roux@example.com", nom: "Roux", prenom: "Isabelle" },
  { id: 10, email: "julien.faure@example.com", nom: "Faure", prenom: "Julien" }
]);