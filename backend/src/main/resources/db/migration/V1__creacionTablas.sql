-- 3VETAS · Primera migración Flyway · MySQL 8.0.16+
-- La base de datos debe existir y ser seleccionada en la configuración de Flyway.
-- Tipos, tamaños y restricciones no indicados en la hoja: propuestas técnicas.

CREATE TABLE ROLES (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(80) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE USUARIOS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(254) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  rol_ID INT NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  ultimo_acceso DATETIME NULL,
  CONSTRAINT fk_usuarios_roles FOREIGN KEY (rol_ID) REFERENCES ROLES(ID)
) ENGINE=InnoDB;

CREATE TABLE PERSONAS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  usuario_ID INT NOT NULL UNIQUE,
  nombre VARCHAR(100) NOT NULL,
  apellidos VARCHAR(150) NOT NULL,
  telefono VARCHAR(25) NULL,
  dni VARCHAR(20) NULL,
  CONSTRAINT fk_personas_usuarios FOREIGN KEY (usuario_ID) REFERENCES USUARIOS(ID)
) ENGINE=InnoDB;

CREATE TABLE DIRECCIONES (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  persona_ID INT NOT NULL,
  tipo ENUM('envio','facturacion') NOT NULL,
  direccion VARCHAR(255) NOT NULL,
  cp CHAR(5) NOT NULL,
  municipio VARCHAR(120) NOT NULL,
  provincia VARCHAR(120) NOT NULL,
  pais VARCHAR(120) NOT NULL,
  CONSTRAINT fk_direcciones_personas FOREIGN KEY (persona_ID) REFERENCES PERSONAS(ID),
  CONSTRAINT chk_direcciones_cp CHECK (cp REGEXP '^[0-9]{5}$')
) ENGINE=InnoDB;

CREATE TABLE TIPOS_TABLERO (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  descripcion TEXT NULL,
  imagen VARCHAR(500) NULL
) ENGINE=InnoDB;

CREATE TABLE ACABADOS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  familia VARCHAR(120) NULL,
  nombre VARCHAR(120) NOT NULL,
  color_base VARCHAR(100) NULL,
  textura VARCHAR(120) NULL,
  fabricante VARCHAR(150) NULL,
  cod_fabricante VARCHAR(100) NULL
) ENGINE=InnoDB;

CREATE TABLE FORMATOS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ancho_mm DECIMAL(12,4) NOT NULL,
  alto_mm DECIMAL(12,4) NOT NULL,
  CONSTRAINT chk_formatos_medidas CHECK (ancho_mm > 0 AND alto_mm > 0)
) ENGINE=InnoDB;

CREATE TABLE GROSORES (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  valor_mm DECIMAL(12,4) NOT NULL,
  CONSTRAINT chk_grosores_valor CHECK (valor_mm > 0)
) ENGINE=InnoDB;

CREATE TABLE TABLEROS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_tipo INT NOT NULL,
  ID_acabado INT NOT NULL,
  ID_formato INT NOT NULL,
  ID_grosor INT NOT NULL,
  referencia VARCHAR(100) NOT NULL UNIQUE,
  descripcion TEXT NULL,
  precio_tablero DECIMAL(10,2) NOT NULL,
  stock_actual INT NOT NULL DEFAULT 0,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_tableros_tipo FOREIGN KEY (ID_tipo) REFERENCES TIPOS_TABLERO(ID),
  CONSTRAINT fk_tableros_acabado FOREIGN KEY (ID_acabado) REFERENCES ACABADOS(ID),
  CONSTRAINT fk_tableros_formato FOREIGN KEY (ID_formato) REFERENCES FORMATOS(ID),
  CONSTRAINT fk_tableros_grosor FOREIGN KEY (ID_grosor) REFERENCES GROSORES(ID),
  CONSTRAINT chk_tableros_precio CHECK (precio_tablero >= 0),
  CONSTRAINT chk_tableros_stock CHECK (stock_actual >= 0)
) ENGINE=InnoDB;

