-- Crear tabla pets (sin la columna user_id)
CREATE TABLE pets (
                      id UUID PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      hungryness INT NOT NULL,
                      cleanliness INT NOT NULL
);

-- Insertar mascotas
INSERT INTO pets (id, name, hungryness, cleanliness) VALUES
                                                         (UUID(), 'Buddy', 50, 80),
                                                         (UUID(), 'Bella', 30, 70),
                                                         (UUID(), 'Charlie', 60, 90);

-- Crear tabla users (con la columna pet_id añadida)
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       coins INT DEFAULT 0 NOT NULL,
                       pet_id UUID,
                       FOREIGN KEY (pet_id) REFERENCES pets (id) ON DELETE CASCADE
);

-- Insertar usuarios con sus mascotas asignadas
INSERT INTO users (id, username, password, email, pet_id) VALUES
                                                              (UUID(), 'john_doe', 'password123', 'john.doe@example.com', (SELECT id FROM pets WHERE name = 'Buddy')),
                                                              (UUID(), 'jane_smith', 'password456', 'jane.smith@example.com', (SELECT id FROM pets WHERE name = 'Bella')),
                                                              (UUID(), 'alice_wonder', 'password789', 'alice.wonder@example.com', (SELECT id FROM pets WHERE name = 'Charlie'));

-- Crear tabla accessories
CREATE TABLE accessories (
                             id UUID PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             coins INT NOT NULL
);

-- Insertar accesorios
INSERT INTO accessories (id, name, coins) VALUES
                                              (UUID(), 'Bow Tie', 50),
                                              (UUID(), 'Hat', 100),
                                              (UUID(), 'Sunglasses', 150);

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
                                                       ((SELECT id FROM pets WHERE name = 'Buddy'), (SELECT id FROM accessories WHERE name = 'Bow Tie')),
                                                       ((SELECT id FROM pets WHERE name = 'Bella'), (SELECT id FROM accessories WHERE name = 'Hat')),
                                                       ((SELECT id FROM pets WHERE name = 'Charlie'), (SELECT id FROM accessories WHERE name = 'Sunglasses'));

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

-- Crear tabla friendships con columna status en vez de accepted
CREATE TABLE friendships (
                             id UUID PRIMARY KEY,
                             user_id UUID NOT NULL,
                             friend_id UUID NOT NULL,
                             status VARCHAR(50) DEFAULT 'PENDING' NOT NULL,
                             FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                             FOREIGN KEY (friend_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Insertar amistades con estado inicial PENDING
INSERT INTO friendships (id, user_id, friend_id, status) VALUES
                                                             (UUID(), (SELECT id FROM users WHERE username = 'john_doe'),     (SELECT id FROM users WHERE username = 'jane_smith'),  'PENDING'),
                                                             (UUID(), (SELECT id FROM users WHERE username = 'jane_smith'),   (SELECT id FROM users WHERE username = 'alice_wonder'), 'PENDING'),
                                                             (UUID(), (SELECT id FROM users WHERE username = 'alice_wonder'), (SELECT id FROM users WHERE username = 'john_doe'),     'PENDING');
