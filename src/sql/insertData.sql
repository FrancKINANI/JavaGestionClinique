USE gestion_rendez_vous;

-- Insertion des salles
INSERT INTO salles (numero, etage, capacite) VALUES
(101, 1, 3),
(102, 1, 2),
(103, 1, 5),
(201, 2, 4),
(202, 2, 2),
(301, 3, 6),
(302, 3, 3);

-- Insertion des personnes (avec leurs types)
-- Patients
INSERT INTO personnes (nom, prenom, adresse, telephone, email, date_naissance, type_personne, mot_de_passe) VALUES
('Dupont', 'Jean', '15 rue des Lilas, 75001 Paris', '0601020304', 'jean.dupont@email.com', '1980-05-15', 'PATIENT', SHA2('password123', 256)),
('Martin', 'Sophie', '27 avenue Victor Hugo, 69002 Lyon', '0607080910', 'sophie.martin@email.com', '1992-11-23', 'PATIENT', SHA2('password123', 256)),
('Dubois', 'Pierre', '8 boulevard Gambetta, 33000 Bordeaux', '0612131415', 'pierre.dubois@email.com', '1975-03-07', 'PATIENT', SHA2('password123', 256)),
('Lefebvre', 'Marie', '42 rue de la Paix, 59000 Lille', '0616171819', 'marie.lefebvre@email.com', '1988-09-12', 'PATIENT', SHA2('password123', 256)),
('Leroy', 'Thomas', '3 place de la République, 44000 Nantes', '0620212223', 'thomas.leroy@email.com', '1965-07-30', 'PATIENT', SHA2('password123', 256)),
('Moreau', 'Claire', '19 rue du Commerce, 13001 Marseille', '0624252627', 'claire.moreau@email.com', '1991-01-18', 'PATIENT', SHA2('password123', 256)),
('Simon', 'Lucas', '56 rue de la Liberté, 67000 Strasbourg', '0628293031', 'lucas.simon@email.com', '1983-12-05', 'PATIENT', SHA2('password123', 256));

-- Docteurs
INSERT INTO personnes (nom, prenom, adresse, telephone, email, date_naissance, type_personne, mot_de_passe) VALUES
('Bernard', 'Hélène', '10 rue Saint-Michel, 75006 Paris', '0632333435', 'helene.bernard@hopital.fr', '1978-04-22', 'DOCTEUR', SHA2('doctor123', 256)),
('Petit', 'Nicolas', '24 avenue des Ternes, 75017 Paris', '0636373839', 'nicolas.petit@hopital.fr', '1982-08-14', 'DOCTEUR', SHA2('doctor123', 256)),
('Richard', 'Catherine', '7 rue du Faubourg, 69003 Lyon', '0640414243', 'catherine.richard@hopital.fr', '1973-06-29', 'DOCTEUR', SHA2('doctor123', 256)),
('Robert', 'Philippe', '31 boulevard Haussmann, 75009 Paris', '0644454647', 'philippe.robert@hopital.fr', '1976-10-11', 'DOCTEUR', SHA2('doctor123', 256)),
('Michel', 'Isabelle', '14 place Bellecour, 69002 Lyon', '0648495051', 'isabelle.michel@hopital.fr', '1985-02-17', 'DOCTEUR', SHA2('doctor123', 256));

-- Schedulers
INSERT INTO personnes (nom, prenom, adresse, telephone, email, date_naissance, type_personne, mot_de_passe) VALUES
('Durand', 'Emilie', '5 rue des Capucines, 75002 Paris', '0652535455', 'emilie.durand@hopital.fr', '1990-07-19', 'SCHEDULER', SHA2('scheduler123', 256)),
('Laurent', 'Julien', '18 avenue Jean Jaurès, 69007 Lyon', '0656575859', 'julien.laurent@hopital.fr', '1987-05-25', 'SCHEDULER', SHA2('scheduler123', 256)),
('Garcia', 'Nathalie', '22 boulevard Voltaire, 75011 Paris', '0660616263', 'nathalie.garcia@hopital.fr', '1981-11-03', 'SCHEDULER', SHA2('scheduler123', 256));

-- Insertion des patients (référence aux personnes)
INSERT INTO patients (id, numero_secu, dossier_medical) VALUES
(1, '180055612345678', 'Allergie aux arachides, Hypertension légère'),
(2, '292116712345678', 'Aucun antécédent médical notable'),
(3, '175030712345678', 'Asthme, nécessite un inhalateur en cas de crise'),
(4, '288091212345678', 'Diabète de type 2, suivi régulier requis'),
(5, '165073012345678', 'Antécédents cardiaques familiaux, cholestérol élevé'),
(6, '291011812345678', 'Migraines chroniques, traitement préventif en cours'),
(7, '183120512345678', 'Fracture du poignet droit en 2022, bien consolidée');

-- Insertion des docteurs (référence aux personnes)
INSERT INTO docteurs (id, specialite, matricule) VALUES
(8, 'Cardiologie', 'DOC-001-CARD'),
(9, 'Dermatologie', 'DOC-002-DERM'),
(10, 'Pédiatrie', 'DOC-003-PED'),
(11, 'Neurologie', 'DOC-004-NEURO'),
(12, 'Ophtalmologie', 'DOC-005-OPHTA');