CREATE TABLE PROYECTOS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  usuario_ID INT NOT NULL,
  nombre VARCHAR(150) NOT NULL,
  descripcion TEXT NULL,
  ID_tipo_tablero INT NOT NULL,
  ID_acabado INT NOT NULL,
  ID_grosor INT NOT NULL,
  estado VARCHAR(40) NOT NULL,
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_proyectos_usuario FOREIGN KEY (usuario_ID) REFERENCES USUARIOS(ID),
  CONSTRAINT fk_proyectos_tipo FOREIGN KEY (ID_tipo_tablero) REFERENCES TIPOS_TABLERO(ID),
  CONSTRAINT fk_proyectos_acabado FOREIGN KEY (ID_acabado) REFERENCES ACABADOS(ID),
  CONSTRAINT fk_proyectos_grosor FOREIGN KEY (ID_grosor) REFERENCES GROSORES(ID)
) ENGINE=InnoDB;

CREATE TABLE PIEZAS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_proyecto INT NOT NULL,
  descripcion VARCHAR(255) NULL,
  ancho_mm DECIMAL(12,4) NOT NULL,
  alto_mm DECIMAL(12,4) NOT NULL,
  cantidad INT NOT NULL,
  rotacion_permitida BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_piezas_proyecto FOREIGN KEY (ID_proyecto) REFERENCES PROYECTOS(ID),
  CONSTRAINT chk_piezas_dimensiones CHECK (ancho_mm > 0 AND alto_mm > 0 AND cantidad > 0)
) ENGINE=InnoDB;

CREATE TABLE OPTIMIZACIONES (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_proyecto INT NOT NULL,
  nombre_opcion VARCHAR(150) NOT NULL,
  ID_tablero INT NOT NULL,
  cantidad INT NOT NULL,
  superficie_utilizada DECIMAL(24,8) NOT NULL,
  superficie_desperdiciada DECIMAL(24,8) NOT NULL,
  porcentaje_aprovechamiento DECIMAL(5,2) NOT NULL,
  coste_estimado DECIMAL(10,2) NOT NULL,
  seleccionada BOOLEAN NOT NULL DEFAULT FALSE,
  fecha_calculo DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_optimizaciones_proyecto FOREIGN KEY (ID_proyecto) REFERENCES PROYECTOS(ID),
  CONSTRAINT fk_optimizaciones_tablero FOREIGN KEY (ID_tablero) REFERENCES TABLEROS(ID),
  CONSTRAINT chk_optimizaciones_valores CHECK (
    cantidad > 0 AND superficie_utilizada >= 0 AND superficie_desperdiciada >= 0
    AND porcentaje_aprovechamiento BETWEEN 0 AND 100 AND coste_estimado >= 0
  )
) ENGINE=InnoDB;

CREATE TABLE DISTRIBUCION_PIEZAS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_optimizacion INT NOT NULL,
  ID_pieza INT NOT NULL,
  num_tablero INT NOT NULL,
  numero_unidad INT NOT NULL,
  orden_dibujo INT NOT NULL,
  posicion_x_mm DECIMAL(12,4) NOT NULL,
  posicion_y_mm DECIMAL(12,4) NOT NULL,
  ancho_final_mm DECIMAL(12,4) NOT NULL,
  alto_final_mm DECIMAL(12,4) NOT NULL,
  rotada BOOLEAN NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_distribucion_optimizacion FOREIGN KEY (ID_optimizacion) REFERENCES OPTIMIZACIONES(ID),
  CONSTRAINT fk_distribucion_pieza FOREIGN KEY (ID_pieza) REFERENCES PIEZAS(ID),
  CONSTRAINT chk_distribucion_valores CHECK (
    num_tablero > 0 AND numero_unidad > 0 AND orden_dibujo >= 0
    AND posicion_x_mm >= 0 AND posicion_y_mm >= 0
    AND ancho_final_mm > 0 AND alto_final_mm > 0
  )
) ENGINE=InnoDB;

