#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

int main(){
	//Definicion de argumentos para el comando a ejecutar
	char *args[] = {"ls", "-l", "/home", "/opt", "/pruebas", NULL};
	
	//Valida si ocurrio un error en execvp
	if(execvp("ls", args) == -1){
		//Impresion del error
		perror("execvp");
		//Salida con fallo del programa
		exit(EXIT_FAILURE);
	}

	puts("shouldn't get here");
	exit(EXIT_SUCCESS);
}
