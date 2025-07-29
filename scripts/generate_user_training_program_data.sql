-- Script to generate test data for user_training_programs table
-- Script pour générer des données de test pour la table user_training_programs

-- Insert test data for user_training_programs table
-- Insérer des données de test pour la table user_training_programs

-- User 1 subscribed to Program 1 (In Progress)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (1, 1, '2024-01-15 10:00:00', 'IN_PROGRESS', 3, 2, NULL, 'Programme très efficace pour débuter', true, '2024-01-15 10:00:00', '2024-01-20 15:30:00');

-- User 1 subscribed to Program 2 (Completed)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (1, 2, '2023-12-01 09:00:00', 'COMPLETED', 8, 3, '2024-01-10 16:45:00', 'Excellent programme, résultats visibles après 6 semaines', true, '2023-12-01 09:00:00', '2024-01-10 16:45:00');

-- User 1 subscribed to Program 3 (Not Started)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (1, 3, '2024-01-22 14:00:00', 'NOT_STARTED', 1, 1, NULL, 'À commencer la semaine prochaine', false, '2024-01-22 14:00:00', '2024-01-22 14:00:00');

-- User 2 subscribed to Program 1 (In Progress)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (2, 1, '2024-01-10 11:30:00', 'IN_PROGRESS', 5, 1, NULL, 'Programme adapté à mon niveau', true, '2024-01-10 11:30:00', '2024-01-25 10:15:00');

-- User 2 subscribed to Program 4 (Paused)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (2, 4, '2023-11-15 08:00:00', 'PAUSED', 4, 2, NULL, 'Pause temporaire pour cause de blessure', false, '2023-11-15 08:00:00', '2024-01-05 12:00:00');

-- User 3 subscribed to Program 2 (Completed)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (3, 2, '2023-10-01 07:00:00', 'COMPLETED', 8, 3, '2023-11-26 18:30:00', 'Très satisfait des résultats', true, '2023-10-01 07:00:00', '2023-11-26 18:30:00');

-- User 3 subscribed to Program 5 (In Progress)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (3, 5, '2024-01-05 16:00:00', 'IN_PROGRESS', 2, 3, NULL, 'Programme cardio intense mais efficace', false, '2024-01-05 16:00:00', '2024-01-18 14:20:00');

-- User 4 subscribed to Program 1 (Not Started)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (4, 1, '2024-01-25 13:00:00', 'NOT_STARTED', 1, 1, NULL, 'Prêt à commencer demain', true, '2024-01-25 13:00:00', '2024-01-25 13:00:00');

-- User 4 subscribed to Program 3 (In Progress)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (4, 3, '2024-01-08 09:30:00', 'IN_PROGRESS', 6, 2, NULL, 'Progression constante, très motivé', true, '2024-01-08 09:30:00', '2024-01-22 11:45:00');

-- User 5 subscribed to Program 2 (Completed)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (5, 2, '2023-09-15 06:00:00', 'COMPLETED', 8, 3, '2023-11-10 17:00:00', 'Programme parfait pour reprendre le sport', false, '2023-09-15 06:00:00', '2023-11-10 17:00:00');

-- User 5 subscribed to Program 4 (In Progress)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (5, 4, '2024-01-12 10:00:00', 'IN_PROGRESS', 3, 1, NULL, 'Défis intéressants, progression visible', true, '2024-01-12 10:00:00', '2024-01-19 15:30:00');

-- User 6 subscribed to Program 1 (Completed)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (6, 1, '2023-08-01 08:00:00', 'COMPLETED', 8, 3, '2023-09-26 16:15:00', 'Excellent programme pour débutants', true, '2023-08-01 08:00:00', '2023-09-26 16:15:00');

-- User 6 subscribed to Program 5 (In Progress)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (6, 5, '2024-01-03 17:00:00', 'IN_PROGRESS', 4, 2, NULL, 'Cardio intense, endurance améliorée', false, '2024-01-03 17:00:00', '2024-01-20 13:45:00');

-- User 7 subscribed to Program 3 (Not Started)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (7, 3, '2024-01-28 12:00:00', 'NOT_STARTED', 1, 1, NULL, 'En attente de matériel', false, '2024-01-28 12:00:00', '2024-01-28 12:00:00');

-- User 8 subscribed to Program 2 (In Progress)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (8, 2, '2024-01-01 07:30:00', 'IN_PROGRESS', 7, 1, NULL, 'Dernière semaine, résultats excellents', true, '2024-01-01 07:30:00', '2024-01-21 09:00:00');

-- User 9 subscribed to Program 4 (Completed)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (9, 4, '2023-07-01 09:00:00', 'COMPLETED', 8, 3, '2023-08-26 18:00:00', 'Programme complet et bien structuré', true, '2023-07-01 09:00:00', '2023-08-26 18:00:00');

-- User 10 subscribed to Program 1 (In Progress)
INSERT INTO user_training_programs (user_id, training_program_id, started_at, status, current_week, current_session, completed_at, notes, is_favorite, created_at, updated_at)
VALUES (10, 1, '2024-01-20 14:30:00', 'IN_PROGRESS', 2, 3, NULL, 'Début prometteur, exercices adaptés', false, '2024-01-20 14:30:00', '2024-01-23 16:20:00');

-- Summary of test data:
-- Résumé des données de test :
-- 
-- Program 1: 5 users (3 in progress, 1 not started, 1 completed)
-- Program 2: 4 users (1 in progress, 3 completed)
-- Program 3: 3 users (1 in progress, 1 not started, 1 in progress)
-- Program 4: 3 users (1 in progress, 1 paused, 1 completed)
-- Program 5: 2 users (2 in progress)
-- 
-- Status distribution:
-- Répartition des statuts :
-- - IN_PROGRESS: 10 users
-- - COMPLETED: 6 users
-- - NOT_STARTED: 3 users
-- - PAUSED: 1 user
-- 
-- Favorite programs:
-- Programmes favoris :
-- - Program 1: 3 favorites
-- - Program 2: 3 favorites
-- - Program 3: 2 favorites
-- - Program 4: 1 favorite
-- - Program 5: 0 favorites 