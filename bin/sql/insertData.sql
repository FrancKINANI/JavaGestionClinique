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

-- Informations specifiques aux schedulers
INSERT INTO schedulers (utilisateur_id, fonction) VALUES
(6, 'Gestion des rendez-vous');

-- Créneaux horaires disponibles
INSERT INTO time_slots (debut, fin, statut) VALUES
-- Créneaux du Dr Legrand (Cardiologie)
('2023-12-01 09:00:00', '2023-12-01 09:30:00', 'DISPONIBLE'),
('2023-12-01 10:00:00', '2023-12-01 10:30:00', 'DISPONIBLE'),
('2023-12-01 11:00:00', '2023-12-01 11:30:00', 'DISPONIBLE'),

-- Créneaux du Dr Petit (Dermatologie)
('2023-12-01 14:00:00', '2023-12-01 14:30:00', 'DISPONIBLE'),
('2023-12-01 15:00:00', '2023-12-01 15:30:00', 'DISPONIBLE'),

-- Créneaux déjà réservés
('2023-12-02 09:00:00', '2023-12-02 09:30:00', 'RESERVE'),
('2023-12-02 10:00:00', '2023-12-02 10:30:00', 'RESERVE');

-- Rendez-vous à venir
INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, patient_id, time_slot_id, salle_id, scheduler_id, docteur_id)
VALUES
  (NOW(), 'PLANIFIE', 'Première consultation cardiologie', 30, 1, 6, 1, 1, 4),
  (NOW(), 'CONFIRME', 'Suivi dermatologique', 30, 2, 7, 3, 1, 2);

-- Rendez-vous passés  
INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, patient_id, time_slot_id, salle_id, scheduler_id, docteur_id)
VALUES
  ('2023-11-15 10:00:00', 'TERMINE', 'Contrôle annuel', 30, 3, 8, 2, 1, 5),
  ('2023-11-20 14:30:00', 'TERMINE', 'Examen de routine', 30, 1, 9, 1, 1, 4);

-- Rendez-vous annulés
INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, patient_id, time_slot_id, salle_id, scheduler_id, docteur_id)
VALUES
  ('2023-11-10 11:00:00', 'ANNULE', 'Patient a reporté', 30, 2, 10, NULL, 1, 1),
  ('2023-11-12 15:00:00', 'ANNULE', 'Patient a annulé', 30, 3, 11, NULL, 1, 1);

  -- Rendez-vous reportés
  INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, patient_id, time_slot_id, salle_id, scheduler_id, docteur_id)
  VALUES
  ('2023-11-18 10:00:00', 'REPORTE', 'Patient a demandé un report', 30, 1, 12, NULL, 1, 1),
  ('2023-11-22 14:30:00', 'REPORTE', 'Patient a demandé un report', 30, 2, 13, NULL, 1, 1);
  
  -- Rendez-vous en cours
  INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, patient_id, time_slot_id, salle_id, scheduler_id, docteur_id)
  VALUES
  ('2023-11-25 10:00:00', 'EN_COURS', 'Consultation en cours', 30, 3, 14, NULL, 1, 1),
  ('2023-11-26 14:30:00', 'EN_COURS', 'Consultation en cours', 30, 1, 15, NULL, 1, 1);
  
  -- Rendez-vous confirmés
  
  INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, patient_id, time_slot_id, salle_id, scheduler_id, docteur_id)
  VALUES
  ('2023-11-28 10:00:00', 'CONFIRME', 'Consultation confirmée', 30, 2, 16, NULL, 1, 1),
  ('2023-11-29 14:30:00', 'CONFIRME', 'Consultation confirmée', 30, 3, 17, NULL, 1, 1);
  -- Rendez-vous planifiés
  INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, patient_id, time_slot_id, salle_id, scheduler_id, docteur_id)
  VALUES
  ('2023-11-30 10:00:00', 'PLANIFIE', 'Consultation planifiée', 30, 1, 18, NULL, 1, 1),
  ('2023-12-01 14:30:00', 'PLANIFIE', 'Consultation planifiée', 30, 2, 19, NULL, 1, 1);
  -- Rendez-vous en attente	
  INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, patient_id, time_slot_id, salle_id, scheduler_id, docteur_id)
  VALUES
  ('2023-12-02 10:00:00', 'EN_ATTENTE', 'Consultation en attente', 30, 3, 20, NULL, 1, 1),
  ('2023-12-03 14:30:00', 'EN_ATTENTE', 'Consultation en attente', 30, 1, 21, NULL, 1, 1);
  -- Rendez-vous en attente de confirmation
  INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, patient_id, time_slot_id, salle_id, scheduler_id, docteur_id)
  VALUES
  ('2023-12-04 10:00:00', 'EN_ATTENTE_CONFIRMATION', 'Consultation en attente de confirmation', 30, 2, 22, NULL, 1, 1),
  ('2023-12-05 14:30:00', 'EN_ATTENTE_CONFIRMATION', 'Consultation en attente de confirmation', 30, 3, 23, NULL, 1, 1);
  