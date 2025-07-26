-- Script pour générer des données de test supplémentaires
-- À exécuter manuellement après les migrations

-- 1. Vérifier qu'il y a des utilisateurs
SELECT 'Vérification des utilisateurs' as info;
SELECT COUNT(*) as nb_users FROM users;

-- 2. Statistiques sur les programmes existants
SELECT 'Statistiques des programmes' as info;
SELECT 
    difficulty_level,
    category,
    COUNT(*) as nb_programmes,
    AVG(duration_weeks) as duree_moyenne_semaines,
    AVG(estimated_duration_minutes) as duree_moyenne_minutes
FROM training_programs 
WHERE is_active = TRUE 
GROUP BY difficulty_level, category
ORDER BY difficulty_level, category;

-- 3. Statistiques sur les exercices
SELECT 'Statistiques des exercices' as info;
SELECT 
    category,
    difficulty_level,
    COUNT(*) as nb_exercices
FROM exercises 
WHERE is_active = TRUE 
GROUP BY category, difficulty_level
ORDER BY category, difficulty_level;

-- 4. Programmes les plus populaires (avec le plus d'exercices)
SELECT 'Programmes avec le plus d\'exercices' as info;
SELECT 
    tp.name,
    tp.difficulty_level,
    tp.category,
    COUNT(pe.id) as nb_exercices,
    tp.estimated_duration_minutes
FROM training_programs tp
LEFT JOIN program_exercises pe ON tp.id = pe.training_program_id
WHERE tp.is_active = TRUE
GROUP BY tp.id, tp.name, tp.difficulty_level, tp.category, tp.estimated_duration_minutes
ORDER BY nb_exercices DESC
LIMIT 10;

-- 5. Exercices les plus utilisés dans les programmes
SELECT 'Exercices les plus utilisés' as info;
SELECT 
    e.name,
    e.category,
    e.muscle_group,
    COUNT(pe.id) as nb_utilisations
FROM exercises e
LEFT JOIN program_exercises pe ON e.id = pe.exercise_id
LEFT JOIN training_programs tp ON pe.training_program_id = tp.id
WHERE e.is_active = TRUE AND tp.is_active = TRUE
GROUP BY e.id, e.name, e.category, e.muscle_group
ORDER BY nb_utilisations DESC
LIMIT 10;

-- 6. Durée moyenne des programmes par niveau
SELECT 'Durée moyenne par niveau' as info;
SELECT 
    difficulty_level,
    COUNT(*) as nb_programmes,
    ROUND(AVG(duration_weeks), 1) as duree_moyenne_semaines,
    ROUND(AVG(estimated_duration_minutes), 1) as duree_moyenne_minutes,
    ROUND(AVG(sessions_per_week), 1) as sessions_moyenne_semaine
FROM training_programs 
WHERE is_active = TRUE 
GROUP BY difficulty_level
ORDER BY 
    CASE difficulty_level 
        WHEN 'Débutant' THEN 1 
        WHEN 'Intermédiaire' THEN 2 
        WHEN 'Avancé' THEN 3 
    END;

-- 7. Répartition des programmes par catégorie
SELECT 'Répartition par catégorie' as info;
SELECT 
    category,
    COUNT(*) as nb_programmes,
    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM training_programs WHERE is_active = TRUE), 1) as pourcentage
FROM training_programs 
WHERE is_active = TRUE 
GROUP BY category
ORDER BY nb_programmes DESC;

-- 8. Programmes publics vs privés
SELECT 'Programmes publics vs privés' as info;
SELECT 
    is_public,
    COUNT(*) as nb_programmes,
    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM training_programs WHERE is_active = TRUE), 1) as pourcentage
FROM training_programs 
WHERE is_active = TRUE 
GROUP BY is_public
ORDER BY is_public DESC;

-- 9. Top 5 des programmes les plus longs
SELECT 'Programmes les plus longs' as info;
SELECT 
    name,
    difficulty_level,
    category,
    duration_weeks,
    estimated_duration_minutes,
    sessions_per_week
FROM training_programs 
WHERE is_active = TRUE 
ORDER BY estimated_duration_minutes DESC
LIMIT 5;

-- 10. Top 5 des programmes les plus courts
SELECT 'Programmes les plus courts' as info;
SELECT 
    name,
    difficulty_level,
    category,
    duration_weeks,
    estimated_duration_minutes,
    sessions_per_week
FROM training_programs 
WHERE is_active = TRUE 
ORDER BY estimated_duration_minutes ASC
LIMIT 5;

-- 11. Exercices par groupe musculaire
SELECT 'Exercices par groupe musculaire' as info;
SELECT 
    muscle_group,
    COUNT(*) as nb_exercices,
    GROUP_CONCAT(name SEPARATOR ', ') as exercices
FROM exercises 
WHERE is_active = TRUE AND muscle_group IS NOT NULL
GROUP BY muscle_group
ORDER BY nb_exercices DESC;

-- 12. Exercices par équipement nécessaire
SELECT 'Exercices par équipement' as info;
SELECT 
    equipment_needed,
    COUNT(*) as nb_exercices,
    GROUP_CONCAT(name SEPARATOR ', ') as exercices
FROM exercises 
WHERE is_active = TRUE AND equipment_needed IS NOT NULL
GROUP BY equipment_needed
ORDER BY nb_exercices DESC;

-- 13. Programmes avec exercices optionnels
SELECT 'Programmes avec exercices optionnels' as info;
SELECT 
    tp.name,
    tp.difficulty_level,
    COUNT(CASE WHEN pe.is_optional = TRUE THEN 1 END) as nb_exercices_optionnels,
    COUNT(CASE WHEN pe.is_optional = FALSE THEN 1 END) as nb_exercices_obligatoires
FROM training_programs tp
LEFT JOIN program_exercises pe ON tp.id = pe.training_program_id
WHERE tp.is_active = TRUE
GROUP BY tp.id, tp.name, tp.difficulty_level
HAVING nb_exercices_optionnels > 0
ORDER BY nb_exercices_optionnels DESC;

-- 14. Statistiques des poids dans les programmes
SELECT 'Statistiques des poids' as info;
SELECT 
    tp.name,
    tp.difficulty_level,
    COUNT(pe.id) as nb_exercices_avec_poids,
    ROUND(AVG(pe.weight_kg), 1) as poids_moyen_kg,
    MIN(pe.weight_kg) as poids_min_kg,
    MAX(pe.weight_kg) as poids_max_kg
FROM training_programs tp
LEFT JOIN program_exercises pe ON tp.id = pe.training_program_id
WHERE tp.is_active = TRUE AND pe.weight_kg IS NOT NULL AND pe.weight_kg > 0
GROUP BY tp.id, tp.name, tp.difficulty_level
ORDER BY poids_moyen_kg DESC;

-- 15. Résumé général
SELECT 'Résumé général' as info;
SELECT 
    (SELECT COUNT(*) FROM users) as total_users,
    (SELECT COUNT(*) FROM training_programs WHERE is_active = TRUE) as total_programmes,
    (SELECT COUNT(*) FROM training_programs WHERE is_active = TRUE AND is_public = TRUE) as programmes_publics,
    (SELECT COUNT(*) FROM training_programs WHERE is_active = TRUE AND is_public = FALSE) as programmes_prives,
    (SELECT COUNT(*) FROM exercises WHERE is_active = TRUE) as total_exercices,
    (SELECT COUNT(*) FROM program_exercises) as total_relations_programme_exercice; 