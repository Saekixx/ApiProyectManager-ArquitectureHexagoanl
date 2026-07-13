use projectmanager;

-- ====================================================================
-- 1. INSERTAR PROYECTOS (El líder se define en leader_id)
-- ====================================================================
INSERT INTO project (name, leader_id, description) VALUES 
('Sistema de Ventas MERN', 2, 'Desarrollo de un sistema POS con React y Node.js'), -- Líder: Carlos (id: 2)
('Módulo HelpDesk Prácticas', 3, 'Sistema de tickets de soporte técnico e incidencias'), -- Líder: Ana (id: 3)
('Rediseño UI Dashboard', 1, 'Optimización de las interfaces de administración'); -- Líder: Jack Admin (id: 1)


-- ====================================================================
-- 2. INSERTAR MIEMBROS DE LOS PROYECTOS (project_miembro)
-- REGLA: El líder DEBE estar incluido aquí también como miembro.
-- ====================================================================

-- Miembros para 'Sistema de Ventas MERN' (project_id: 1)
INSERT INTO project_miembro (project_id, user_id) VALUES 
(1, 2), -- Carlos (Líder obligatorio)
(1, 5), -- Pedro (Backend)
(1, 6); -- Sofia (Frontend)

-- Miembros para 'Módulo HelpDesk Prácticas' (project_id: 2)
INSERT INTO project_miembro (project_id, user_id) VALUES 
(2, 3), -- Ana (Líder obligatoria)
(2, 4), -- Maria (QA)
(2, 5); -- Pedro (Backend)

-- Miembros para 'Rediseño UI Dashboard' (project_id: 3)
INSERT INTO project_miembro (project_id, user_id) VALUES 
(3, 1), -- Jack Admin (Líder obligatorio)
(3, 6); -- Sofia (Frontend)


-- ====================================================================
-- 3. INSERTAR TAREAS (task)
-- Asociadas a cada ID de proyecto correspondiente.
-- ====================================================================
INSERT INTO task (name, description, status, due_date, project_id) VALUES 
-- Tareas del Proyecto 1 (Ventas MERN)
('Diseño de Base de Datos MySQL', 'Crear el modelo relacional e índices', 'EN_PROGRESO', '2026-08-15 23:59:59', 1),
('Desarrollo de API Express.js', 'Estructurar controladores y rutas base', 'PENDIENTE', '2026-08-20 23:59:59', 1),
('Maquetación de Componentes Vite', 'Vistas de login y registro con Tailwind', 'PENDIENTE', '2026-08-25 23:59:59', 1),

-- Tareas del Proyecto 2 (HelpDesk)
('Definición de Requerimientos', 'Reunión con el cliente para el HelpDesk', 'COMPLETADA', '2026-07-01 12:00:00', 2),
('Pruebas de Integración de Seguridad', 'Testing de roles ADMIN y COLABORADOR', 'PENDIENTE', '2026-08-10 18:00:00', 2),

-- Tareas del Proyecto 3 (Rediseño UI)
('Creación de Mockups en Figma', 'Diseño de la interfaz oscura y clara', 'EN_PROGRESO', '2026-07-30 20:00:00', 3);


-- ====================================================================
-- 4. ASIGNACIÓN DE TAREAS (task_assignment)
-- REGLA: Los usuarios asignados DEBEN ser miembros de dicho proyecto.
-- ====================================================================

-- Asignaciones del Proyecto 1:
-- Tarea 1 (Diseño DB) asignada a Carlos (2) y Pedro (5)
INSERT INTO task_assignment (task_id, user_id) VALUES (1, 2), (1, 5);
-- Tarea 2 (API Express) asignada a Pedro (5)
INSERT INTO task_assignment (task_id, user_id) VALUES (2, 5);
-- Tarea 3 (Componentes Vite) asignada a Sofia (6)
INSERT INTO task_assignment (task_id, user_id) VALUES (3, 6);

-- Asignaciones del Proyecto 2:
-- Tarea 4 (Requerimientos) asignada a Ana (3)
INSERT INTO task_assignment (task_id, user_id) VALUES (4, 3);
-- Tarea 5 (Pruebas Seguridad) asignada a Maria (4) y Ana (3)
INSERT INTO task_assignment (task_id, user_id) VALUES (5, 4), (5, 3);

-- Asignaciones del Proyecto 3:
-- Tarea 6 (Mockups Figma) asignada a Sofia (6) y Jack Admin (1)
INSERT INTO task_assignment (task_id, user_id) VALUES (6, 6), (6, 1);