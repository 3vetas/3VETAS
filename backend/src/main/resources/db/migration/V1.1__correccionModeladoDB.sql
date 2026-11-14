-- 3VETAS · Corrección del modelado · MySQL 8.0.16+
-- Ejecutar después de V1__creacionTablas.sql, antes de insertar datos.
-- IMPORTANTE: migración estructural para entorno SIN DATOS en las tablas afectadas.
-- No modifica la migración V1. Los cambios DDL de MySQL no son transaccionales en conjunto.

-- 0. Unificar nombres de claves foráneas con el modelo definitivo.
-- Se renombran columnas, sin cambiar las relaciones existentes.
ALTER TABLE USUARIOS RENAME COLUMN rol_ID TO ID_rol;
ALTER TABLE PERSONAS RENAME COLUMN usuario_ID TO ID_usuario;
ALTER TABLE DIRECCIONES RENAME COLUMN persona_ID TO ID_persona;
ALTER TABLE PROYECTOS RENAME COLUMN usuario_ID TO ID_usuario;
ALTER TABLE MOVIMIENTOS_STOCK RENAME COLUMN usuario_ID TO ID_usuario;
ALTER TABLE PEDIDO_ESTADOS
  RENAME COLUMN pedido_ID TO ID_pedido,
  RENAME COLUMN usuario_ID TO ID_usuario;
ALTER TABLE DOCUMENTOS RENAME COLUMN pedido_ID TO ID_pedido;

-- 1. Crear agrupaciones de material por proyecto.
CREATE TABLE TABLEROS_PROYECTO (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_proyecto INT NOT NULL,
  ID_tipo_tablero INT NOT NULL,
  ID_acabado INT NOT NULL,
  ID_grosor INT NOT NULL,
  CONSTRAINT fk_tableros_proyecto_proyecto FOREIGN KEY (ID_proyecto) REFERENCES PROYECTOS(ID) ON DELETE CASCADE,
  CONSTRAINT fk_tableros_proyecto_tipo FOREIGN KEY (ID_tipo_tablero) REFERENCES TIPOS_TABLERO(ID),
  CONSTRAINT fk_tableros_proyecto_acabado FOREIGN KEY (ID_acabado) REFERENCES ACABADOS(ID),
  CONSTRAINT fk_tableros_proyecto_grosor FOREIGN KEY (ID_grosor) REFERENCES GROSORES(ID),
  CONSTRAINT uq_tableros_proyecto_caracteristicas UNIQUE (ID_proyecto, ID_tipo_tablero, ID_acabado, ID_grosor)
) ENGINE=InnoDB;

-- 2. Desvincular proyectos de una única combinación de características.
ALTER TABLE PROYECTOS
  DROP FOREIGN KEY fk_proyectos_tipo,
  DROP FOREIGN KEY fk_proyectos_acabado,
  DROP FOREIGN KEY fk_proyectos_grosor;
ALTER TABLE PROYECTOS
  DROP COLUMN ID_tipo_tablero,
  DROP COLUMN ID_acabado,
  DROP COLUMN ID_grosor;

-- 3. Cada pieza pertenece a un grupo de tableros del proyecto.
ALTER TABLE PIEZAS DROP FOREIGN KEY fk_piezas_proyecto;
ALTER TABLE PIEZAS
  DROP COLUMN ID_proyecto,
  ADD COLUMN ID_tableros_proyecto INT NOT NULL AFTER ID,
  ADD CONSTRAINT fk_piezas_tableros_proyecto FOREIGN KEY (ID_tableros_proyecto) REFERENCES TABLEROS_PROYECTO(ID) ON DELETE CASCADE;

-- 4. Las optimizaciones pertenecen a un grupo; sus referencias comerciales pasan al detalle.
ALTER TABLE OPTIMIZACIONES
  DROP FOREIGN KEY fk_optimizaciones_proyecto,
  DROP FOREIGN KEY fk_optimizaciones_tablero;
ALTER TABLE OPTIMIZACIONES
  DROP CHECK chk_optimizaciones_valores;
ALTER TABLE OPTIMIZACIONES
  DROP COLUMN ID_proyecto,
  DROP COLUMN ID_tablero,
  DROP COLUMN cantidad,
  ADD COLUMN ID_tableros_proyecto INT NOT NULL AFTER ID,
  ADD CONSTRAINT fk_optimizaciones_tableros_proyecto FOREIGN KEY (ID_tableros_proyecto) REFERENCES TABLEROS_PROYECTO(ID) ON DELETE CASCADE,
  ADD CONSTRAINT chk_optimizaciones_valores CHECK (
    superficie_utilizada >= 0 AND superficie_desperdiciada >= 0
    AND porcentaje_aprovechamiento BETWEEN 0 AND 100 AND coste_estimado >= 0
  );

-- 5. Referencias comerciales y cantidad de tableros por alternativa.
-- 'precio' representa el subtotal de la referencia en la optimización.
CREATE TABLE DETALLE_OPTIMIZACION (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_optimizacion INT NOT NULL,
  ID_tablero INT NOT NULL,
  cantidad INT NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_detalle_optimizacion_optimizacion FOREIGN KEY (ID_optimizacion) REFERENCES OPTIMIZACIONES(ID) ON DELETE CASCADE,
  CONSTRAINT fk_detalle_optimizacion_tablero FOREIGN KEY (ID_tablero) REFERENCES TABLEROS(ID),
  CONSTRAINT uq_detalle_optimizacion_tablero UNIQUE (ID_optimizacion, ID_tablero),
  CONSTRAINT chk_detalle_optimizacion_valores CHECK (cantidad > 0 AND precio >= 0)
) ENGINE=InnoDB;