-- Insertion des schedulers (référence aux personnes)
INSERT INTO schedulers (id, fonction) VALUES
(13, 'Coordinateur rendez-vous'),
(14, 'Assistant médical'),
(15, 'Responsable planning');

-- Définition des créneaux horaires pour les docteurs (pour une semaine)
-- Docteur Cardiologie (id 8)
INSERT INTO time_slots (debut, fin, statut, docteur_id) VALUES
-- Lundi
('2025-05-05 08:30:00', '2025-05-05 09:00:00', 'DISPONIBLE', 8),
('2025-05-05 09:00:00', '2025-05-05 09:30:00', 'DISPONIBLE', 8),
('2025-05-05 09:30:00', '2025-05-05 10:00:00', 'DISPONIBLE', 8),
('2025-05-05 10:00:00', '2025-05-05 10:30:00', 'DISPONIBLE', 8),
('2025-05-05 10:30:00', '2025-05-05 11:00:00', 'DISPONIBLE', 8),
('2025-05-05 11:00:00', '2025-05-05 11:30:00', 'DISPONIBLE', 8),
-- Mardi
('2025-05-06 14:00:00', '2025-05-06 14:30:00', 'DISPONIBLE', 8),
('2025-05-06 14:30:00', '2025-05-06 15:00:00', 'DISPONIBLE', 8),
('2025-05-06 15:00:00', '2025-05-06 15:30:00', 'DISPONIBLE', 8),
('2025-05-06 15:30:00', '2025-05-06 16:00:00', 'DISPONIBLE', 8),
('2025-05-06 16:00:00', '2025-05-06 16:30:00', 'DISPONIBLE', 8),
('2025-05-06 16:30:00', '2025-05-06 17:00:00', 'DISPONIBLE', 8);

-- Docteur Dermatologie (id 9)
INSERT INTO time_slots (debut, fin, statut, docteur_id) VALUES
-- Mercredi
('2025-05-07 09:00:00', '2025-05-07 09:30:00', 'DISPONIBLE', 9),
('2025-05-07 09:30:00', '2025-05-07 10:00:00', 'DISPONIBLE', 9),
('2025-05-07 10:00:00', '2025-05-07 10:30:00', 'DISPONIBLE', 9),
('2025-05-07 10:30:00', '2025-05-07 11:00:00', 'DISPONIBLE', 9),
-- Jeudi
('2025-05-08 13:00:00', '2025-05-08 13:30:00', 'DISPONIBLE', 9),
('2025-05-08 13:30:00', '2025-05-08 14:00:00', 'DISPONIBLE', 9),
('2025-05-08 14:00:00', '2025-05-08 14:30:00', 'DISPONIBLE', 9),
('2025-05-08 14:30:00', '2025-05-08 15:00:00', 'DISPONIBLE', 9);

-- Docteur Pédiatrie (id 10)
INSERT INTO time_slots (debut, fin, statut, docteur_id) VALUES
-- Lundi
('2025-05-05 13:00:00', '2025-05-05 13:30:00', 'DISPONIBLE', 10),
('2025-05-05 13:30:00', '2025-05-05 14:00:00', 'DISPONIBLE', 10),
('2025-05-05 14:00:00', '2025-05-05 14:30:00', 'DISPONIBLE', 10),
-- Vendredi
('2025-05-09 09:00:00', '2025-05-09 09:30:00', 'DISPONIBLE', 10),
('2025-05-09 09:30:00', '2025-05-09 10:00:00', 'DISPONIBLE', 10),
('2025-05-09 10:00:00', '2025-05-09 10:30:00', 'DISPONIBLE', 10);

-- Docteur Neurologie (id 11)
INSERT INTO time_slots (debut, fin, statut, docteur_id) VALUES
-- Mardi
('2025-05-06 08:30:00', '2025-05-06 09:00:00', 'DISPONIBLE', 11),
('2025-05-06 09:00:00', '2025-05-06 09:30:00', 'DISPONIBLE', 11),
('2025-05-06 09:30:00', '2025-05-06 10:00:00', 'DISPONIBLE', 11),
-- Jeudi
('2025-05-08 15:30:00', '2025-05-08 16:00:00', 'DISPONIBLE', 11),
('2025-05-08 16:00:00', '2025-05-08 16:30:00', 'DISPONIBLE', 11),
('2025-05-08 16:30:00', '2025-05-08 17:00:00', 'DISPONIBLE', 11);

-- Docteur Ophtalmologie (id 12)
INSERT INTO time_slots (debut, fin, statut, docteur_id) VALUES
-- Mercredi
('2025-05-07 13:30:00', '2025-05-07 14:00:00', 'DISPONIBLE', 12),
('2025-05-07 14:00:00', '2025-05-07 14:30:00', 'DISPONIBLE', 12),
('2025-05-07 14:30:00', '2025-05-07 15:00:00', 'DISPONIBLE', 12),
-- Vendredi
('2025-05-09 13:00:00', '2025-05-09 13:30:00', 'DISPONIBLE', 12),
('2025-05-09 13:30:00', '2025-05-09 14:00:00', 'DISPONIBLE', 12),
('2025-05-09 14:00:00', '2025-05-09 14:30:00', 'DISPONIBLE', 12);

