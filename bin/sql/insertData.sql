-- Insertion des salles
INSERT INTO salles (numero, etage, capacite) VALUES
(101, 1, 2),
(102, 1, 2),
(201, 2, 3),
(202, 2, 1),
(301, 3, 4);

-- Insertion des utilisateurs (patients, docteurs, etc.)
INSERT INTO utilisateurs (nom, prenom, adresse, telephone, email, date_naissance, mot_de_passe) VALUES
-- Patients
('Dupont', 'Jean', '12 Rue de Paris', '0612345678', 'jean.dupont@email.com', '1985-05-15', SHA2('patient123', 256)),
('Martin', 'Sophie', '34 Avenue Victor Hugo', '0698765432', 'sophie.martin@email.com', '1990-11-22', SHA2('patient456', 256)),
('Bernard', 'Luc', '56 Boulevard Voltaire', '0678912345', 'luc.bernard@email.com', '1978-03-08', SHA2('patient789', 256)),

-- Docteurs
('Legrand', 'Pierre', '78 Rue de la République', '0645678912', 'pierre.legrand@email.com', '1975-07-30', SHA2('docteur123', 256)),
('Petit', 'Marie', '90 Avenue des Champs-Élysées', '0632145698', 'marie.petit@email.com', '1982-09-14', SHA2('docteur456', 256)),

-- Scheduler
('Durand', 'Alice', '11 Rue de la Paix', '0687654321', 'alice.durand@email.com', '1988-12-05', SHA2('scheduler123', 256)),

-- Admin
('Admin', 'Systeme', '1 Admin Street', '0600000000', 'admin@clinique.com', '1990-01-01', SHA2('admin1234', 256));

-- Attribution des rôles
INSERT INTO roles (utilisateur_id, role) VALUES
(1, 'PATIENT'),
(2, 'PATIENT'),
(3, 'PATIENT'),
(4, 'DOCTEUR'),
(5, 'DOCTEUR'),
(6, 'SCHEDULER'),
(7, 'ADMIN');

-- Informations spécifiques patients
INSERT INTO patients (utilisateur_id, numero_secu, dossier_medical) VALUES
(1, '185051234567891', 'Allergie aux pénicillines'),
(2, '290112345678902', 'Antécédents familiaux de diabète'),
(3, '378033456789013', 'Suivi cardiologique régulier');

-- Informations spécifiques docteurs
INSERT INTO docteurs (utilisateur_id, specialite, matricule) VALUES
(4, 'Cardiologie', 'CARD7654321'),
(5, 'Dermatologie', 'DERM1234567');

-- Créneaux horaires disponibles
INSERT INTO time_slots (debut, fin, docteur_id, statut) VALUES
-- Créneaux du Dr Legrand (Cardiologie)
('2023-12-01 09:00:00', '2023-12-01 09:30:00', 4, 'DISPONIBLE'),
('2023-12-01 10:00:00', '2023-12-01 10:30:00', 4, 'DISPONIBLE'),
('2023-12-01 11:00:00', '2023-12-01 11:30:00', 4, 'DISPONIBLE'),

-- Créneaux du Dr Petit (Dermatologie)
('2023-12-01 14:00:00', '2023-12-01 14:30:00', 5, 'DISPONIBLE'),
('2023-12-01 15:00:00', '2023-12-01 15:30:00', 5, 'DISPONIBLE'),

-- Créneaux déjà réservés
('2023-12-02 09:00:00', '2023-12-02 09:30:00', 4, 'RESERVE'),
('2023-12-02 10:00:00', '2023-12-02 10:30:00', 5, 'RESERVE');

-- Rendez-vous (les créneaux réservés correspondent à ces rendez-vous)
INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, patient_id, time_slot_id, salle_id) VALUES
-- Rendez-vous à venir
(NOW(), 'PLANIFIE', 'Première consultation cardiologie', 30, 1, 6, 1),
(NOW(), 'CONFIRME', 'Suivi dermatologique', 30, 2, 7, 3),

-- Rendez-vous passé
('2023-11-15 10:00:00', 'TERMINE', 'Contrôle annuel', 30, 3, 8, 2),
('2023-11-20 14:30:00', 'TERMINE', 'Examen de routine', 30, 1, 9, 1),

-- Rendez-vous annulé
('2023-11-10 11:00:00', 'ANNULE', 'Patient a reporté', 30, 2, 10, NULL);