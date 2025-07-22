-- Migration V3: Create TrainingInfo table
-- Migration V3 : Créer la table TrainingInfo

CREATE TABLE training_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    weight DOUBLE NOT NULL,
    height DOUBLE NOT NULL,
    body_fat_percentage DOUBLE,
    experience_level VARCHAR(20) NOT NULL,
    session_frequency VARCHAR(20) NOT NULL,
    session_duration VARCHAR(20) NOT NULL,
    main_goal VARCHAR(20) NOT NULL,
    training_preference VARCHAR(20) NOT NULL,
    equipment VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Foreign key constraint
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    
    -- Unique constraint to ensure one training info per user
    UNIQUE KEY uk_training_info_user (user_id)
);

-- Add indexes for better performance
CREATE INDEX idx_training_info_user_id ON training_info(user_id);
CREATE INDEX idx_training_info_experience_level ON training_info(experience_level);
CREATE INDEX idx_training_info_main_goal ON training_info(main_goal);
CREATE INDEX idx_training_info_equipment ON training_info(equipment); 