# Cifrado Hill

- [Código fuente en Github](https://github.com/Jloen1999/06_P1y2SI2023_ela_nanguang)
- [La presentación del manual de Usuario en Canva](https://www.canva.com/design/DAFxDlz-4CM/4EpUKGDCMCqu0lqiTvCrvQ/view?utm_content=DAFxDlz-4CM&utm_campaign=share_your_design&utm_medium=link&utm_source=shareyourdesignpanel)
- [La presentación del manual de Programador en Canva](https://www.canva.com/design/DAFxFXChvyY/Z8JdK_KWe5NE0duzx32G9g/edit?utm_content=DAFxFXChvyY&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)
- **Ejemplos de claves:**
  - Clave 3x3: 1  2  3  0  4  5  1  0  6
  - Clave 4x4: 1  2  3  2  0  4  5  6  1  0  6  7  1  3  6  9

<span style="color: yellow">NOTA:</span>

  - *Los textos en claro y sus criptogramas correspondientes se almacenan en un archivo llamado logs.txt para tener un registro de todos los cifrados.*
  - *He creado dos jar, uno con la ejecución con los colores ANSI y otro con la ejecución normal(sin colores)*
  - *He creado 6 ejecuciones bat:*
    - `pruebaConArgumentoIncorrecto.bat`: Muestra el mensaje de error <span style="color: red">Error, parametro incorrecto</span> -> Ejecucion bat `p1y2_si -p`
    - ``pruebaMostrarAyuda.bat``: Muestra la ayuda del programa -> Ejecucion bat `p1y2_si -h`
    - ``pruebaSinArgumentos.bat``: Muestra el mensaje <span style="color: red">No has introducido ningun parametro</span> ->Ejecucion bat `p1y2_si`
    - ``pruebaSinFicheroConf.bat``: Muestra el mensaje de error <span style="color: red">Falta el archivo de configuracion</span>, ya que falta el fichero de configuracion ->Ejecucion bat ``p1y2_si -f``
    - ``probar.bat``: ejecuta correctamente el programa ``p1y2_si -f config.txt``
    - ``probarConColores.bat``: ejecuta correctamente el programa con los colores ANSI ``P1y2_SIConColores -f config.txt``



