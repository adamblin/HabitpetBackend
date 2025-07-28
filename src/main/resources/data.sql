-- Crear tabla pets (con satiated, cleanliness y hapyness)
CREATE TABLE pets (
                      id UUID PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      satiated INT NOT NULL,
                      cleanliness INT NOT NULL,
                      hapyness INT NOT NULL
);

-- Insertar mascotas
INSERT INTO pets (id, name, satiated, cleanliness, hapyness) VALUES
                                                                 (UUID(), 'Buddy', 50, 80, 60),
                                                                 (UUID(), 'Bella', 30, 70, 85),
                                                                 (UUID(), 'Charlie', 60, 90, 70);

-- Crear tabla users (con relación a pet_id)
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       habit_coins INT DEFAULT 0 NOT NULL,
                       habit_gems INT DEFAULT 0 NOT NULL,
                       pet_id UUID,
                       FOREIGN KEY (pet_id) REFERENCES pets (id) ON DELETE CASCADE
);

-- Insertar usuarios con mascotas
INSERT INTO users (id, username, password, email, habit_coins, habit_gems, pet_id) VALUES
                                                                                       (UUID(), 'john_doe', 'password123', 'john.doe@example.com', 100, 5, (SELECT id FROM pets WHERE name = 'Buddy')),
                                                                                       (UUID(), 'jane_smith', 'password456', 'jane.smith@example.com', 50, 2, (SELECT id FROM pets WHERE name = 'Bella')),
                                                                                       (UUID(), 'alice_wonder', 'password789', 'alice.wonder@example.com', 80, 3, (SELECT id FROM pets WHERE name = 'Charlie'));

-- Crear tabla accessories
CREATE TABLE accessories (
                             id UUID PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             habit_coins_cost INT NOT NULL DEFAULT 0,
                             habit_gems_cost INT NOT NULL DEFAULT 0
);

-- Insertar accesorios
INSERT INTO accessories (id, name, habit_coins_cost, habit_gems_cost) VALUES
                                                                          (UUID(), 'Gafas de Sol', 100, 0),
                                                                          (UUID(), 'Sombrero de Vaquero', 150, 2),
                                                                          (UUID(), 'Bufanda Roja', 80, 1),
                                                                          (UUID(), 'Orejas de Conejo', 0, 5),
                                                                          (UUID(), 'Pañuelo Pirata', 110, 0);

-- Crear tabla pet_accessories
CREATE TABLE pet_accessories (
                                 pet_id UUID,
                                 accessory_id UUID,
                                 PRIMARY KEY (pet_id, accessory_id),
                                 FOREIGN KEY (pet_id) REFERENCES pets (id) ON DELETE CASCADE,
                                 FOREIGN KEY (accessory_id) REFERENCES accessories (id) ON DELETE CASCADE
);

-- Relacionar mascotas con accesorios
INSERT INTO pet_accessories (pet_id, accessory_id) VALUES
                                                       ((SELECT id FROM pets WHERE name = 'Buddy'), (SELECT id FROM accessories WHERE name = 'Gafas de Sol')),
                                                       ((SELECT id FROM pets WHERE name = 'Bella'), (SELECT id FROM accessories WHERE name = 'Sombrero de Vaquero')),
                                                       ((SELECT id FROM pets WHERE name = 'Charlie'), (SELECT id FROM accessories WHERE name = 'Pañuelo Pirata'));

-- Crear tabla tasks
CREATE TABLE tasks (
                       id UUID PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       estimated_time INT NOT NULL,
                       type VARCHAR(50) NOT NULL,
                       status VARCHAR(50) NOT NULL,
                       points INT NOT NULL,
                       user_id UUID,
                       FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Insertar tareas
INSERT INTO tasks (id, name, estimated_time, type, status, points, user_id) VALUES
                                                                                (UUID(), 'Study Java', 120, 'study', 'in_progress', 10, (SELECT id FROM users WHERE username = 'john_doe')),
                                                                                (UUID(), 'Work Out', 60, 'exercise', 'completed', 20, (SELECT id FROM users WHERE username = 'jane_smith')),
                                                                                (UUID(), 'Grocery Shopping', 30, 'errand', 'to_do', 5, (SELECT id FROM users WHERE username = 'alice_wonder'));

-- Crear tabla friendships
CREATE TABLE friendships (
                             id UUID PRIMARY KEY,
                             user_id UUID NOT NULL,
                             friend_id UUID NOT NULL,
                             status VARCHAR(50) DEFAULT 'PENDING' NOT NULL,
                             FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                             FOREIGN KEY (friend_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Insertar amistades
INSERT INTO friendships (id, user_id, friend_id, status) VALUES
                                                             (UUID(), (SELECT id FROM users WHERE username = 'john_doe'),     (SELECT id FROM users WHERE username = 'jane_smith'),  'PENDING'),
                                                             (UUID(), (SELECT id FROM users WHERE username = 'jane_smith'),   (SELECT id FROM users WHERE username = 'alice_wonder'), 'PENDING'),
                                                             (UUID(), (SELECT id FROM users WHERE username = 'alice_wonder'), (SELECT id FROM users WHERE username = 'john_doe'),     'PENDING');

CREATE TABLE interaction_types (
                                   id UUID PRIMARY KEY,
                                   action VARCHAR(20) NOT NULL,          -- FEED, CLEAN, PET
                                   type VARCHAR(50) NOT NULL,            -- manzana, ducha, juguete...
                                   delta_satiated INT DEFAULT 0,
                                   delta_cleanliness INT DEFAULT 0,
                                   delta_hapyness INT DEFAULT 0
);

-- Datos de ejemplo
INSERT INTO interaction_types (id, action, type, delta_satiated, delta_cleanliness, delta_hapyness) VALUES
                                                                                                        (UUID(), 'FEED', 'manzana', 10, 0, 0),
                                                                                                        (UUID(), 'FEED', 'hamburguesa', 30, -5, 0),
                                                                                                        (UUID(), 'CLEAN', 'ducha', 0, 40, 0),
                                                                                                        (UUID(), 'CLEAN', 'toallita', 0, 10, 0),
                                                                                                        (UUID(), 'PET', 'caricia', 0, 0, 10),
                                                                                                        (UUID(), 'PET', 'juguete', -10, 0, 20);