-- 6. Cada fila representa un tablero físico de una referencia.
CREATE TABLE UNIDADES_TABLERO_OPTIMIZACION (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_detalle_optimizacion INT NOT NULL,
  numero_tablero INT NOT NULL,
  CONSTRAINT fk_unidades_detalle FOREIGN KEY (ID_detalle_optimizacion) REFERENCES DETALLE_OPTIMIZACION(ID) ON DELETE CASCADE,
  CONSTRAINT uq_unidades_detalle_numero UNIQUE (ID_detalle_optimizacion, numero_tablero),
  CONSTRAINT chk_unidades_numero CHECK (numero_tablero > 0)
) ENGINE=InnoDB;

-- 7. La distribución corresponde a un tablero físico, no a una optimización genérica.
ALTER TABLE DISTRIBUCION_PIEZAS
  DROP FOREIGN KEY fk_distribucion_optimizacion;
ALTER TABLE DISTRIBUCION_PIEZAS
  DROP CHECK chk_distribucion_valores;
ALTER TABLE DISTRIBUCION_PIEZAS
  DROP COLUMN ID_optimizacion,
  DROP COLUMN num_tablero,
  ADD COLUMN ID_unidad_tablero INT NOT NULL AFTER ID,
  ADD CONSTRAINT fk_distribucion_unidad FOREIGN KEY (ID_unidad_tablero) REFERENCES UNIDADES_TABLERO_OPTIMIZACION(ID) ON DELETE CASCADE,
  ADD CONSTRAINT chk_distribucion_valores CHECK (
    numero_unidad > 0 AND orden_dibujo >= 0
    AND posicion_x_mm >= 0 AND posicion_y_mm >= 0
    AND ancho_final_mm > 0 AND alto_final_mm > 0
  );

-- 8. Un pedido ya no apunta directamente a una única optimización.
ALTER TABLE PEDIDOS DROP FOREIGN KEY fk_pedidos_optimizacion;
ALTER TABLE PEDIDOS DROP COLUMN ID_optimizacion;

-- 9. Cada línea representa una optimización adquirida.
CREATE TABLE LINEAS_PEDIDO (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_pedido INT NOT NULL,
  ID_optimizacion INT NOT NULL,
  importe DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_lineas_pedido_pedido FOREIGN KEY (ID_pedido) REFERENCES PEDIDOS(ID),
  CONSTRAINT fk_lineas_pedido_optimizacion FOREIGN KEY (ID_optimizacion) REFERENCES OPTIMIZACIONES(ID),
  CONSTRAINT uq_lineas_pedido_optimizacion UNIQUE (ID_pedido, ID_optimizacion),
  CONSTRAINT chk_lineas_pedido_importe CHECK (importe >= 0)
) ENGINE=InnoDB;

-- 10. Planos asociados a optimizaciones; facturas asociadas a pedidos.
ALTER TABLE DOCUMENTOS
  MODIFY COLUMN ID_optimizacion INT NULL,
  ADD CONSTRAINT chk_documentos_referencia CHECK (
    (ID_optimizacion IS NOT NULL AND ID_pedido IS NULL)
    OR (ID_optimizacion IS NULL AND ID_pedido IS NOT NULL)
  );

-- 11. Ajustar claves foráneas heredadas de V1 para el borrado del planificador.
-- Una pieza eliminada no debe dejar posiciones de corte huérfanas.
ALTER TABLE DISTRIBUCION_PIEZAS
  DROP FOREIGN KEY fk_distribucion_pieza;

ALTER TABLE DISTRIBUCION_PIEZAS
  ADD CONSTRAINT fk_distribucion_pieza
    FOREIGN KEY (ID_pieza)
    REFERENCES PIEZAS(ID)
    ON DELETE CASCADE;

-- Los metadatos de planos asociados a una optimización se eliminan con ella.
-- Los documentos de pedidos siguen protegidos por su relación restrictiva.
-- Los archivos físicos requieren gestión independiente desde la aplicación.
ALTER TABLE DOCUMENTOS
  DROP FOREIGN KEY fk_documentos_optimizacion;

ALTER TABLE DOCUMENTOS
  ADD CONSTRAINT fk_documentos_optimizacion
    FOREIGN KEY (ID_optimizacion)
    REFERENCES OPTIMIZACIONES(ID)
    ON DELETE CASCADE;

-- Las líneas de pedido deben proteger tanto el pedido como la optimización.
-- IMPORTANTE: no usar CASCADE en LINEAS_PEDIDO -> OPTIMIZACIONES,
-- porque permitiría borrar la optimización adquirida y perder el detalle comercial.
-- El resto de relaciones de V1 permanece restrictivo, en especial
-- PEDIDOS -> PROYECTOS y MOVIMIENTOS_STOCK -> TABLEROS/USUARIOS.