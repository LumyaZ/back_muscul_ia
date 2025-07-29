-- Script pour créer la table de liaison simple entre User et TrainingProgram
-- Script to create the simple liaison table between User and TrainingProgram

-- Suppression de la table existante si elle existe
-- Drop existing table if it exists
DROP TABLE IF EXISTS user_training_programs;

-- Création de la table de liaison simple
-- Create the simple liaison table
CREATE TABLE user_training_programs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    training_program_id BIGINT NOT NULL,
    UNIQUE KEY uk_user_training_program (user_id, training_program_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (training_program_id) REFERENCES training_programs(id) ON DELETE CASCADE
);

-- Insertion de données de test
-- Insert test data
INSERT INTO user_training_programs (user_id, training_program_id) VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 3);

-- Vérification de la création
-- Verification of creation
SELECT 
    'Table user_training_programs créée avec succès' as status,
    COUNT(*) as nombre_liaisons
FROM user_training_programs; 