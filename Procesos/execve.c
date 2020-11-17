//Requiere previa compilacion de myecho.c
//Usa parametros de ejecucion: ./ejecutable ./myecho
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[]){
	char *newargv[] = {NULL, "hello", "world", NULL};
	char *newenviron = {NULL};

	//Valida que se tengan 2 parametros de ejecucion
	if(argc != 2){
		fprintf(stderr, "Sintaxis: %s <file-to-exec>\n", argv[0]);
		exit(EXIT_FAILURE);
	}

	newargv[0] = argv[1];

	//Ejecuta el archivo dado como argumento con los parametros descritos en este archivo
	execve(argv[1], newargv, newenviron);
	//Solo devuelve el error
	perror("execve");
	exit(EXIT_FAILURE);
}
