#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <unistd.h>

#define MAXDATASIZE 300

main(){
    char buffer[MAXDATASIZE];

    //Descriptor de archivo o file descriptor
    //Se le asigna el valor devuelto por funcion socket
    int socket_fd;

    //Creacion del socket
    //Funcion socket devuelve entero que es el file descriptor
    printf("socket (AF_UNIX, SOCK_STREAM, 0)\n");
    socket_fd = socket (AF_UNIX, SOCK_STREAM, 0);

    //Variable tipo struct sockaddr_un
    //estructura del socket del servidor
    struct sockaddr_un servidor;

    //Apuntador a la estructura socket
    struct sockaddr* pservidor = (struct sockaddr*)&servidor;

    //Almacena el tamaño de la estructura
    //sockaddr_un es la estructura del servidor
    int size_servidor = sizeof(servidor);

    //Archivo para nombrar al socket
    //No debe existir, se crea de tamaño 0
    const char* const socket_name = "socket";

    //Inicializa atributo sun_family de estructura
    servidor.sun_family = AF_UNIX;

    //Inicializa atributo sun_path con nombre del archivo
    strcpy( servidor.sun_path, socket_name );

    //Ligar socket con estructura del servidor
    //como el archivo tiene nombre, se crea un socket nombrado
    printf("bind(socket_fd,pservidor,size_servidor)\n");
    bind(socket_fd,pservidor,size_servidor);

    //Servidor en escicha para conexiones, limite 5
    printf("listen (socket_fd, 5)\n");
    listen (socket_fd, 5);

    //Creacion de la estructura sockaddr_un
    struct sockaddr_un cliente;
    //Obtiene apuntador y tamaño
    struct sockaddr* pcliente = (struct sockaddr*)&cliente;
    int size_cliente = sizeof(cliente);
    //La estructura hablara con el socket del cliente al aceptar una conexion

    //Bloqueo del programa hasta recibir peticion de conexion
    printf("Antes de la funcion accept()\n");
    int client_socket_fd = accept (socket_fd, (struct sockaddr *)pcliente, &size_cliente);
    printf("Se acepto la conexion de un cliente\n");

    //Bloaque de recpecion del mensaje
    int num_bytes_leidos = recv( client_socket_fd, buffer, MAXDATASIZE - 1, 0 );
    printf("num_bytes_leidos=%d\n",num_bytes_leidos);
    buffer[num_bytes_leidos] = '\0';
    printf("Texto recibido: %s.\n",buffer);

    //Bloque para respuesta del servidor
    //Lectura de mensaje con teclado
    printf("\nEscribe la respuesta que deseas enviar, máximo %d catcteres\n", MAXDATASIZE);
    fgets(buffer, MAXDATASIZE, stdin);
    //Envio del mensaje
    int res_send = send(client_socket_fd, buffer, strlen(buffer)-1, 0);
    printf("res_send=%d\n", res_send);

    printf("close(socket_fd)\n");
    close( socket_fd );
    printf("unlink( socket_name )\n");
    unlink( socket_name );
    exit(0);
}
