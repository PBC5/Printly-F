CREATE TABLE USUARIOS (
    usuario_id NUMBER PRIMARY KEY,
    nombre VARCHAR2(100),
    email VARCHAR2(100) UNIQUE,
    password VARCHAR2(100),
    fecha_registro DATE
);

CREATE TABLE PUBLICACIONES (
  publicacion_id NUMBER PRIMARY KEY,
  usuario_id NUMBER,
  titulo VARCHAR2(200),
  descripcion VARCHAR2(1000),
  tipo VARCHAR2(50),
  fecha_publicacion DATE,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id)
);

CREATE TABLE MODELOS3D (
  modelo_id NUMBER,
  publicacion_id NUMBER NOT NULL,
  url_modelo VARCHAR2(500) NOT NULL,
  formato VARCHAR2(50),
  CONSTRAINT pk_modelos3d PRIMARY KEY (modelo_id),
  CONSTRAINT fk_modelos3d_publicacion FOREIGN KEY (publicacion_id) REFERENCES PUBLICACIONES (publicacion_id)
);

CREATE TABLE MATERIALES (
  material_id NUMBER,
  nombre VARCHAR2(100) NOT NULL,
  descripcion CLOB,
  reciclable CHAR(1) DEFAULT 'S',
  CONSTRAINT pk_materiales PRIMARY KEY (material_id)
);

CREATE TABLE PUBLICACION_MATERIAL (
  publicacion_id NUMBER NOT NULL,
  material_id NUMBER NOT NULL,
  cantidad NUMBER,
  CONSTRAINT pk_publicacion_material PRIMARY KEY (publicacion_id, material_id),
  CONSTRAINT fk_publicacion FOREIGN KEY (publicacion_id) REFERENCES PUBLICACIONES (publicacion_id),
  CONSTRAINT fk_material FOREIGN KEY (material_id) REFERENCES MATERIALES (material_id)
);

CREATE TABLE INTERACCIONES (
  interaccion_id NUMBER,
  publicacion_id NUMBER NOT NULL,
  usuario_id NUMBER NOT NULL,
  tipo VARCHAR2(50),
  comentario CLOB,
  fecha_interaccion DATE DEFAULT SYSDATE,
  CONSTRAINT pk_interacciones PRIMARY KEY (interaccion_id),
  CONSTRAINT fk_interacciones_publicacion FOREIGN KEY (publicacion_id) REFERENCES PUBLICACIONES (publicacion_id),
  CONSTRAINT fk_interacciones_usuario FOREIGN KEY (usuario_id) REFERENCES USUARIOS (usuario_id)
);

CREATE TABLE IMPRESORAS (
  impresora_id NUMBER,
  marca VARCHAR2(100) NOT NULL,
  modelo VARCHAR2(100) NOT NULL,
  tipo VARCHAR2(100),
  descripcion CLOB,
  CONSTRAINT pk_impresoras PRIMARY KEY (impresora_id)
);