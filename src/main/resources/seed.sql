USE dentalsoftdb;

INSERT INTO cabinet (id, nom, adresse, telephone, email)
VALUES (1, 'YOU SMILE', 'Rabat, Maroc', '0500000000', 'contact@yousmile.test');

INSERT INTO role (code, libelle) VALUES
('ADMIN','Administrateur'),('MEDECIN','Médecin'),('SECRETAIRE','Secrétaire');

-- Mots de passe fictifs : le hash sera remplacé par T03 (hachage réel)
INSERT INTO utilisateur (login, password_hash, nom, prenom, specialite) VALUES
('admin','A_REMPLACER_PAR_HASH','Admin','Fictif',NULL),
('dr.doe','A_REMPLACER_PAR_HASH','Doe','John','Chirurgien dentiste'),
('secretaire1','A_REMPLACER_PAR_HASH','Alami','Sara',NULL);

INSERT INTO utilisateur_role VALUES
(1,'ADMIN'),(2,'MEDECIN'),(3,'SECRETAIRE');