CREATE TABLE PEDIDOS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_persona INT NOT NULL,
  ID_proyecto INT NOT NULL,
  ID_optimizacion INT NOT NULL,
  ID_direccion_envio INT NOT NULL,
  ID_direccion_facturacion INT NOT NULL,
  estado VARCHAR(40) NOT NULL,
  base_imponible DECIMAL(10,2) NOT NULL,
  descuento DECIMAL(10,2) NOT NULL DEFAULT 0,
  importe_iva DECIMAL(10,2) NOT NULL,
  importe_final DECIMAL(10,2) NOT NULL,
  fecha_pedido DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_pedidos_persona FOREIGN KEY (ID_persona) REFERENCES PERSONAS(ID),
  CONSTRAINT fk_pedidos_proyecto FOREIGN KEY (ID_proyecto) REFERENCES PROYECTOS(ID),
  CONSTRAINT fk_pedidos_optimizacion FOREIGN KEY (ID_optimizacion) REFERENCES OPTIMIZACIONES(ID),
  CONSTRAINT fk_pedidos_direccion_envio FOREIGN KEY (ID_direccion_envio) REFERENCES DIRECCIONES(ID),
  CONSTRAINT fk_pedidos_direccion_facturacion FOREIGN KEY (ID_direccion_facturacion) REFERENCES DIRECCIONES(ID),
  CONSTRAINT chk_pedidos_importes CHECK (
    base_imponible >= 0 AND descuento >= 0 AND importe_iva >= 0 AND importe_final >= 0
  )
) ENGINE=InnoDB;

CREATE TABLE PEDIDO_ESTADOS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  pedido_ID INT NOT NULL,
  estado VARCHAR(40) NOT NULL,
  comentario TEXT NULL,
  usuario_ID INT NOT NULL,
  fecha_estado DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_pedido_estados_pedido FOREIGN KEY (pedido_ID) REFERENCES PEDIDOS(ID),
  CONSTRAINT fk_pedido_estados_usuario FOREIGN KEY (usuario_ID) REFERENCES USUARIOS(ID)
) ENGINE=InnoDB;

CREATE TABLE DOCUMENTOS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_optimizacion INT NOT NULL,
  pedido_ID INT NULL,
  tipo_documento VARCHAR(80) NOT NULL,
  nombre_original VARCHAR(255) NOT NULL,
  nombre_fisico VARCHAR(255) NOT NULL,
  extension VARCHAR(20) NOT NULL,
  tamano_bytes BIGINT NOT NULL,
  ruta_archivo VARCHAR(1024) NOT NULL,
  fecha_generacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_documentos_optimizacion FOREIGN KEY (ID_optimizacion) REFERENCES OPTIMIZACIONES(ID),
  CONSTRAINT fk_documentos_pedido FOREIGN KEY (pedido_ID) REFERENCES PEDIDOS(ID),
  CONSTRAINT chk_documentos_tamano CHECK (tamano_bytes >= 0)
) ENGINE=InnoDB;

CREATE TABLE MOVIMIENTOS_STOCK (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ID_tablero INT NOT NULL,
  tipo_movimiento ENUM('entrada','salida','ajuste') NOT NULL,
  cantidad INT NOT NULL,
  fecha_mov DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  observaciones TEXT NULL,
  usuario_ID INT NOT NULL,
  CONSTRAINT fk_movimientos_tablero FOREIGN KEY (ID_tablero) REFERENCES TABLEROS(ID),
  CONSTRAINT fk_movimientos_usuario FOREIGN KEY (usuario_ID) REFERENCES USUARIOS(ID),
  CONSTRAINT chk_movimientos_cantidad CHECK (cantidad > 0)
) ENGINE=InnoDB;