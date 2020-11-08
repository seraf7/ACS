/************************************************************/
/**************                         cliente.c           **********************/
/************************************************************/
/*********                   proceso cliente con sockets AF_UNIX   **********/
/************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <string.h>
#include <unistd.h>

#include <sys/un.h>     //Libreria para sockets

#define PROTOCOLO_DEFECTO 0
#define MAXDATASIZE 300     //Tamaño máximo de mensaje
/****************************************************/

main(){
    //Variable para almacenar mensaje
    char mensaje[MAXDATASIZE];

    //Variable para File descriptor cliente, y otros reultados
    int dfClient, longitud, resultado;
    //Estructura del socket servidor
    struct sockaddr_un dirUNIXServer;
    //Apuntador al socket
    struct sockaddr* puntSockServer;

    //Inicializacion del apuntador
    puntSockServer = (struct sockaddr*) &dirUNIXServer;
    longitud = sizeof(dirUNIXServer);

    printf("socket (AF_UNIX, SOCK_STREAM, 0)\n");
    //Creacion de un socket UNIX, bidireccional
    dfClient = socket(AF_UNIX, SOCK_STREAM, PROTOCOLO_DEFECTO);

    dirUNIXServer.sun_family = AF_UNIX;     //Tipo de dominio servidor
    strcpy(dirUNIXServer.sun_path, "socket");   //Nombre del servidor

    do{
        printf("Intentando conectarse\n");
        resultado = connect(dfClient, puntSockServer, longitud);
        if(resultado == -1);
            sleep(1);   //Duerme 1s antes de reintentar conexion
    }while(resultado == -1);

    //Lectura de mensaje con teclado
    printf("\nEscribe el mensaje que deseas enviar, máximo %d catcteres\n", MAXDATASIZE);
    fgets(mensaje, MAXDATASIZE, stdin);

    //Envio de mensaje al servidor
    int res_send = send(dfClient, mensaje, strlen(mensaje)-1, 0);
    printf("res_send=%d\n", res_send);

    //Bloque para recibir respuesta del servidor
    int bytes_leidos = recv(dfClient, mensaje, MAXDATASIZE-1, PROTOCOLO_DEFECTO);
    printf("\nbytes_leidos = %d\n", bytes_leidos);
    mensaje[bytes_leidos] = '\0';
    printf("Respuesta del servidor: %s.\n", mensaje);

    printf("close(dfClient)\n");
    close(dfClient);    //Cierra el socket
    exit(0);        //Fin del programa
}
/************* fin de cliente.c ****************************/