-- Création de réservations et rendez-vous
-- Réservation 1: Patient Dupont avec Dr Bernard (Cardiologie)
UPDATE time_slots SET statut = 'RESERVE' WHERE id = 1;
INSERT INTO reservation (patient_id, time_slot_id, statut) VALUES (1, 1, 'CONFIRMEE');  -- Correction ici
INSERT INTO rendez_vous (reservation_id, salle_id, statut, commentaire, duree) 
VALUES (1, 1, 'CONFIRME', 'Contrôle annuel cardiaque', 30);

-- Réservation 2: Patient Martin avec Dr Petit (Dermatologie)
UPDATE time_slots SET statut = 'RESERVE' WHERE id = 13;
INSERT INTO reservation (patient_id, time_slot_id, statut) VALUES (2, 13, 'CONFIRMEE');  -- Correction ici
INSERT INTO rendez_vous (reservation_id, salle_id, statut, commentaire, duree) 
VALUES (2, 2, 'PLANIFIE', 'Examen dermatologique complet', 30);

-- Réservation 3: Patient Dubois avec Dr Richard (Pédiatrie)
UPDATE time_slots SET statut = 'RESERVE' WHERE id = 25;
INSERT INTO reservation (patient_id, time_slot_id, statut) VALUES (3, 25, 'CONFIRMEE');  -- Correction ici
INSERT INTO rendez_vous (reservation_id, salle_id, statut, commentaire, duree) 
VALUES (3, 3, 'PLANIFIE', 'Consultation de suivi asthme', 30);

-- Réservation 4: Patient Lefebvre avec Dr Robert (Neurologie)
UPDATE time_slots SET statut = 'RESERVE' WHERE id = 31;
INSERT INTO reservation (patient_id, time_slot_id, statut) VALUES (4, 31, 'CONFIRMEE');  -- Correction ici
INSERT INTO rendez_vous (reservation_id, salle_id, statut, commentaire, duree) 
VALUES (4, 4, 'PLANIFIE', 'Consultation migraines', 30);

-- Réservation 5: Patient Leroy avec Dr Michel (Ophtalmologie)
UPDATE time_slots SET statut = 'RESERVE' WHERE id = 37;
INSERT INTO reservation (patient_id, time_slot_id, statut) VALUES (5, 37, 'CONFIRMEE');  -- Correction ici
INSERT INTO rendez_vous (reservation_id, salle_id, statut, commentaire, duree) 
VALUES (5, 5, 'PLANIFIE', 'Contrôle de la vision', 30);

-- Réservation 6: Patient Moreau avec Dr Bernard (Cardiologie) - Rendez-vous terminé avec commentaire
UPDATE time_slots SET statut = 'RESERVE' WHERE id = 2;
INSERT INTO reservation (patient_id, time_slot_id, statut) VALUES (6, 2, 'CONFIRMEE');  -- Correction ici
INSERT INTO rendez_vous (reservation_id, salle_id, statut, commentaire, duree) 
VALUES (6, 1, 'TERMINE', 'Tension artérielle normale, ECG sans anomalie. Contrôle dans 6 mois recommandé.', 30);

-- Réservation 7: Patient Simon avec Dr Petit (Dermatologie) - Rendez-vous annulé
UPDATE time_slots SET statut = 'DISPONIBLE' WHERE id = 14; -- Revenu à disponible car annulé
INSERT INTO reservation (patient_id, time_slot_id, statut) VALUES (7, 14, 'ANNULEE');  -- Correction ici
INSERT INTO rendez_vous (reservation_id, salle_id, statut, commentaire, duree) 
VALUES (7, 2, 'ANNULE', 'Annulé par le patient pour raisons personnelles', 30);

-- Insertion de rendez-vous supplémentaires (si nécessaire)
INSERT INTO rendez_vous (date_creation, statut, commentaire, duree, reservation_id, salle_id) VALUES
('2025-05-05 09:00:00', 'PLANIFIE', 'Consultation initiale', 30, 1, 101),
('2025-05-05 10:00:00', 'CONFIRME', 'Suivi dermatologique', 45, 2, 102);

-- Réservation 8: Patient Moreau avec Dr Bernard (Cardiologie) - Rendez-vous annulé
UPDATE time_slots SET statut = 'DISPONIBLE' WHERE id = 3; -- Revenu à disponible car annulé
INSERT INTO reservation (patient_id, time_slot_id, statut) VALUES (6, 3, 'ANNULEE');  -- Correction ici
INSERT INTO rendez_vous (reservation_id, salle_id, statut, commentaire, duree)
VALUES (8, 1, 'ANNULE', 'Annulé par le patient pour raisons personnelles', 30);