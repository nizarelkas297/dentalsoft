USE dentalsoftdb;

INSERT INTO cabinet (id, nom, adresse, telephone, email)
VALUES (1, 'YOU SMILE', 'Rabat, Maroc', '0500000000', 'contact@yousmile.test');

INSERT INTO role (code, libelle) VALUES
('ADMIN','Administrateur'),('MEDECIN','Médecin'),('SECRETAIRE','Secrétaire');

-- Hash fictifs : remplacés par de vrais hash dans T03
INSERT INTO utilisateur (login, password_hash, nom, prenom, specialite) VALUES
('admin','A_REMPLACER_PAR_HASH','Admin','Fictif',NULL),
('dr.doe','A_REMPLACER_PAR_HASH','Doe','John','Chirurgien dentiste'),
('secretaire1','A_REMPLACER_PAR_HASH','Alami','Sara',NULL);

INSERT INTO utilisateur_role VALUES (1,'ADMIN'),(2,'MEDECIN'),(3,'SECRETAIRE');

INSERT INTO acte (libelle, categorie, prix_base) VALUES
('Soin de carie','Soins',400.00),
('Détartrage','Prévention',300.00),
('Extraction','Chirurgie',450.00),
('Traitement canalaire','Endodontie',650.00),
('Blanchiment','Esthétique',900.00);

INSERT INTO medicament (nom, forme) VALUES
('Paracétamol 500 mg','Comprimé'),
('Amoxicilline 1 g','Comprimé'),
('Ibuprofène 400 mg','Comprimé');

INSERT INTO antecedent (libelle, categorie) VALUES
('Diabète','Maladie chronique'),
('Allergie à la pénicilline','Allergie'),
('Hypertension','Maladie chronique');